package com.example.bookservice;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // 1. ADD BOOK
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {

        Book savedBook = bookRepository.save(book);

        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    // 2. VIEW ALL BOOKS
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {

        List<Book> books = bookRepository.findAll();

        return ResponseEntity.ok(books);
    }

    // 3. VIEW BOOK BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {

        return bookRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. UPDATE BOOK
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable Long id,
            @RequestBody Book book) {

        return bookRepository.findById(id)
                .map(existingBook -> {

                    existingBook.setTitle(book.getTitle());
                    existingBook.setAuthor(book.getAuthor());
                    existingBook.setIsbn(book.getIsbn());

                    Book updatedBook = bookRepository.save(existingBook);

                    return ResponseEntity.ok(updatedBook);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. DELETE BOOK
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {

        if (bookRepository.existsById(id)) {

            bookRepository.deleteById(id);

            return ResponseEntity.ok("Book deleted successfully");

        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Book not found");
    }
}