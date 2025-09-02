package com.igor.app.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Todo {
    private String id;
    private String title;
    private boolean done;
    private LocalDate createdAt;
    private LocalDate due; // opcional

    public Todo() {}

    public Todo(String title, LocalDate due) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.done = false;
        this.createdAt = LocalDate.now();
        this.due = due;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public boolean isDone() { return done; }
    public LocalDate getCreatedAt() { return createdAt; }
    public LocalDate getDue() { return due; }

    public void setId(String id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDone(boolean done) { this.done = done; }
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }
    public void setDue(LocalDate due) { this.due = due; }

    @Override public String toString() {
        return (done ? "[OK]" : "[  ]") + " " + title + (due != null ? " (venc: " + due + ")" : "");
    }
    @Override public boolean equals(Object o){ return o instanceof Todo t && Objects.equals(id, t.id); }
    @Override public int hashCode(){ return Objects.hash(id); }
}
