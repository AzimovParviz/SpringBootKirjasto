package com.integrify.fs12.user.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.integrify.fs12.book.bean.Book;
import com.integrify.fs12.book.bean.Book.BorrowStatus;
import com.integrify.fs12.book.repository.BookRepository;
import com.integrify.fs12.user.bean.User;
import com.integrify.fs12.user.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Autowired
	private UserRepository repository;
	@Autowired
	private BookRepository bookRepository;

	// GET all users
	@GetMapping("/api/v1/users")
	public List<User> getAllUsers() {
		return repository.findAll();
	}

	// GET a single user
	@GetMapping("/api/v1/users/{id}")
	public User getUserById(@PathVariable long id) {
		Optional<User> user = repository.findById(id);
		if (user.isEmpty()) {
			throw new RuntimeException("User not found with the ID of " + id);
		}

		return user.get();
	}

	@GetMapping("/api/v1/users/current")
	public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
		return Collections.singletonMap("name", principal.getAttribute("name"));
	}

	// POST a new user / I guess counts as signUp?
	@PostMapping("/api/v1/users")
	public void createUser(@RequestBody User user) {
		repository.save(user);
	}

	// POST borrow a book
	@PostMapping("/api/v1/users/borrowBook/{id}")
	public void borrowBook(@RequestBody ArrayList<Long> bookIds, @PathVariable long id) {
		Optional<User> user = repository.findById(id);
		Set<Book> booksToBorrow = new HashSet<>(bookRepository.findAllById(bookIds));
		if (user.isEmpty()) {
			throw new RuntimeException("User not found with the ID of " + id);
		}

		User _user = user.get();
		_user.borrowBooks(booksToBorrow);
		for (Book b : booksToBorrow) {
			b.setBorrowedAt(new Timestamp(System.currentTimeMillis()));
			b.setStatus(BorrowStatus.BORROWED);
			bookRepository.save(b);
		}
		repository.save(_user);
	}

	// POST return a book
	@PostMapping("/api/v1/users/returnBook/{id}")
	public void returnBook(@RequestBody ArrayList<Long> bookIds, @PathVariable long id) {
		Optional<User> user = repository.findById(id);
		Set<Book> booksToReturn = new HashSet<>(bookRepository.findAllById(bookIds));
		if (user.isEmpty()) {
			throw new RuntimeException("User not found with the ID of " + id);
		}

		User _user = user.get();
		_user.returnBooks(booksToReturn);
		for (Book b : booksToReturn) {
			b.setStatus(BorrowStatus.AVAILABLE);
			bookRepository.save(b);
		}
		repository.save(_user);
	}

	// PUT update an user
	@PutMapping("/api/v1/users/{id}")
	public ResponseEntity<?> updateUser(@RequestBody Optional<User> user, @PathVariable("id") long id) {
		Optional<User> userData = repository.findById(id);
		if (userData.isPresent()) {
			User _user = userData.get();
			repository.save(_user);
			return ResponseEntity.ok("User updated" + _user);
		} else {
			return ResponseEntity.badRequest().body("Failed to find an user by ID of" + id);
		}
	}

	// DELETE delete a user
	@DeleteMapping("/api/v1/users/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
		Optional<User> userData = repository.findById(id);
		if (userData.isPresent()) {
			repository.deleteById(id);
			return ResponseEntity.ok("User with the ID of " + id + " deleted");
		} else {
			return ResponseEntity.badRequest().body("User not found with the ID of " + id);
		}
	}
}
