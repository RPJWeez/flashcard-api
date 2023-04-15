package io.flashcards.fc.domain;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Deck {
    @Id
    @GeneratedValue
    private UUID deckId;

    @NotBlank(message = "Decks must have a name")
    @Size(min = 3, max = 50, message = "Deck name must be between 3 and 50 characters")
    private String deckName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "deck")
    @JsonManagedReference //prevents json representation from writing circular loop of cards and decks
    @Cascade(CascadeType.DELETE)
    private List<Card> cards;
}
