package com.csc340.animeverse.characters;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CharacterService {

    private final CharacterRepository repository;

    public CharacterService(CharacterRepository repository) {
        this.repository = repository;
    }

    public List<AnimeCharacter> search(String name, String universe) {
        boolean hasName = name != null && !name.isBlank();
        boolean hasUniverse = universe != null && !universe.isBlank();

        if (hasName && hasUniverse) {
            return repository
                    .findByNameContainingIgnoreCaseAndUniverseIgnoreCase(
                            name.trim(), universe.trim());
        }

        if (hasName) {
            return repository.findByNameContainingIgnoreCase(name.trim());
        }

        if (hasUniverse) {
            return repository.findByUniverseIgnoreCase(universe.trim());
        }

        return repository.findAll();
    }

    public AnimeCharacter findById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CharacterNotFoundException(id));
    }

    public AnimeCharacter create(AnimeCharacter input) {
        // Create a fresh entity so the database always generates the new ID.
        AnimeCharacter character = new AnimeCharacter();
        copyFields(input, character);
        return repository.save(character);
    }

    public AnimeCharacter update(long id, AnimeCharacter input) {
        AnimeCharacter existing = findById(id);
        copyFields(input, existing);
        return repository.save(existing);
    }

    public void delete(long id) {
        AnimeCharacter existing = findById(id);
        repository.delete(existing);
    }

    private void copyFields(
            AnimeCharacter source, AnimeCharacter destination) {

        destination.setName(source.getName().trim());
        destination.setDescription(source.getDescription().trim());
        destination.setUniverse(source.getUniverse().trim());
        destination.setSpecies(source.getSpecies().trim());
    }
}
