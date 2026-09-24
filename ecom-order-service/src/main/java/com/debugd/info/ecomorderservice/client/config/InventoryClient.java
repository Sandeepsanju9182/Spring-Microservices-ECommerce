package com.debugd.info.ecomorderservice.client.config;

import com.debugd.info.ecomorderservice.dto.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ecom-inventory-service", configuration = InventoryFeignClientConfig.class)
public interface InventoryClient {
    @GetMapping("/inventory/{productId}")
    Inventory getInventory(@PathVariable String  productId);

    @PutMapping("/inventory")
    String updateInventory(@RequestBody Inventory inventory);
}
