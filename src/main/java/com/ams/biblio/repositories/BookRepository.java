package com.ams.biblio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ams.biblio.entities.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

}
