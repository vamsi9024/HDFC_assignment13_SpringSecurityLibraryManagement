package org.example.service;


import org.example.model.Book;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
    import java.util.*;

    @Service
    public class BookService {
        private final Map<Long, Book> store = new HashMap<>();
        private long nextId = 1;

        @PostConstruct
        public void init() {
            // seed some books
            add(new Book(null, "1984", "George Orwell", "1234567890", true));
            add(new Book(null, "Clean Code", "Robert Martin", "0987654321", true));
        }

    public List<Book> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public Book add(Book b) {
        b.setId(nextId++);
        store.put(b.getId(), b);
        return b;
    }

    public Book update(Long id, Book b) {
        b.setId(id);
        store.put(id, b);
        return b;
    }

    public void delete(Long id) {
        store.remove(id);
    }
}

