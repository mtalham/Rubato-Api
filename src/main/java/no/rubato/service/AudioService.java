package no.rubato.service;

import no.rubato.model.Audio;
import no.rubato.repository.AudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("audioService")
public class AudioService {
    private final AudioRepository audioRepository;

    @Autowired
    public AudioService(AudioRepository audioRepository){
        this.audioRepository = audioRepository;
    }

    //Get Audio by Id
    public Audio getByIdAudio(long id){
        return audioRepository.getByIdAudio(id);
    }
    //Save Audio
    public Audio saveAudio(Audio audio) {
        return audioRepository.save(audio);
    }
    //Delete Audio
    public void deleteAudioById(long id){
        audioRepository.deleteById(id);
    }
    //Find By SearchId
    public Audio findBySearchId(long id){
        return audioRepository.getByIdAudio(id);
    }
    public List<Audio> getAll(){
        return audioRepository.findAll();
    }
}
