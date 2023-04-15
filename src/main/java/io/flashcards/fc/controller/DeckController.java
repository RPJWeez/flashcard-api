package io.flashcards.fc.controller;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.flashcards.fc.domain.Deck;
import io.flashcards.fc.repository.DeckRepository;
import lombok.AllArgsConstructor;

@RequestMapping("/v1/fc/decks")
@RestController
@AllArgsConstructor
public class DeckController {

    private final DeckRepository deckRepository;

    @PostMapping
    public ResponseEntity postDeck(@RequestBody Deck deck) {
        Deck savedDeck = deckRepository.save(deck);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedDeck.getDeckId())
                .toUri();

        return(ResponseEntity.created(location)).build();
    }

    @GetMapping("{uuid}")
    public ResponseEntity<Deck> getDeck(@PathVariable UUID uuid) {
        Deck deck = deckRepository.findById(uuid).orElse(null);
        if (deck == null) { 
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deck);
    }

    @DeleteMapping("{uuid}")
    public ResponseEntity deleteDeck(@PathVariable UUID uuid) {
        Deck deck = deckRepository.findById(uuid).orElse(null);
        if (deck == null) { 
            return ResponseEntity.notFound().build();
        }
        deckRepository.deleteById(uuid);
        return ResponseEntity.noContent().build();
    }
}
