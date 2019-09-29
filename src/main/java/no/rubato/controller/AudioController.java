package no.rubato.controller;

import no.rubato.model.Audio;
import no.rubato.service.AudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/api/audio/")
public class AudioController {

    private  final AudioService audioService;

    @Autowired
    public AudioController(AudioService audioService){
        this.audioService = audioService;
    }

    ///Upload Audio
    @PostMapping("/upload")
    public ResponseEntity<?> uploadAudio(@RequestBody Audio audio, Principal principal){
        Audio newAudio = audioService.saveAudio(audio, principal.getName());
        return new ResponseEntity<>(newAudio, HttpStatus.OK);
    }
    //Delete Audio By Id
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteImage(@PathVariable("id") long id){
        Audio currentAudio = audioService.findBySearchId(id);
        if(currentAudio == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        audioService.deleteAudioById(id);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //List All Images
    @GetMapping("/list-all")//Show all Users from Database
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(audioService.getAll(), HttpStatus.OK);
    }
}
