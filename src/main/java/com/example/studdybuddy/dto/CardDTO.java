package com.example.studdybuddy.dto;

import java.time.Instant;
import java.util.List;

public record CardDTO(Long id, String front, String back, List<String> tags, Instant createdAt, Instant updatedAt) {
}
