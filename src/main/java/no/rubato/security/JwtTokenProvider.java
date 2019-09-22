package no.rubato.security;

import io.jsonwebtoken.*;
import no.rubato.model.Persons;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static no.rubato.security.SecurityConstants.EXPIRATION_TIME;
import static no.rubato.security.SecurityConstants.SECRET;

//Generate and Validate the token and getPersonId from the token
@Component
public class JwtTokenProvider {
    //Generate Token
    public String generateToken(Authentication authentication){
        Persons persons = (Persons)authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime()+EXPIRATION_TIME);
        String idPerson = Long.toString(persons.getIdPerson());
        Map<String, Object> claims = new HashMap<>();
        claims.put("idPerson", Long.toString(persons.getIdPerson()));
        claims.put("username", persons.getUsername());
        claims.put("role", persons.getRole());
        claims.put("name", persons.getName());


        return Jwts.builder()
                .setSubject(idPerson)
                //.setSubject(idBand)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    //Validate Token
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        }catch (SignatureException ex){
            System.out.println("Invalid JWT Signature");
        }catch (MalformedJwtException ex){
            System.out.println("Invalid JWT Token");
        }catch (ExpiredJwtException ex){
            System.out.println("Expired JWT Token");
        }catch (UnsupportedJwtException ex){
            System.out.println("Unsupported JWT Token");
        }catch (IllegalArgumentException ex){
            System.out.println("JWT claims string is empty");
        }
        return false;
    }

    //Get Person Id from token
    public long getPersonIdFromJWT(String token){
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        String id = (String)claims.get("idPerson");
        return Long.parseLong(id);
    }

}
