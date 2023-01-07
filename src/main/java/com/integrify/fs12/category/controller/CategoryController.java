package com.integrify.fs12.category.controller;

import java.util.List;
import java.util.Optional;

import com.integrify.fs12.category.bean.Category;
import com.integrify.fs12.category.repository.CategoryRepository;

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
public class CategoryController {

	@Autowired
	private CategoryRepository repository;

	// GET all categories
	@GetMapping("/api/v1/categories")
	public List<Category> getAllCategorys() {
		return repository.findAll();
	}

	// GET a single category
	@GetMapping("/api/v1/categories/{id}")
	public Category getCategoryById(@PathVariable long id) {
		Optional<Category> category = repository.findById(id);
		if (category.isEmpty()) {
			throw new RuntimeException("Category not found with the ID of " + id);
		}

		return category.get();
	}

	// POST a new category
	@PostMapping("/api/v1/categories")
	public void createCategory(@RequestBody Category category) {
		repository.save(category);
	}

	// PUT update an category
	@PutMapping("/api/v1/categories/{id}")
	public ResponseEntity<?> updateCategory(@RequestBody Optional<Category> category, @PathVariable("id") long id) {
		Optional<Category> categoryData = repository.findById(id);

		if (categoryData.isPresent()) {
			Category _category = new Category(categoryData.get().getName());
			repository.save(_category);
			return ResponseEntity.ok("Category updated" + _category);
		} else {
			return ResponseEntity.badRequest().body("Failed to find an category by ID of " + id);
		}
	}

	// DELETE delete a category
	@DeleteMapping("/api/v1/categories/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable("id") long id) {
		Optional<Category> categoryData = repository.findById(id);
		if (categoryData.isPresent()) {
			repository.deleteById(id);
			return ResponseEntity.ok("Category with the ID of " + id + " deleted");
		} else {
			return ResponseEntity.badRequest().body("Category not found with the ID of " + id);
		}
	}
}
