package com.example.studdybuddy.dto;

import java.time.Instant;

public record DeckDTO(Long id, String name, String description, int cardCount, Instant createdAt, Instant updatedAt) {
}
