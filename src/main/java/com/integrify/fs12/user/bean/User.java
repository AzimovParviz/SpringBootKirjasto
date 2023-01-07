package com.integrify.fs12.user.bean;

import java.util.Set;

import com.integrify.fs12.book.bean.Book;

import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "Users")
public class User {

	@Id
	@GeneratedValue
	private long id;
	@NonNull
	private String username;
	@Column(unique = true)
	private String email;
	@NonNull
	private String passwordHash;

	@OneToMany
	private Set<Book> borrowedBooks;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void borrowBooks(Set<Book> booksToBorrow) {
		booksToBorrow.forEach((book) -> this.borrowedBooks.add(book));
	}

	public void returnBooks(Set<Book> booksToBorrow) {
		booksToBorrow.forEach((book) -> this.borrowedBooks.remove(book));
	}

	public User() {
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", email=" + email + "]";
	}

	public User(String username, String passwordHash, String email) {
		this.username = username;
		this.passwordHash = passwordHash;
		this.email = email;
	}
}
