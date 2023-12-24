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
	
	private String currentDate = java.time.LocalDate.now().toString();

	@Transactional
	public void save(Reservation reservation) {
		reservationDAO.save(reservation);
	}

	@Transactional
	public List<Employee> getEmployeesPerDate() {
		
		String jpql = "SELECT s FROM Employee s JOIN s.reservations d WHERE d.destination = :destinationName AND d.reservatinDate = :specificDate";
		TypedQuery<Employee> query = entityManager.createQuery(jpql, Employee.class);
		query.setParameter("destinationName","Ultadanga");
		query.setParameter("specificDate", Date.valueOf(currentDate));
		System.out.println("From ReservationService::getEmployeesPerDate() "+query.getFirstResult());
		return query.getResultList();
	}
	
	public Reservation getReservationByDestination(String name)
	{
		
		Reservation reservation =  reservationDAO.findByDestinationAndReservatinDate(name,Date.valueOf(currentDate));
		
		return reservation;
	}

	
	
}

