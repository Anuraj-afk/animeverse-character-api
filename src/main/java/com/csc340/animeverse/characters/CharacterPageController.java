package com.csc340.animeverse.characters;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CharacterPageController {

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
