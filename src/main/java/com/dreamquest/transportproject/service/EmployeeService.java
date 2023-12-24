package com.dreamquest.transportproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreamquest.transportproject.dao.EmployeeDAO;
import com.dreamquest.transportproject.entity.Employee;
import com.dreamquest.transportproject.entity.Reservation;

import jakarta.transaction.Transactional;

@Service
public class EmployeeService {

	@Autowired
	EmployeeDAO employeeDAO;

	@Transactional
	public void save(Employee employee) {
		Employee employeereturn = employeeDAO.save(employee);

		System.out.println("Reservations for the employee: " + employeereturn.getReservations());

	}

	public Employee findById(Long id) {

		Optional<Employee> optional = employeeDAO.findById(id);

		if (optional.isEmpty()) {
			System.out.println("Not Found");
			return null;
		}
		return optional.get();

	}

	@Transactional
	public List<Reservation> findByIdWithReservations(Long id) {

		Optional<Employee> optional = employeeDAO.findById(id);

		if (optional.isEmpty()) {
			System.out.println("Not Found");
			return null;
		}
		return optional.get().getReservations();

	}

	@Transactional
	public String bookASpot(Long id, Reservation reservation) throws Exception {

		System.out.println("Finding employee");
		Optional<Employee> optional = employeeDAO.findById(id);

		if (optional.isEmpty()) {
			System.out.println("Employee Not Found");
			throw new Exception("Employee not found");
		}

		Employee employee = optional.get();

		employee.bookSeat(reservation);

		save(employee);

		return "Reservation Successful.";

	}

}
