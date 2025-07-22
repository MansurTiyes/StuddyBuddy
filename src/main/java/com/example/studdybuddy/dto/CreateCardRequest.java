package com.example.studdybuddy.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CreateCardRequest(
        @NotBlank(message="Card's Front must not be blank")
        String front,

        @NotBlank(message="Card's Back must not be blank")
        String back,

        List<String> tags
) {
}
