package com.dreamquest.transportproject.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.dreamquest.transportproject.service.EmployeUserDetailServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	EmployeUserDetailServiceImpl employeUserDetailServiceImpl;

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService());
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	@Bean
	public SecurityFilterChain securityChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((authorize) -> authorize

				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
				.requestMatchers("/transport/registration").permitAll()
				.requestMatchers("/transport/register").permitAll()
				.requestMatchers("/transport/success").permitAll()
				.requestMatchers("/transport/login").permitAll()
				
				.requestMatchers("/test").hasAuthority("Admin")

				.anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/transport/login").loginProcessingUrl("/transport/login-processing")
						.defaultSuccessUrl("/transport/bookaseat"))
				.logout((logout) -> logout.invalidateHttpSession(true)
//						.logoutUrl("/logout")
//				        .logoutSuccessUrl("/login").deleteCookies("JSESSIONID")
						.permitAll());
//				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//						.invalidSessionUrl("/invalidSession.html").maximumSessions(1).maxSessionsPreventsLogin(true));

		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {

		return employeUserDetailServiceImpl;
	}

}
