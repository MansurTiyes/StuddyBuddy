package com.example.studdybuddy.model;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Deck {
    private Long id;
    private String title;
    private String description;
    private int cardCount;
    private Instant createdAt;
    private Instant updatedAt;
    private Map<Long, Card> cards = new ConcurrentHashMap<>();

    public Deck() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCardCount() {
        return cardCount;
    }

    public void setCardCount(int cardCount) {
        this.cardCount = cardCount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Map<Long, Card> getCards() {
        return cards;
    }

    public void setCards(Map<Long, Card> cards) {
        this.cards = cards;
    }
}
