package no.rubato.repository;

import no.rubato.model.Persons;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonsRepository extends JpaRepository<Persons, Long> {
    Persons getByIdPerson(long idPerson);
    Persons findByUsername(String username);

}
