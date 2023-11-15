package com.dreamquest.transportproject.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dreamquest.transportproject.entity.Employee;

public interface EmployeeDAO extends JpaRepository<Employee, Long> {

}
