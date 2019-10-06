package no.rubato.controller;

import no.rubato.model.BandInformation;
import no.rubato.model.PersonDto;
import no.rubato.model.Persons;
import no.rubato.service.PersonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/person/")
public class PersonController {
  private final PersonsService personsService;

  @Autowired
  public PersonController(PersonsService personsService) {
    this.personsService = personsService;
  }
  /// Update Profile Information
  @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
  public ResponseEntity<?> updateInfo(@PathVariable("id") long id, @RequestBody Persons persons) {
    Persons currentPerson = personsService.findById(id);
    if (currentPerson == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    currentPerson.setUsername(persons.getUsername());
    currentPerson.setName(persons.getName());
    currentPerson.setPassword(persons.getPassword());
    currentPerson.setConfirmPassword(persons.getConfirmPassword());
    currentPerson.setPhone(persons.getPhone());
    currentPerson.setPrice(persons.getPrice());
    currentPerson.setVipps(persons.getVipps());
    currentPerson.setAbout(persons.getAbout());
    currentPerson.setRole(persons.getRole());

    personsService.savePerson(currentPerson);
    return new ResponseEntity<>(persons, HttpStatus.OK);
  }
  /// Delete Profile
  @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
    Persons currentPerson = personsService.findById(id);
    if (currentPerson == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    personsService.deletePersonById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /// List All Users For Admin Use Only
  @GetMapping("/list-all") // Show all Users from Database
  public ResponseEntity<?> getAll() {
    return new ResponseEntity<>(personsService.getAll(), HttpStatus.OK);
  }

  /// Search By Email, Phone, Role, Orders For Admin Use Only
  @RequestMapping(value = "/search/{searchId}", method = RequestMethod.GET)
  public List<PersonDto> searchUsers(@PathVariable String searchId) {
    return personsService.searchUsers(searchId);
  }
  // Search By Id
  @RequestMapping(value = "/searchById/{id}", method = RequestMethod.GET)
  public Persons searchPersonById(@PathVariable long id) {
    return personsService.findById(id);
  }
  /// More Complex Booking Request Mapping

  /// Cancel Booking Request Mapping
  @RequestMapping(value = "/update-band/{id}", method = RequestMethod.PUT)
  public ResponseEntity<?> updateBandInfo(
      @PathVariable("id") long id, @RequestBody BandInformation bandInformation) {
    Persons currentPerson = personsService.findById(id);
    if (currentPerson == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(
        personsService.updateBand(currentPerson, bandInformation), HttpStatus.OK);
  }
}
