package com.dreamquest.transportproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.dreamquest.transportproject.entity.Employee;

@Controller
public class Webcontroller {


	@GetMapping("/showRegistrationForm")
	public String showRegistrationForm(Model model)
	{
		model.addAttribute("employee", new Employee()); 
		return "customer-registration";
	}
	
	@PostMapping("/register")
	public String registerCustomer(@ModelAttribute(name ="employee") Employee employee)
	{
		System.out.println(employee.toString());
		return "redirect:/success";
	}
}
