package com.integrify.fs12.author.controller;

import java.util.List;
import java.util.Optional;

import com.integrify.fs12.author.bean.Author;
import com.integrify.fs12.author.repository.AuthorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {

	@Autowired
	private AuthorRepository repository;

	// GET all authors
	@GetMapping("/api/v1/authors")
	public List<Author> getAllAuthors() {
		return repository.findAll();
	}

	// GET a single author
	@GetMapping("/api/v1/authors/{id}")
	public Author getAuthorById(@PathVariable long id) {
		Optional<Author> author = repository.findById(id);
		if (author.isEmpty()) {
			throw new RuntimeException("Author not found with the ID of " + id);
		}

		return author.get();
	}

	// POST a new author
	@PostMapping("/api/v1/authors")
	public void createAuthor(@RequestBody Author author) {
		repository.save(author);
	}
	
	// PUT update an author
	@PutMapping("/api/v1/authors/{id}")
	public ResponseEntity<?> updateAuthor(@RequestBody Optional<Author> author, @PathVariable("id") long id) {
		Optional<Author> authorData = repository.findById(id);

		if(authorData.isPresent()) {
			Author _author = new Author(authorData.get().getName());
			repository.save(_author);
			return ResponseEntity.ok("Author updated" + _author);
		}
		else {
			return ResponseEntity.badRequest().body("Failed to find an author by ID of "+id);
		}
	}

	// DELETE delete a author
	@DeleteMapping("/api/v1/authors/{id}")
	public ResponseEntity<?> deleteAuthor(@PathVariable("id") long id) {
		Optional<Author> authorData = repository.findById(id);
		if(authorData.isPresent())
		{
			repository.deleteById(id);
			return ResponseEntity.ok("Author with the ID of "+id+" deleted");
		}
		else {
			return ResponseEntity.badRequest().body("Author not found with the ID of "+id);
		}
	}
}
