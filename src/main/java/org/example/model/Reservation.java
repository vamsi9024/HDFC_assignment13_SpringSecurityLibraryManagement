package org.example.model;

public class Reservation {
    private Long id;
    private Long bookId;
    private String username;
    private Boolean approved; // ✅ Changed from primitive boolean to wrapper Boolean

    // Constructors
    public Reservation() {}

    public Reservation(Long id, Long bookId, String username, Boolean approved) {
        this.id = id;
        this.bookId = bookId;
        this.username = username;
        this.approved = approved;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }
}


