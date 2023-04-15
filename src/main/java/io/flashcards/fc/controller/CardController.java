package io.flashcards.fc.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.flashcards.fc.controller.representation.FcErrorResponse;
import io.flashcards.fc.domain.Card;
import io.flashcards.fc.domain.Deck;
import io.flashcards.fc.repository.CardRepository;
import io.flashcards.fc.repository.DeckRepository;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v1/fc/decks/{deckId}/cards")
@AllArgsConstructor
public class CardController {

    private final CardRepository cardRepository;
    private final DeckRepository deckRepository;

    @PostMapping
    public ResponseEntity postCard(@PathVariable("deckId") UUID deckId, @RequestBody Card card) {
        Deck associatedDeck = deckRepository.findById(deckId).orElse(null);

        if (associatedDeck == null) {
            return ResponseEntity.notFound().build();
        }

        //decks can only have 100 cards
        if (associatedDeck.getCards().size() >= 100) {
            return ResponseEntity.badRequest().body(FcErrorResponse.builder().errorMessage("Decks are not allowed to have more than 100 cards").build());
        }

        card.setDeck(associatedDeck);
        cardRepository.save(card);

        return ResponseEntity.created(null).build();
    }
    
    @DeleteMapping("{cardId}")
    public ResponseEntity deleteCard(@PathVariable("deckId") UUID deckId, @PathVariable("cardId") UUID cardId) {
        //we don't really need to retrieve the deck to delete, but since the API only knows about cards in relation to the deck, we should make sure it exists anyway
        Deck foundDeck = deckRepository.findById(deckId).orElse(null);
        if (foundDeck == null) {
            return ResponseEntity.status(404).body(FcErrorResponse.builder().errorMessage("Deck with id " + deckId.toString() + " not found.").build());
        }
        
        Card foundCard = cardRepository.findById(cardId).orElse(null);
        if (foundCard == null) {
            return ResponseEntity.status(404).body(FcErrorResponse.builder().errorMessage("Card with id " + cardId.toString() + " not found.").build());
        }

        cardRepository.deleteById(cardId);
        return ResponseEntity.noContent().build();
    }
}
