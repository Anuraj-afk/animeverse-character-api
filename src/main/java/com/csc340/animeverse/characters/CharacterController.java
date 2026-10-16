package com.csc340.animeverse.characters;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/characters")
public class CharacterController {

    private final CharacterService service;

    public CharacterController(CharacterService service) {
        this.service = service;
    }

    @GetMapping
    public List<AnimeCharacter> list(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "universe", required = false) String universe) {

        return service.search(name, universe);
    }

    @GetMapping("/{id}")
    public AnimeCharacter get(@PathVariable("id") long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AnimeCharacter create(
            @Valid @RequestBody AnimeCharacter character) {

        return service.create(character);
    }

    @PutMapping("/{id}")
    public AnimeCharacter update(
            @PathVariable("id") long id,
            @Valid @RequestBody AnimeCharacter character) {

        return service.update(id, character);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id) {
        service.delete(id);
    }
}
