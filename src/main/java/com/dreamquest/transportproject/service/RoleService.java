package com.dreamquest.transportproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreamquest.transportproject.dao.RoleDAO;
import com.dreamquest.transportproject.entity.Role;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class RoleService {

	@Autowired
	RoleDAO roleDAO;
	
	@Autowired
	EntityManager entityManager;

	
	
	@Transactional
	public void save(Role role) {
		Role getrole =  roleDAO.save(role);
		System.out.println(getrole.getDesignation());
		
	}

	public Role findByRole(String string) {
		
		Role role = roleDAO.findByDesignation(string);
		
		if(role == null)
			{
				role = roleDAO.save(new Role(string));
//				System.out.println("Role" + role.toString());
				
			}
		
		return role;
	}
}
