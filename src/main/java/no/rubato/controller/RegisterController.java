package no.rubato.controller;

import no.rubato.model.Persons;
import no.rubato.payload.JWTLoginSuccessResponse;
import no.rubato.payload.LoginRequest;
import no.rubato.security.JwtTokenProvider;
import no.rubato.service.MapValidationErrorService;
import no.rubato.service.PersonsService;
import no.rubato.validator.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static no.rubato.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/register")
public class RegisterController {

  private final PersonsService personsService;
  private final MapValidationErrorService mapValidationErrorService;
  private final PersonValidator personValidator;
  private final JwtTokenProvider tokenProvider;
  private final AuthenticationManager authenticationManager;

  // Using Constructor Based Dependency Injection(Spring Recommended)
  @Autowired
  public RegisterController(
      PersonsService personsService,
      MapValidationErrorService mapValidationErrorService,
      PersonValidator personValidator,
      JwtTokenProvider tokenProvider,
      AuthenticationManager authenticationManager) {
    this.personsService = personsService;
    this.mapValidationErrorService = mapValidationErrorService;
    this.personValidator = personValidator;
    this.tokenProvider = tokenProvider;
    this.authenticationManager = authenticationManager;
  }

  //// Register Person
  @CrossOrigin
  @PostMapping("/signUp")
  public ResponseEntity<?> registerPerson(
      @Valid @RequestBody Persons persons, BindingResult result) {
    // Validate passwords match
    personValidator.validate(persons, result);
    ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
    if (errorMap != null) return errorMap;
    Persons newPersons = personsService.savePerson(persons);
    return new ResponseEntity<Persons>(newPersons, HttpStatus.CREATED);
  }

  //// Login Person
  @CrossOrigin
  @PostMapping("/signIn")
  public ResponseEntity<?> authenticatePerson(
      @Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
    ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
    if (errorMap != null) return errorMap;

    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()
            )
        );

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = TOKEN_PREFIX + tokenProvider.generateToken(authentication);
    return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));
  }
}
