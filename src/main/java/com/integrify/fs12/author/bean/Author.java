package com.integrify.fs12.author.bean;

@javax.persistence.Entity(name = "Authors")
public class Author {
	@javax.persistence.Id
	@javax.persistence.GeneratedValue
	private long id;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Author() {
	}

	public Author(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Author [name=" + name + "]";
	}

}
