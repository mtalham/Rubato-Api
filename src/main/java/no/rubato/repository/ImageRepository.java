package no.rubato.repository;

import no.rubato.model.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Images, Long> {
    Images getByIdImage(long idImage);
    //Images findByImageName(String name);

}
