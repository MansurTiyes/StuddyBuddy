package com.example.studdybuddy.mapper;

import com.example.studdybuddy.dto.CreateDeckRequest;
import com.example.studdybuddy.dto.DeckDTO;
import com.example.studdybuddy.model.Deck;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DeckMapper {
    public DeckDTO toDto(Deck deck) {
        if (deck == null) {
            return null;
        }

        return new DeckDTO(
                deck.getId(),
                deck.getTitle(),       // Mapping to 'name' in DTO
                deck.getDescription(),
                deck.getCardCount(),
                deck.getCreatedAt(),
                deck.getUpdatedAt()
        );
    }

    public Deck toEntity(CreateDeckRequest request) {
        if (request == null) {
            return null;
        }

        Deck deck = new Deck();
        deck.setTitle(request.name());                 // 'name' from request maps to 'title' in Deck
        deck.setDescription(request.description());
        deck.setCardCount(0);                          // New deck starts with zero cards
        deck.setCreatedAt(Instant.now());
        deck.setUpdatedAt(Instant.now());
        deck.setCards(new ConcurrentHashMap<>());      // Optional, but safe to re-initialize

        return deck;
    }
}
