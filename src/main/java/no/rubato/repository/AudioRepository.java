package no.rubato.repository;

import no.rubato.model.Audio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudioRepository extends JpaRepository<Audio, Long> {
    Audio getByIdAudio(long idAudio);
    //Images findByImageName(String name);

}
