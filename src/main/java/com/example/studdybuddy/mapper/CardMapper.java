package com.example.studdybuddy.mapper;

import com.example.studdybuddy.dto.CardDTO;
import com.example.studdybuddy.dto.CreateCardRequest;
import com.example.studdybuddy.model.Card;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class CardMapper {
    /**
     * Convert a Card entity into a CardDTO.
     *
     * @param card the Card entity to map
     * @return a new CardDTO, or null if the source card is null
     */
    public CardDTO toDto(Card card) {
        if (card == null) {
            return null;
        }

        // Defensive copy of tags list to prevent accidental mutation
        List<String> tags = card.getTags() != null
                ? new ArrayList<>(card.getTags())
                : Collections.emptyList();

        return new CardDTO(
                card.getId(),
                card.getFront(),
                card.getBack(),
                tags,
                card.getCreatedAt(),
                card.getUpdatedAt()
        );
    }

    /**
     * Convert a CreateCardRequest into a new Card entity.
     *
     * @param request the request carrying front, back, and optional tags
     * @return a fully initialized Card entity, or null if the request is null
     */
    public Card toEntity(CreateCardRequest request) {
        if (request == null) {
            return null;
        }

        Card card = new Card();
        card.setFront(request.front());
        card.setBack(request.back());

        // Defensive copy of tags (or empty list if null)
        List<String> tags = request.tags() != null
                ? new ArrayList<>(request.tags())
                : Collections.emptyList();
        card.setTags(tags);

        // Initialize timestamps
        Instant now = Instant.now();
        card.setCreatedAt(now);
        card.setUpdatedAt(now);

        return card;
    }
}
