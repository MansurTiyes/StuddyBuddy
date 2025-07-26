package com.example.studdybuddy.service;

import com.example.studdybuddy.dto.CreateDeckRequest;
import com.example.studdybuddy.dto.DeckDTO;
import com.example.studdybuddy.dto.PageResponse;

public interface DeckService {

    PageResponse<DeckDTO> getAllDecks(int page, int size, String q);
    DeckDTO getDeckById(Long deckId);
    DeckDTO createDeck(CreateDeckRequest createDeckRequest);
    DeckDTO updateDeck(Long deckId, CreateDeckRequest updateDeckRequest);
    void deleteDeckById(Long deckId);

}
