package ru.sergeypyzin.springrestapi.controller.restController;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.sergeypyzin.springrestapi.domain.Characters;
import ru.sergeypyzin.springrestapi.domain.Result;
import ru.sergeypyzin.springrestapi.service.ServiceApi;

@RestController
@AllArgsConstructor
public class ControllerAPI {

    private ServiceApi serviceApi;

    @GetMapping("/characters")
    public ResponseEntity<Characters> getAllCharacters() {
        Characters characters = serviceApi.getAllCharacters();
        if (characters != null) {
            return new ResponseEntity<>(characters, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/character/{id}")
    public ResponseEntity<Result> getCharacter(@PathVariable Integer id){
        Result character = serviceApi.getCharacterById(id);
        if (character != null) {
            return new ResponseEntity<>(character, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/characters/sortedbyname")
    public ResponseEntity<Characters> getCharactersSortedByName() {
        Characters characters = serviceApi.getSortedByName();
        if (characters != null) {
            return new ResponseEntity<>(characters, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/characters/sortedbygender")
    public ResponseEntity<Characters> getCharactersSortedByGender() {
        Characters characters = serviceApi.getSortedByGender();
        if (characters != null) {
            return new ResponseEntity<>(characters, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/characters/sortedbycreated")
    public ResponseEntity<Characters> getCharactersSortedByCreated() {
        Characters characters = serviceApi.getSortedByCreated();
        if (characters != null) {
            return new ResponseEntity<>(characters, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
