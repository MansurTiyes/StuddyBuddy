package com.example.studdybuddy.repository;

import com.example.studdybuddy.model.Card;

import java.util.List;
import java.util.Optional;

public interface CardRepository {
    Card save(Long deckId, Card card);
    Optional<Card> findById(Long deckId, Long cardId);
    List<Card> findAll(Long deckId);
    void deleteById(Long deckId, Long cardId);
}
