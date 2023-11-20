package com.dreamquest.transportproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.dreamquest.transportproject.entity.Employee;
import com.dreamquest.transportproject.entity.Role;
import com.dreamquest.transportproject.service.EmployeeService;
import com.dreamquest.transportproject.service.ReservationService;
import com.dreamquest.transportproject.service.RoleService;

@Controller
public class Webcontroller {

	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	ReservationService reservationService;
	
	@Autowired
	RoleService roleService;

	@GetMapping("/showRegistrationForm")
	public String showRegistrationForm(Model model)
	{
		model.addAttribute("employee", new Employee()); 
		return "customer-registration";
	}
	
	@PostMapping("/register")
	public String registerCustomer(@ModelAttribute(name ="employee") Employee employee)
	{
		
		
		Role role = roleService.findByRole(employee.getFormRole());
		
		employee.setRole(role);
		
		employeeService.save(employee);
		
		System.out.println(role.toString());
		
		
		return "redirect:/success";
	}
	
	@GetMapping("/success")
	public String successPage() {
		
		return "success";
	}
	
	
}
