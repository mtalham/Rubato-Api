package no.rubato.service;

import no.rubato.model.Persons;
import no.rubato.repository.PersonsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomPersonDetailsService implements UserDetailsService {

    @Autowired
    private PersonsRepository personsRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Persons persons = personsRepository.findByUsername(username);
        if(persons == null) new UsernameNotFoundException("User not found");
        return persons;
    }
    @Transactional
        public Persons loadPersonById(long idPerson){
        Persons persons = personsRepository.getByIdPerson(idPerson);
        if(persons == null) new UsernameNotFoundException("User not found");
        return persons;
    }
}
