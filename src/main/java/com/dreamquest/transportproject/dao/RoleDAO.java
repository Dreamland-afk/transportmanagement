package com.dreamquest.transportproject.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dreamquest.transportproject.entity.Role;

public interface RoleDAO extends JpaRepository<Role, Long> {
	
	Role findByDesignation(String role);

}
