package com.integrify.fs12.book.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import com.integrify.fs12.author.repository.AuthorRepository;
import com.integrify.fs12.book.bean.Book;
import com.integrify.fs12.book.dto.BookDTO;
import com.integrify.fs12.book.repository.BookRepository;
import com.integrify.fs12.category.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

	@Autowired
	private BookRepository repository;
	@Autowired
	private AuthorRepository authorRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	// GET all books
	@GetMapping("/api/v1/books")
	public List<Book> getAllBooks() {
		return repository.findAll();
	}

	// GET a single book
	@GetMapping("/api/v1/books/{id}")
	public Book getBookById(@PathVariable long id) {
		Optional<Book> book = repository.findById(id);
		if (book.isEmpty()) {
			throw new RuntimeException("Book not found with the ID of " + id);
		}

		return book.get();
	}

	// POST a new book
	@PostMapping("/api/v1/books")
	public ResponseEntity<?> createBook(@RequestBody BookDTO book) {
		System.out.println("how the book is looking like" + book);
		Book result = book.convertToBook(authorRepository.findAllById(book.authors), categoryRepository.findAllById(book.categories));
		repository.save(result);
		return ResponseEntity.ok(result);
	}

	// PUT update a book
	@PutMapping("/api/v1/books/{id}")
	public ResponseEntity<?> updateBook(@RequestBody Optional<BookDTO> book, @PathVariable("id") long id) {
		Optional<Book> bookData = repository.findById(id);

		if (bookData.isPresent() && book.isPresent()) {
			Book _book = bookData.get();
			BookDTO updatedBook = book.get();
			if (updatedBook.countIterables(updatedBook.authors)>0)
				_book.setAuthors(new HashSet<>(authorRepository.findAllById(updatedBook.getAuthors())));
			_book.setisbn(updatedBook.getISBN());
			_book.setTitle(updatedBook.getTitle());
			_book.setPublisher(updatedBook.getPublisher());
			if (updatedBook.countIterables(updatedBook.categories)>0)
				_book.setGenres(categoryRepository.findAllById(updatedBook.getCategories()));
			_book.setDescription(updatedBook.getDescription());
			_book.setDateOfPublishing(updatedBook.getDateOfPublishing());
			System.out.println("updated book to: "+_book);
			repository.save(_book);
			return ResponseEntity.ok(HttpStatus.OK);
		} else {
			return ResponseEntity.badRequest().body(HttpStatus.NOT_FOUND);
		}
	}

	// DELETE delete a book
	@DeleteMapping("/api/v1/books/{id}")
	public ResponseEntity<?> deleteBook(@PathVariable("id") long id) {
		Optional<Book> bookData = repository.findById(id);
		if(bookData.isPresent())
		{
			repository.deleteById(id);
			return ResponseEntity.ok("Book with the ID of "+id+" deleted");
		}
		else {
			return ResponseEntity.badRequest().body("Book not found with the ID of "+id);
		}
	}

}
