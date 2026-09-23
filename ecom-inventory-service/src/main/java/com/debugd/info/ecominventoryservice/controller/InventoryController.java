package com.debugd.info.ecominventoryservice.controller;

import com.debugd.info.ecominventoryservice.service.InventoryService;
import com.debugd.info.ecominventoryservice.model.Inventory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/{productId}")
    public Inventory checkInventory(@PathVariable Long productId) {
        return inventoryService.checkStock(productId);
    }

    @PostMapping
    public String addProduct(@RequestBody Inventory inventory){
        return inventoryService.addProduct(inventory);
    }

    @PutMapping()
    public String updateProduct(
            @RequestBody Inventory inventory){
        return inventoryService.updateProduct(inventory);
    }

    @DeleteMapping("/{productId}")
    public String deleteProduct(@PathVariable Long productId){
        return inventoryService.deleteProduct(productId);
    }
}
