package com.dreamquest.transportproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.dreamquest.transportproject.entity.Employee;
import com.dreamquest.transportproject.entity.Reservation;
import com.dreamquest.transportproject.entity.Role;
import com.dreamquest.transportproject.service.EmployeeService;
import com.dreamquest.transportproject.service.ReservationService;
import com.dreamquest.transportproject.service.RoleService;

import jakarta.validation.Valid;

@Controller
public class Webcontroller {

	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	ReservationService reservationService;
	
	@Autowired
	RoleService roleService;
	
	@Value("${distinationList}")
	List<String> destinationList;

	@GetMapping("/showRegistrationForm")
	public String showRegistrationForm(Model model)
	{
		model.addAttribute("employee", new Employee()); 
		return "customer-registration";
	}
	
	@PostMapping("/register")
	public String registerCustomer( @Valid @ModelAttribute(name ="employee") Employee employee, BindingResult bindingResult)
	{
		
		if(bindingResult.hasErrors())
		{
			System.out.println("Has errors," + bindingResult.getFieldError());
			return "customer-registration";
		}
		
		Role role = roleService.findByRole(employee.getFormRole());
		
		employee.setRole(role);
		
		employeeService.save(employee);
		
		System.out.println(role.toString());
		
		
		return "redirect:/success";
	}
	
	@GetMapping("/bookaseat")
	public String bookSeat(Model model)
	{
		model.addAttribute("options", destinationList);
		model.addAttribute("reservation", new Reservation());
		return "reservation-form";
	}
	
	@GetMapping("/success")
	public String successPage() {
		
		return "success";
	}
	
	@PostMapping("/reservation")
	public String register(@ModelAttribute(name = "reservation") Reservation reservation) throws Exception {
		
		System.out.println(reservation.toString());
		
		Reservation fetchReservation = reservationService.getReservationByDestination(reservation.getDestination());
		
		System.out.println("Reservation fetching successfull");
		
		if(fetchReservation == null)
			fetchReservation = new Reservation(reservation.getDestination());
		
		employeeService.bookASpot(3L, fetchReservation);
		
		return "success";
		
	}
	
	@GetMapping("/test")
	public String main() throws Exception
	{
//		reservationService.getReservationByDestination("Ultadanga");
//		System.out.println(employeeService.findById(3l));
//		
//		Reservation fetchReservation = new Reservation("Delhi");
//		employeeService.bookASpot(3L, fetchReservation);
		
		System.out.println(reservationService.getEmployeesPerDate());
		
		return "success";
		
	}
	
}
