package com.reservation.inventoryservice.repository;

import com.reservation.inventoryservice.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route, Integer> {
}