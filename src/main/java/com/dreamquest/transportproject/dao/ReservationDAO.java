package com.dreamquest.transportproject.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dreamquest.transportproject.entity.Reservation;

public interface ReservationDAO extends JpaRepository<Reservation, Long> {

	
}
