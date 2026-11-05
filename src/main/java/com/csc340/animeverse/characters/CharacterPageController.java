package com.csc340.animeverse.characters;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CharacterPageController {

    private final CharacterService service;

    public CharacterPageController(CharacterService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String gallery(
            @RequestParam(name = "name", defaultValue = "") String name,
            @RequestParam(name = "universe", defaultValue = "") String universe,
            Model model) {

        List<AnimeCharacter> allCharacters = service.search(null, null);
        List<String> universes = allCharacters.stream()
                .map(AnimeCharacter::getUniverse)
                .distinct()
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .toList();

        List<AnimeCharacter> characters = allCharacters;
        if (!name.isBlank() || !universe.isBlank()) {
            characters = service.search(name, universe);
        }

        model.addAttribute("characters", characters);
        model.addAttribute("universes", universes);
        model.addAttribute("name", name);
        model.addAttribute("selectedUniverse", universe);
        return "gallery";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
