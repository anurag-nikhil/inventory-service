package com.reservation.inventoryservice;

import com.reservation.inventoryservice.model.Inventory;
import com.reservation.inventoryservice.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class InventoryController {

    private final InventoryService busInventoryService;
    Logger logger = LoggerFactory.getLogger(InventoryController.class);

    public InventoryController(InventoryService busInventoryService) {
        this.busInventoryService = busInventoryService;
    }


    @PostMapping("/buses/inventories")
    ResponseEntity<Inventory> saveBusInventory(@RequestBody Inventory busInventory) {
        logger.debug("saveBusInventory:/buses/inventories {}",busInventory);
        Inventory busInventoryDetail = busInventoryService.saveBusInventory(busInventory);
        return ResponseEntity.status(HttpStatus.CREATED).body(busInventoryDetail);
    }

    @PutMapping("/buses/inventories")
    ResponseEntity<Inventory> updateBusInventory(@RequestBody Inventory busInventory) {
        logger.debug("updateBusInventory:/buses/inventories {}",busInventory);
        Inventory busInventoryDetail = busInventoryService.saveBusInventory(busInventory);
        return ResponseEntity.status(HttpStatus.OK).body(busInventoryDetail);
    }

    @GetMapping("/buses/inventories")
    ResponseEntity<List<Inventory>> getAllBusInventories() {
        logger.debug("getAllBusInventories:/buses/inventories");
        List<Inventory> busInventoryDetail = busInventoryService.getAllBusesInventory();
        return ResponseEntity.status(HttpStatus.OK).body(busInventoryDetail);
    }

    @GetMapping("/buses/{bus_id}/inventories")
    ResponseEntity<Inventory> getBusInventory(@PathVariable("bus_id") Integer busId) {
        logger.debug("getBusInventory:/buses/{}/inventories",busId);
        return ResponseEntity.status(HttpStatus.OK).body(busInventoryService.getBusInventory(busId));
    }
}
