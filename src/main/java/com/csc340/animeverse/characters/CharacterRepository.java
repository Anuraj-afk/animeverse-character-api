package com.csc340.animeverse.characters;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository
        extends JpaRepository<AnimeCharacter, Long> {

    List<AnimeCharacter> findByUniverseIgnoreCase(String universe);

    List<AnimeCharacter> findByNameContainingIgnoreCase(String name);

    List<AnimeCharacter> findByNameContainingIgnoreCaseAndUniverseIgnoreCase(
            String name, String universe);
}
