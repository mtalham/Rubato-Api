package no.rubato.repository;

import no.rubato.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {
    Video getByIdVideo(long idVideo);

    List<Video> findByPersons_IdPerson(long id);
    //Images findByImageName(String name);

}
