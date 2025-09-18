package org.example.controller;


import org.example.model.Reservation;
import org.example.service.BookService;
import org.example.service.ReservationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {

    private final ReservationService reservationService;
    private final BookService bookService;

    public AdminController(ReservationService rs, BookService bs) {
        this.reservationService = rs;
        this.bookService = bs;
    }

    @GetMapping("/reservations")
    @PreAuthorize("hasRole('ADMIN')")
    public String allRes(Model m) {
        m.addAttribute("reservations", reservationService.findAll());
        return "reservations";
    }

    @PostMapping("/reservations/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public String approve(@PathVariable Long id, @RequestParam boolean approve) {
        reservationService.approve(id, approve);
        return "redirect:/reservations";
    }

    @DeleteMapping("/books/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public String users(Model m) {
        // mock list of users
        List<String> users = List.of("admin", "librarian", "student", "guest");
        m.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/admin/reports")
    @PreAuthorize("hasRole('ADMIN')")
    public String reports() {
        return "admin-reports";
    }
}

