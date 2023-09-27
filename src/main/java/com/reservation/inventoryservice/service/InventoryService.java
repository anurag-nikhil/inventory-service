package com.reservation.inventoryservice.service;

import com.reservation.inventoryservice.exception.BusResourceNotFoundException;
import com.reservation.inventoryservice.repository.BookingRepository;
import com.reservation.inventoryservice.repository.InventoryRepository;
import com.reservation.inventoryservice.repository.RouteRepository;
import com.reservation.inventoryservice.model.Inventory;
import com.reservation.inventoryservice.model.Route;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.jms.annotation.JmsListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class InventoryService {

    private final InventoryRepository busInventoryRepository;

   // private final MessageBroker messageBroker;
    private final RouteRepository busRouteRepository;

    private final BookingRepository bookingRepository;
    private final ObjectMapper objectMapper;
    Logger logger = LoggerFactory.getLogger(InventoryService.class);

    public InventoryService(InventoryRepository busInventoryRepository, RouteRepository busRouteRepository, BookingRepository bookingRepository, ObjectMapper objectMapper) {
        this.busInventoryRepository = busInventoryRepository;
        this.busRouteRepository = busRouteRepository;
        this.bookingRepository = bookingRepository;
        this.objectMapper = objectMapper;
    }


    public Inventory saveBusInventory(Inventory busInventory) {
        logger.debug("saveBusInventory: {}",busInventory);
        return busInventoryRepository.saveAndFlush(busInventory);
    }

    public List<Inventory> getAllBusesInventory() {
        logger.debug("getAllBusesInventory");
        return busInventoryRepository.findAll();
    }

    public Inventory getBusInventory(Integer busId) {
        logger.debug("getBusInventory {}",busId);
        Inventory busInventory = busInventoryRepository.findByBusId(busId).orElse(null);
        if (busInventory == null) {
            initBusInventory(busId);
        }
        return busInventoryRepository.findByBusId(busId).orElse(null);
    }

    private void initBusInventory(Integer busId) {
        logger.debug("initBusInventory {}",busId);
        Route busRouteDetail = busRouteRepository.findById(busId).orElse(null);
        if (busRouteDetail != null) {
            Inventory busInventoryNew = new Inventory();
            busInventoryNew.setBusId(busRouteDetail.getId());
            busInventoryNew.setAvailableSeats(busRouteDetail.getTotalSeats());
            busInventoryNew.setLastUpdatedDate(LocalDateTime.now());
            busInventoryRepository.saveAndFlush(busInventoryNew);
        } else {
            logger.debug("initBusInventory: Bus detail of id {} not found",busId);
            throw new BusResourceNotFoundException(String.format("Bus detail of id %d not found", busId));
        }
    }

   // @JmsListener(destination = MessageDestinationConst.DEST_UPDATE_INVENTORY)
//    public void updateBusInventory(Map<String, Object> object) {
//
//        final BusBookingMessage busBookingMessage = objectMapper.convertValue(object, BusBookingMessage.class);
//        logger.debug("updateBusInventory {}",busBookingMessage);
//
//        BusInventory busInventory = busInventoryRepository
//                .findByBusId(busBookingMessage.getBusId()).orElse(null);
//
//        Booking booking = bookingRepository.findById(busBookingMessage.getBookingId()).orElse(null);
//
//        if (busInventory == null) {
//            initBusInventory(busBookingMessage.getBusId());
//            busInventory = busInventoryRepository
//                    .findByBusId(busBookingMessage.getBusId()).orElse(null);
//        }
//
//        busInventory.setAvailableSeats(busInventory.getAvailableSeats() - booking.getNoOfSeats());
//        busInventory.setLastUpdatedDate(LocalDateTime.now());
//        busInventoryRepository.saveAndFlush(busInventory);
//        messageBroker.sendConfirmBookingMessage(MessageDestinationConst.DEST_UPDATE_BOOKING, busBookingMessage);
//    }
}
