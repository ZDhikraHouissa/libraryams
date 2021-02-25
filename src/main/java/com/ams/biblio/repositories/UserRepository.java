package com.ams.biblio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ams.biblio.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	User findByEmail(String email);
}
