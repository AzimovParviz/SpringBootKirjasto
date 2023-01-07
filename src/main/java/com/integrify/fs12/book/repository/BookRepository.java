package com.integrify.fs12.book.repository;

import com.integrify.fs12.book.bean.Book;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long>{

}
