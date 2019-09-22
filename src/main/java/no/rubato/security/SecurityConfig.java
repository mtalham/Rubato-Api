package no.rubato.security;

import no.rubato.service.CustomPersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static no.rubato.security.SecurityConstants.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final CustomPersonDetailsService customPersonDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    SecurityConfig(JwtAuthenticationEntryPoint unauthorizedHandler, CustomPersonDetailsService customPersonDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.unauthorizedHandler = unauthorizedHandler;
        this.customPersonDetailsService = customPersonDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

   /* @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;
    @Autowired
    private CustomPersonDetailsService customPersonDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){ return new JwtAuthenticationFilter();}


    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(customPersonDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }


    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //Frontend will handle the session, so the backend will be stateless
                .and()
                .headers().frameOptions().sameOrigin() //To enable H2 Databases
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()
                .antMatchers(SIGN_UP_URLS).permitAll()
                .antMatchers(VIEW_PERSON).permitAll()
                .antMatchers(FILE_UPLOAD).permitAll()
                .antMatchers(FILE_DOWNLOAD).permitAll()
                //.antMatchers(VIDEO).permitAll()
                //.antMatchers(ORDER).permitAll()
                .anyRequest()
                //.hasRole("admin");
                .authenticated();
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    ////inner class
}
