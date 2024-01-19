package com.dreamquest.transportproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dreamquest.transportproject.entity.Employee;
import com.dreamquest.transportproject.entity.Reservation;
import com.dreamquest.transportproject.entity.Role;
import com.dreamquest.transportproject.service.EmployeeService;
import com.dreamquest.transportproject.service.ReservationService;
import com.dreamquest.transportproject.service.RoleService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/transport")
public class Webcontroller {

	@Autowired
	EmployeeService employeeService;

	@Autowired
	ReservationService reservationService;

	@Autowired
	RoleService roleService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Value("${distinationList}")
	List<String> destinationList;

	@GetMapping("/login")
	public String loginForm() {
		return "login-page";
	}

	@GetMapping("/registration")
	public String showRegistrationForm(Model model) {
		model.addAttribute("employee", new Employee());
		return "customer-registration";
	}

	@PostMapping("/register")
	public String registerCustomer(@Valid @ModelAttribute(name = "employee") Employee employee,
			BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			System.out.println("Has errors: " + bindingResult.getFieldError());
			return "redirect:/registration";
			
		} else {
			if (!employee.getPassword().equals(employee.getConfirmPassword())) {
				// Passwords do not match
				bindingResult.rejectValue("confirmPassword", "error.employee", "Passwords do not match");
				return "redirect:/registration";
			}

			Role role = roleService.findByRole(employee.getFormRole());
			if (role == null) {
				// Handle the case when the role is not found
				bindingResult.rejectValue("formRole", "error.employee", "Role not found");
				return "redirect:/registration";
			}

			employee.setRole(role);
			employee.setPassword(passwordEncoder.encode(employee.getPassword()));
			employeeService.save(employee);

			System.out.println(role.toString());

			// Add a success message to be displayed on the success page
//			redirectAttributes.addFlashAttribute("successMessage", "Registration successful!");

			return "redirect:/transport/success";
		}
	}

	@GetMapping("/bookaseat")
	public String bookSeat(Model model) {
		// Retrive the email from the security session object then do the processing.

		// Add it to the model

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

		if (fetchReservation == null)
			fetchReservation = new Reservation(reservation.getDestination());

		employeeService.bookASpot(3L, fetchReservation);

		// Here I need to add the destination

		return "success";

	}

	@GetMapping("/test")
	public String main(Model model) throws Exception {
//		reservationService.getReservationByDestination("Ultadanga");
//		System.out.println(employeeService.findById(3l));
//		
//		Reservation fetchReservation = new Reservation("Delhi");
//		employeeService.bookASpot(3L, fetchReservation);
//		List<Employee> employees = reservationService.getEmployeesPerDate();
//		System.out.println(employees);
//		
//		model.addAttribute("employeeList", employees);
//		model.addAttribute("sessionUser", "swapnadeep407@e-arc.com");
//		
//		return "reservation_list";

		reservationService.fetchEmailReservationsForDay("swapnadeep407@e-arc.com");

		return "success";
	}

	@GetMapping("/removeReservation")
	public String deleteReservation(@RequestParam(value = "email") String email) {
		System.out.println("Deleted requested for email: " + email);
		return "redirect:/reservation_list";
	}

}
