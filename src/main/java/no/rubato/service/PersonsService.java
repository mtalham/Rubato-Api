package no.rubato.service;

import no.rubato.exceptions.UsernameAlreadyExistsException;
import no.rubato.model.BandInformation;
import no.rubato.model.Persons;
import no.rubato.repository.PersonsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("personsService")
public class PersonsService {

    private final PersonsRepository personsRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public PersonsService(PersonsRepository personsRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.personsRepository = personsRepository;
    }

    //Search Function
    public List<Persons> findBySearch(String searchId) {
            return personsRepository.findAll().stream().filter(Objects::nonNull).filter(
                    persons -> persons.getUsername().equals(searchId) ||
                            persons.getPhone().equals(searchId) ||//Search By Phone
                            persons.getVipps().equals(searchId) ||//Search By Vipps
                            persons.getAbout().equals(searchId) ||//Search By about
                            persons.getName().equals(searchId) ||//Search by Name
                            persons.getRole().equals(searchId) ||//Search By Role
                            persons.getPrice().equals(searchId) //||//Search By Price
                            //persons.getIdPerson() == Long.parseLong(searchId)//Search By Id
            ).collect(Collectors.toList());
    }
    //Find By id
    public Persons findBySearchId(long id){
        return personsRepository.getByIdPerson(id);
    }
    //Save
    public Persons savePerson(Persons persons) {
        try {
            persons.setPassword(bCryptPasswordEncoder.encode(persons.getPassword()));
            //username (email) has to be unique (throws exception)
            persons.setUsername(persons.getUsername());
            persons.setConfirmPassword("");
            //password and confirmPassword should match
            //do not persist or show confirmPassword
            return personsRepository.save(persons);
        } catch (Exception e) {
            throw new UsernameAlreadyExistsException("Username '" + persons.getUsername() + "' already exists");
        }
    }
    //List All Users for Admin Dashboard
    public List<Persons> getAll(){
        return personsRepository.findAll();
    }
    //Delete
    public void deletePersonById(long id){
        personsRepository.deleteById(id);
    }

    public Persons getPersonByUsername(String username) { return personsRepository.findByUsername(username); }

    public Persons updateBand(Persons currentPerson, BandInformation bandInformation) {
        currentPerson.setName(bandInformation.getName());
        currentPerson.setAbout(bandInformation.getAbout());
        currentPerson.setPhone(bandInformation.getPhone());
        currentPerson.setPrice(bandInformation.getPrice());

        return personsRepository.save(currentPerson);
    }
}
