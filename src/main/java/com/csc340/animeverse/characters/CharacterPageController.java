package com.csc340.animeverse.characters;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/characters/{id}")
    public String details(@PathVariable("id") long id, Model model) {
        model.addAttribute("character", service.findById(id));
        return "details";
    }

    @GetMapping("/characters/new")
    public String newCharacter(Model model, HttpSession session) {
        String formToken = UUID.randomUUID().toString();
        session.setAttribute("characterCreateToken", formToken);

        model.addAttribute("character", new AnimeCharacter());
        model.addAttribute("formToken", formToken);
        model.addAttribute("formTitle", "Add a Character");
        model.addAttribute("formAction", "/characters");
        model.addAttribute("submitLabel", "Add Character");
        return "character-form";
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
                addFieldErrors(bindingResult, model);
                model.addAttribute("formToken", formToken);
                model.addAttribute("formTitle", "Add a Character");
                model.addAttribute("formAction", "/characters");
                model.addAttribute("submitLabel", "Add Character");
                return "character-form";
            }

            AnimeCharacter saved = service.create(character);
            session.removeAttribute("characterCreateToken");
            redirectAttributes.addFlashAttribute(
                    "successMessage", saved.getName() + " was added.");
            return "redirect:/";
        }
    }

    @GetMapping("/characters/{id}/edit")
    public String editCharacter(@PathVariable("id") long id, Model model) {
        model.addAttribute("character", service.findById(id));
        return editForm(id, model);
    }

    @PostMapping("/characters/{id}/edit")
    public String updateCharacter(
            @PathVariable("id") long id,
            @Valid @ModelAttribute("character") AnimeCharacter character,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            service.findById(id);
            addFieldErrors(bindingResult, model);
            return editForm(id, model);
        }

        AnimeCharacter saved = service.update(id, character);
        redirectAttributes.addFlashAttribute(
                "successMessage", saved.getName() + " was updated.");
        return "redirect:/characters/" + id;
    }

    @PostMapping("/characters/{id}/delete")
    public String deleteCharacter(
            @PathVariable("id") long id,
            RedirectAttributes redirectAttributes) {

        String name = service.findById(id).getName();
        service.delete(id);
        redirectAttributes.addFlashAttribute(
                "successMessage", name + " was removed.");
        return "redirect:/";
    }

    @ExceptionHandler(CharacterNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String characterNotFound() {
        return "not-found";
    }

    private String editForm(long id, Model model) {
        model.addAttribute("formTitle", "Edit Character");
        model.addAttribute("formAction", "/characters/" + id + "/edit");
        model.addAttribute("submitLabel", "Save Changes");
        return "character-form";
    }

    private void addFieldErrors(BindingResult bindingResult, Model model) {
        Map<String, String> errors = new LinkedHashMap<>();
        bindingResult.getFieldErrors().forEach(error ->
                errors.putIfAbsent(error.getField(), error.getDefaultMessage()));
        model.addAttribute("errors", errors);
    }
}