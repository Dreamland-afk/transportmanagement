package com.dreamquest.transportproject.service;


import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreamquest.transportproject.dao.ReservationDAO;
import com.dreamquest.transportproject.entity.Employee;
import com.dreamquest.transportproject.entity.Reservation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service
public class ReservationService {
	@Autowired
	ReservationDAO reservationDAO;

	@Autowired
	EntityManager entityManager;

	@Transactional
	public void save(Reservation reservation) {
		reservationDAO.save(reservation);
	}

	@Transactional
	public List<Employee> getEmployeesPerDate() {
		
		String jpql = "SELECT s FROM Employee s JOIN s.reservations d WHERE d.destination = :destinationName AND d.reservatinDate = :specificDate";
		TypedQuery<Employee> query = entityManager.createQuery(jpql, Employee.class);
		query.setParameter("destinationName","Dubai");
		query.setParameter("specificDate", Date.valueOf("2023-11-06"));
		System.out.println("HEre"+query.getFirstResult());
		return query.getResultList();
	}

}

