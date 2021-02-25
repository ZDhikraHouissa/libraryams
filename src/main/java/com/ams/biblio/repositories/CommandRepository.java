package com.ams.biblio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ams.biblio.entities.Command;

public interface CommandRepository extends JpaRepository<Command, Integer> {

}
