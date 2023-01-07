package com.integrify.fs12.author.repository;

import com.integrify.fs12.author.bean.Author;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
