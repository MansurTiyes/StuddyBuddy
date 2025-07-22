package com.example.studdybuddy.model;

import java.time.Instant;
import java.util.List;

public class Card {
    private Long id;
    private String front;
    private String back;
    private List<String> tags;
    private Instant createdAt;
    private Instant updatedAt;

    public Card() {}

    public Long getId() {
        return id;
    }

    public String getFront() {
        return front;
    }

    public String getBack() {
        return back;
    }

    public List<String> getTags() {
        return tags;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFront(String front) {
        this.front = front;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
