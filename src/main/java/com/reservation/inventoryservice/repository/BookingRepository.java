package com.reservation.inventoryservice.repository;

import com.reservation.inventoryservice.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
}