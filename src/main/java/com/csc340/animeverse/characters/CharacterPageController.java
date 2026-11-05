package com.csc340.animeverse.characters;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/characters/{id}")
    public String details(@PathVariable("id") long id, Model model) {
        model.addAttribute("character", service.findById(id));
        return "details";
    }

    @ExceptionHandler(CharacterNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String characterNotFound() {
        return "not-found";
    }

   @GetMapping("/characters/new")
    public String newCharacter(Model model, HttpSession session) {
    String formToken = UUID.randomUUID().toString();
    session.setAttribute("characterCreateToken", formToken);

    model.addAttribute("character", new AnimeCharacter());
    model.addAttribute("formToken", formToken);
        return characterForm(model);
    }

    @PostMapping("/characters")
    public String addCharacter(
        @Valid @ModelAttribute("character") AnimeCharacter character,
        BindingResult bindingResult,
        @RequestParam(name = "formToken", defaultValue = "") String formToken,
        HttpSession session,
        Model model,
        RedirectAttributes redirectAttributes) {

    synchronized (session) {
        if (!formToken.equals(session.getAttribute("characterCreateToken"))) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new LinkedHashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.putIfAbsent(error.getField(), error.getDefaultMessage()));

            model.addAttribute("errors", errors);
            model.addAttribute("formToken", formToken);
            return characterForm(model);
        }

        AnimeCharacter saved = service.create(character);
        session.removeAttribute("characterCreateToken");
        redirectAttributes.addFlashAttribute(
                "successMessage", saved.getName() + " was added.");
        return "redirect:/";
    }
}

private String characterForm(Model model) {
    model.addAttribute("formTitle", "Add a Character");
    model.addAttribute("formAction", "/characters");
    model.addAttribute("submitLabel", "Add Character");
    return "character-form";
}
    
    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
