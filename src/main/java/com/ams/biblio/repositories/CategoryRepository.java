package com.ams.biblio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ams.biblio.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
