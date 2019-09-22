package no.rubato.service;

import no.rubato.model.Persons;
import no.rubato.model.Video;
import no.rubato.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("videoService")
public class VideoService {
    private final VideoRepository videoRepository;

    private final PersonsService personsService;

    @Autowired
    public VideoService(VideoRepository videoRepository, PersonsService personsService){
        this.videoRepository = videoRepository;
        this.personsService = personsService;
    }

    //Get Video by Id
    public Video getByIdVideo(long id){
        return videoRepository.getByIdVideo(id);
    }
    //Save Video
    public Video saveVideo(Video video, String username) {
        Persons persons = personsService.getPersonByUsername(username);
        video.setPersons(persons);
        return videoRepository.save(video);
    }
    //Delete Video
    public void deleteVideoById(long id){
        videoRepository.deleteById(id);
    }
    //Find By SearchId
    public Video getById(long id){
        return videoRepository.getByIdVideo(id);
    }
    public List<Video> getAll(){
        return videoRepository.findAll();
    }

    public List<Video> findByPersonId(long id) { return videoRepository.findByPersons_IdPerson(id); }
}
