package com.integrify.fs12.book.bean;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.integrify.fs12.author.bean.Author;
import com.integrify.fs12.category.bean.Category;

@Entity(name = "Books")
public class Book {

	@Id
	@GeneratedValue
	private long id;

	private String title;
	private String isbn;

	@ManyToMany(targetEntity = Author.class, cascade = CascadeType.PERSIST)
	private Set<Author> Authors;

	private String description;

	@ManyToMany(targetEntity = Category.class, cascade = CascadeType.PERSIST)
	private List<Category> genres;
	
	private Date dateOfPublishing;
	private String publisher;
	private BorrowStatus status;
	private Timestamp borrowedAt;
	private Timestamp returnAt;

	public enum BorrowStatus {
		AVAILABLE,
		BORROWED
	}

	// with ID
	public Book(long id, String title, String isbn, Set<Author> authors, String description,
			List<Category> genres,
			Date dateOfPublishing, String publisher, Timestamp borrowedAt, Timestamp returnAt) {
		this.id = id;
		this.title = title;
		this.isbn = isbn;
		this.Authors = authors;
		this.description = description;
		this.genres = genres;
		this.dateOfPublishing = dateOfPublishing;
		this.publisher = publisher;
		this.status = BorrowStatus.AVAILABLE;
		this.borrowedAt = borrowedAt;
		this.returnAt = returnAt;
	}

	// without ID
	public Book(String title, String isbn, Set<Author> authors, String description, List<Category> genres,
			Date dateOfPublishing, String publisher) {
		this.title = title;
		this.isbn = isbn;
		this.Authors = authors;
		this.description = description;
		this.genres = genres;
		this.dateOfPublishing = dateOfPublishing;
		this.publisher = publisher;
		this.status = BorrowStatus.AVAILABLE;
	}

	// default constructor
	public Book() {

	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Date getDateOfPublishing() {
		return dateOfPublishing;
	}

	public String getPublisher() {
		return publisher;
	}

	public Timestamp getBorrowedAt() {
		return borrowedAt;
	}

	public Timestamp getReturnAt() {
		return returnAt;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDateOfPublishing(Date dateOfPublishing) {
		this.dateOfPublishing = dateOfPublishing;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public void setBorrowedAt(Timestamp borrowedAt) {
		this.borrowedAt = borrowedAt;
	}

	public void setReturnAt(Timestamp returnAt) {
		this.returnAt = returnAt;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", description=" + description + ", dateOfPublishing="
				+ dateOfPublishing + ", publisher=" + publisher + ", borrowedAt=" + borrowedAt
				+ ", returnAt="
				+ returnAt + "]";
	}

	public String getisbn() {
		return isbn;
	}

	public void setisbn(String isbn) {
		this.isbn = isbn;
	}

	public Set<Author> getAuthors() {
		return Authors;
	}

	public void setAuthors(Set<Author> authors) {
		Authors = authors;
	}

	public List<Category> getGenres() {
		return genres;
	}

	public void setGenres(List<Category> genres) {
		this.genres = genres;
	}

	public BorrowStatus getStatus() {
		return status;
	}

	public void setStatus(BorrowStatus status) {
		this.status = status;
	}
}
