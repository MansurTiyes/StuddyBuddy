package com.example.studdybuddy.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateDeckRequest(
       @NotBlank(message = "Deck name must not be blank")
       String name,
       String description
) {
}
