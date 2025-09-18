package org.example.controller;


import org.example.model.Book;
import org.example.service.BookService;
import org.example.service.ReservationService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final ReservationService reservationService;

    public BookController(BookService bs, ReservationService rs) {
        this.bookService = bs;
        this.reservationService = rs;
    }

    @GetMapping("/public")
    public String publicCatalog(Model m) {
        m.addAttribute("books", bookService.findAll());
        return "public-books";
    }

//    @GetMapping
//    @PreAuthorize("hasRole('STUDENT')")
//    public String list(Model m) {
//        m.addAttribute("books", bookService.findAll());
//        return "books";
//    }

    @GetMapping
    public String listBooks(Model model,Principal principal) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);

        Map<Long, Boolean> reservedMap = new HashMap<>();
        String username = (principal != null) ? principal.getName() : null;

        for (Book book : books) {
            boolean reserved = false;
            if (username != null) {
                reserved = reservationService.hasUserReserved(book.getId(), username);
            }
            reservedMap.put(book.getId(), reserved);
        }

        model.addAttribute("reservedMap", reservedMap);
        return "books";
    }


    @PostMapping("/{id}/reserve")
    @PreAuthorize("hasRole('STUDENT')")
    public String reserve(@PathVariable Long id, Principal principal, RedirectAttributes redirectAttributes) {
        String user = principal.getName();
        if (reservationService.hasUserReserved(id, user)) {
            redirectAttributes.addFlashAttribute("errorMessage", "You have already reserved this book!");
        } else {
            reservationService.reserve(id, user);
            redirectAttributes.addFlashAttribute("successMessage", "Book reserved successfully!");
        }
        return "redirect:/books";
    }

    @PostMapping
    @PreAuthorize("hasRole('LIBRARIAN')")
    public String add(@ModelAttribute Book b) {
        bookService.add(b);
        return "redirect:/books";
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public String update(@PathVariable Long id, @ModelAttribute Book b) {
        bookService.update(id, b);
        return "redirect:/books";
    }
}

