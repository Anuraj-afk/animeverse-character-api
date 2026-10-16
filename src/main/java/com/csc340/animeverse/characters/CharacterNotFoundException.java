package com.csc340.animeverse.characters;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CharacterNotFoundException extends RuntimeException {

    public CharacterNotFoundException(long id) {
        super("Character " + id + " not found");
    }
}
