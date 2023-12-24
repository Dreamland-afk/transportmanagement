package com.dreamquest.transportproject.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ArcEmailValidator implements ConstraintValidator<ArcEmail, String>{

	private String domain;
	
	public  void initialize(ArcEmail constraintAnnotation) {
		domain = constraintAnnotation.domain();
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value != null && value.endsWith(domain))
		{
			return true;
		}
		return false;
	}

	

}
