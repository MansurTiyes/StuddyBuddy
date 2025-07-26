package com.example.studdybuddy.repository;

import com.example.studdybuddy.model.Deck;

import java.util.List;
import java.util.Optional;

public interface DeckRepository {
    Deck save(Deck deck);
    Optional<Deck> findById(Long id);
    List<Deck> findAll();
    void deleteById(Long id);
}
