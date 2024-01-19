package com.dreamquest.transportproject.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dreamquest.transportproject.dao.EmployeeDAO;
import com.dreamquest.transportproject.entity.Employee;

@Service
public class EmployeUserDetailServiceImpl implements UserDetailsService {

	@Autowired
	EmployeeDAO employeeDAO;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 Employee user = employeeDAO.findByEmail(username);
         if(user==null){
                new UsernameNotFoundException("User not exists by Username");
              }
         
         Set<GrantedAuthority> authorities = new HashSet<>();
         
         authorities.add(new SimpleGrantedAuthority(user.getRole().getDesignation()));
       
        		 
         return new org.springframework.security.core.userdetails.User(username,user.getPassword(),authorities);
	}

}
