package org.example.service;


import org.example.model.Reservation;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReservationService {

    private final Map<Long, Reservation> store = new HashMap<>();
    private long nextId = 1;

    public List<Reservation> findAll() {
        return new ArrayList<>(store.values());
    }

    public Reservation reserve(Long bookId, String username) {
        // Check if user already reserved this book
        if (hasUserReserved(bookId, username)) return null;

        Reservation r = new Reservation();
        r.setId(nextId++);
        r.setBookId(bookId);
        r.setUsername(username);
        r.setApproved(null); // pending initially
        store.put(r.getId(), r);

        System.out.println("Current reservations: " + store);
        return r;
    }

    public Reservation approve(Long id, boolean flag) {
        Reservation r = store.get(id);
        if (r != null) {
            r.setApproved(flag);
        }
        return r;
    }

    public boolean hasUserReserved(Long bookId, String username) {
        return store.values().stream()
                .anyMatch(r -> r.getBookId().equals(bookId) && r.getUsername().equals(username));
    }
}


