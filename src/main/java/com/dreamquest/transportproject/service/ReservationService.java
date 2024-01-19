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
		query.setParameter("specificDate", Date.valueOf("2023-12-23"));
		System.out.println("From ReservationService::getEmployeesPerDate() "+query.getResultList());
//		return query.getResultList();
		return null;
	}
	
	@Transactional
	public  Reservation fetchEmailReservationsForDay(String email)
	{
		String jpql = "SELECT d FROM Employee s JOIN s.reservations d WHERE s.email = :email AND d.reservatinDate = :specificDate";
//		String jpql = "SELECT d FROM Employee s JOIN s.reservations d WHERE d.reservatinDate = :specificDate";

		TypedQuery<Reservation> query = entityManager.createQuery(jpql, Reservation.class);
		query.setParameter("email",email);
		query.setParameter("specificDate", Date.valueOf("2023-12-23"));
		System.out.println("From ReservationService::getEmployeesPerDate() "+query.getResultList());

		
		if(query.getSingleResult() == null)
			return null; 
		return query.getSingleResult();
	}
	
	@Transactional
	public Reservation getReservationByDestination(String destination)
	{
		
		Reservation reservation =  reservationDAO.findByDestinationAndReservatinDate(destination,Date.valueOf(currentDate));
		
		return reservation;
	}

	
	
}

