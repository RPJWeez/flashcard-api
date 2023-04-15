package io.flashcards.fc.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import io.flashcards.fc.domain.Card;

public interface CardRepository extends CrudRepository<Card, UUID>{
    List<Card> findAllByDeckDeckId(UUID deckId);
}
