package com.ams.biblio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ams.biblio.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
