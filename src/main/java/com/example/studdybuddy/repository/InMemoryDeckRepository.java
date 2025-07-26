package com.example.studdybuddy.repository;


import com.example.studdybuddy.exception.ResourceNotFoundException;
import com.example.studdybuddy.model.Deck;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryDeckRepository implements DeckRepository {

    private final ConcurrentHashMap<Long, Deck> store = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public Deck save(Deck deck) {
        if (deck.getId() == null) {
            // Create new deck
            long newId = idGenerator.incrementAndGet();
            deck.setId(newId);
            store.put(newId, deck);
        } else {
            // Update existing deck
            if (!store.containsKey(deck.getId())) {
                throw new ResourceNotFoundException(
                        "Cannot update Deck; no deck found with id " + deck.getId()
                );
            }
            store.put(deck.getId(), deck);
        }
        return deck;
    }

    @Override
    public Optional<Deck> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Deck> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void deleteById(Long id) {
        if (store.remove(id) == null) {
            throw new ResourceNotFoundException(
                    "Cannot delete Deck; no deck found with id " + id
            );
        }
    }
}
