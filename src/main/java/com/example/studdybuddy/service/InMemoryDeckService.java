package com.example.studdybuddy.service;

import com.example.studdybuddy.dto.CreateDeckRequest;
import com.example.studdybuddy.dto.DeckDTO;
import com.example.studdybuddy.dto.PageResponse;
import com.example.studdybuddy.exception.BadRequestException;
import com.example.studdybuddy.exception.ResourceNotFoundException;
import com.example.studdybuddy.mapper.DeckMapper;
import com.example.studdybuddy.model.Deck;
import com.example.studdybuddy.repository.DeckRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InMemoryDeckService implements DeckService {
    private final DeckRepository deckRepository;
    private final DeckMapper deckMapper;

    public InMemoryDeckService(DeckRepository deckRepository, DeckMapper deckMapper) {
        this.deckRepository = deckRepository;
        this.deckMapper = deckMapper;
    }

    @Override
    public PageResponse<DeckDTO> getAllDecks(int page, int size, String q) {
        // 2) Validate paging parameters
        if (page < 0 || size <= 0) {
            throw new BadRequestException("Page index must be >= 0 and size must be > 0");
        }

        // 1) Fetch all decks
        List<Deck> allDecks = deckRepository.findAll();

        // 4) Optional filtering by keyword in title or description
        List<Deck> filtered = (q == null || q.isBlank())
                ? allDecks
                : allDecks.stream()
                .filter(d -> d.getTitle().toLowerCase().contains(q.toLowerCase())
                        || (d.getDescription() != null
                        && d.getDescription().toLowerCase().contains(q.toLowerCase())))
                .collect(Collectors.toList());

        long totalElements = filtered.size();

        // 3) Slice for pagination
        int fromIndex = page * size;
        List<Deck> pageOfDecks;
        if (fromIndex >= filtered.size()) {
            pageOfDecks = Collections.emptyList();
        } else {
            int toIndex = Math.min(fromIndex + size, filtered.size());
            pageOfDecks = filtered.subList(fromIndex, toIndex);
        }

        // 5) Map to DTOs
        List<DeckDTO> dtoContent = pageOfDecks.stream()
                .map(deckMapper::toDto)
                .collect(Collectors.toList());

        // 6) Build and return PageResponse (no explicit sort implemented here)
        return new PageResponse<>(
                dtoContent,
                page,
                size,
                totalElements,
                ""
        );
    }

    @Override
    public DeckDTO getDeckById(Long deckId) {
        // 1) Retrieve deck or throw if not found
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new ResourceNotFoundException("Deck not found with id " + deckId));

        // 2) Map to DTO and return
        return deckMapper.toDto(deck);
    }

    @Override
    public DeckDTO createDeck(CreateDeckRequest createDeckRequest) {
        if (createDeckRequest == null) {
            throw new BadRequestException("CreateDeckRequest must not be null");
        }

        // 1) Map request to entity
        Deck deck = deckMapper.toEntity(createDeckRequest);

        // 2) Persist via repository
        Deck savedDeck = deckRepository.save(deck);

        // 3) Map saved entity to DTO
        return deckMapper.toDto(savedDeck);
    }

    @Override
    public DeckDTO updateDeck(Long deckId, CreateDeckRequest updateRequest) {
        if (updateRequest == null) {
            throw new BadRequestException("UpdateDeckRequest must not be null");
        }

        // 1) Fetch existing deck or throw if not found
        Deck existing = deckRepository.findById(deckId)
                .orElseThrow(() -> new ResourceNotFoundException("Deck not found with id " + deckId));

        // 2) Update fields
        existing.setTitle(updateRequest.name());
        if (updateRequest.description() != null) {
            existing.setDescription(updateRequest.description());
        }

        // 3) Bump the updatedAt timestamp
        existing.setUpdatedAt(Instant.now());

        // Persist changes (in-memory save will overwrite)
        Deck saved = deckRepository.save(existing);

        // 4) Map to DTO and return
        return deckMapper.toDto(saved);
    }

    @Override
    public void deleteDeckById(Long deckId) {
        deckRepository.deleteById(deckId);
    }

}
