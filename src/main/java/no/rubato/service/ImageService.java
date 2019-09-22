package no.rubato.service;

import no.rubato.model.Images;
import no.rubato.model.Persons;
import no.rubato.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("imageService")
public class ImageService {
    private final ImageRepository imageRepository;
    private final PersonsService personsService;

    @Autowired
    public ImageService(ImageRepository imageRepository, PersonsService personsService){
        this.imageRepository = imageRepository;
        this.personsService = personsService;
    }

    //Get Image by Id
    public Images getByIdImage(long id){
        return imageRepository.getByIdImage(id);
    }
    //Save Images
    public Images saveImage(Images image, String username) {
        Persons person = personsService.getPersonByUsername(username);
        image.setPersons(person);
        return imageRepository.save(image);
    }
    //Delete Images
    public void deleteImageById(long id){
        imageRepository.deleteById(id);
    }
    //Find By SearchId
    public Images findBySearchId(long id){
        return imageRepository.getByIdImage(id);
    }
    //List all
    public List<Images> getAll(){
        return imageRepository.findAll();
    }
}
