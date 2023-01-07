package com.integrify.fs12.book.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.integrify.fs12.author.bean.Author;
import com.integrify.fs12.book.bean.Book;
import com.integrify.fs12.category.bean.Category;

public class BookDTO {
	private String title;
	private String ISBN;
	public Iterable<Long> authors;
	private String description;
	public Iterable<Long> categories;
	private Date dateOfPublishing;
	private String publisher;

	public Book convertToBook(List<Author> authors, List<Category> categories) {
		Book book = new Book(
				this.title,
				this.ISBN,
				new HashSet<Author>(authors),
				this.description,
				new ArrayList<Category>(categories),
				this.dateOfPublishing,
				this.publisher);
		return book;
	}

	public int countIterables(Iterable<Long> iter) {
		int counter = 0;
		if (iter == null) {
			return 0;	
		}
		for (Object i : iter) {
			counter++;
		}
		return counter;
	}

	@Override
	public String toString() {
		return "BookDTO [title=" + title + ", ISBN=" + ISBN + ", authors=" + authors + ", description="
				+ description
				+ ", categories=" + categories + ", dateOfPublishing=" + dateOfPublishing + ", publisher="
				+ publisher + "]";
	}

	public BookDTO(String title, String iSBN, Iterable<Long> authors, String description, Iterable<Long> categories,
			Date dateOfPublishing, String publisher) {
		this.title = title;
		ISBN = iSBN;
		this.authors = authors;
		this.description = description;
		this.categories = categories;
		this.dateOfPublishing = dateOfPublishing;
		this.publisher = publisher;
	}

	public Iterable<Long> getAuthors() {
		return authors;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public void setAuthors(Iterable<Long> authors) {
		this.authors = authors;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Iterable<Long> getCategories() {
		return categories;
	}

	public void setGenres(Iterable<Long> categories) {
		this.categories = categories;
	}

	public Date getDateOfPublishing() {
		return dateOfPublishing;
	}

	public void setDateOfPublishing(Date dateOfPublishing) {
		this.dateOfPublishing = dateOfPublishing;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
}
