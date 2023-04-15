package io.flashcards.fc.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import io.flashcards.fc.domain.Deck;

public interface DeckRepository extends CrudRepository<Deck, UUID>{
    
}
