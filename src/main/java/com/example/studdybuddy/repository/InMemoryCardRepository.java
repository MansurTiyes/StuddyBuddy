package com.example.studdybuddy.repository;

import com.example.studdybuddy.exception.ResourceNotFoundException;
import com.example.studdybuddy.model.Card;
import com.example.studdybuddy.model.Deck;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryCardRepository implements CardRepository {

    private final DeckRepository deckRepository;
    private final ConcurrentHashMap<Long, AtomicLong> idGenerators = new ConcurrentHashMap<>();

    public InMemoryCardRepository(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    @Override
    public Card save(Long deckId, Card card) {
        // 1. Locate the deck or throw if missing
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new ResourceNotFoundException("Deck not found with id " + deckId));

        Map<Long, Card> cards = deck.getCards();

        if (card.getId() == null) {
            // Create new card
            AtomicLong gen = idGenerators.computeIfAbsent(deckId, id -> new AtomicLong());
            long newId = gen.incrementAndGet();
            card.setId(newId);
            cards.put(newId, card);
        } else {
            // Update existing card
            if (!cards.containsKey(card.getId())) {
                throw new ResourceNotFoundException("Cannot update Card; no card found with id " + card.getId()
                        + " in deck " + deckId);
            }
            cards.put(card.getId(), card);
        }

        // Keep deck's cardCount in sync
        deck.setCardCount(cards.size());

        return card;
    }

    @Override
    public Optional<Card> findById(Long deckId, Long cardId) {
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new ResourceNotFoundException("Deck not found with id " + deckId));

        return Optional.ofNullable(deck.getCards().get(cardId));
    }

    @Override
    public List<Card> findAll(Long deckId) {
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new ResourceNotFoundException("Deck not found with id " + deckId));
        return new ArrayList<>(deck.getCards().values());
    }

    @Override
    public void deleteById(Long deckId, Long cardId) {
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new ResourceNotFoundException("Deck not found with id " + deckId));

        Card removed = deck.getCards().remove(cardId);
        if (removed == null) {
            throw new ResourceNotFoundException("Cannot delete Card; no card found with id " + cardId
                    + " in deck " + deckId);
        }

        // Keep deck's cardCount in sync
        deck.setCardCount(deck.getCards().size());
    }
}
