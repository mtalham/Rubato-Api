package no.rubato.service;

import no.rubato.exceptions.UsernameAlreadyExistsException;
import no.rubato.model.BandInformation;
import no.rubato.model.PersonDto;
import no.rubato.model.Persons;
import no.rubato.repository.PersonsRepository;
import org.simmetrics.StringMetric;
import org.simmetrics.metrics.StringMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("personsService")
public class PersonsService {

  private final double NAME_FUZZY_LEVEL = 0.75;
  private final PersonsRepository personsRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  private StringMetric stringMatcher = StringMetrics.smithWatermanGotoh();

  @Autowired
  public PersonsService(
      PersonsRepository personsRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.personsRepository = personsRepository;
  }

  public List<PersonDto> searchUsers(String searchTerm) {
    Persons persons = new Persons();
    List<Persons> allPersons =
        personsRepository.findAll().stream()
            .filter(
                person ->
                    bySearchTerm(searchTerm, person.getName())
                        || bySearchTerm(searchTerm, person.getUsername())
                        || bySearchTerm(searchTerm, person.getRole())
                        || bySearchTerm(searchTerm, person.getPhone()))
            .collect(Collectors.toList());

    return persons.toDtos(allPersons);
  }

  private boolean bySearchTerm(String searchTerm, String personField) {
    return stringMatcher.compare(personField.toLowerCase(), searchTerm.toLowerCase())
            >= NAME_FUZZY_LEVEL;
  }

  public Persons findById(long id) {
    return personsRepository.getByIdPerson(id);
  }

  // Save
  public Persons savePerson(Persons persons) {
    try {
      persons.setPassword(bCryptPasswordEncoder.encode(persons.getPassword()));
      // username (email) has to be unique (throws exception)
      persons.setUsername(persons.getUsername());
      persons.setConfirmPassword("");
      // password and confirmPassword should match
      // do not persist or show confirmPassword
      return personsRepository.save(persons);
    } catch (Exception e) {
      throw new UsernameAlreadyExistsException(
          "Username '" + persons.getUsername() + "' already exists");
    }
  }

  // List All Users for Admin Dashboard
  public List<PersonDto> getAll() {
    Persons persons = new Persons();
    return persons.toDtos(personsRepository.findAll());
  }

  // Delete
  public void deletePersonById(long id) {
    personsRepository.deleteById(id);
  }

  public Persons getPersonByUsername(String username) {
    return personsRepository.findByUsername(username);
  }

  public Persons updateBand(Persons currentPerson, BandInformation bandInformation) {
    currentPerson.setName(bandInformation.getName());
    currentPerson.setAbout(bandInformation.getAbout());
    currentPerson.setPhone(bandInformation.getPhone());
    currentPerson.setPrice(bandInformation.getPrice());

    return personsRepository.save(currentPerson);
  }
}
