package com.debugd.info.ecomorderservice.service;

import com.debugd.info.ecomorderservice.client.config.InventoryClient;
import com.debugd.info.ecomorderservice.dto.Inventory;
import com.debugd.info.ecomorderservice.service.InventoryService;
import com.netflix.appinfo.InstanceInfo;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Service
public class OrderService {
    private final InventoryClient inventoryClient;
    private  final RestTemplate restTemplate;
    private final RestClient restClient;
    private final DiscoveryClient discoveryClient;
    private final InventoryService inventoryService;

    public OrderService(InventoryClient inventoryClient, RestTemplate restTemplate, RestClient restClient, DiscoveryClient discoveryClient, InventoryService inventoryService, InventoryService inventoryService1) {
        this.inventoryClient = inventoryClient;
        this.restTemplate = restTemplate;
        this.restClient = restClient;
        this.discoveryClient = discoveryClient;
        this.inventoryService = inventoryService1;
    }

    public String placeOrder(Long productId){

        Inventory inventory = inventoryService.getInventory(productId);
        int quantity = inventory.getQuantity();
        if (quantity > 0) {
            updateInventory(inventory);
        }

        return  quantity>0?
                "Order Placed Successfully":
                "Product Out Of Stock";
    }


    private void updateInventory(Inventory inventory) {
        inventory.setQuantity(inventory.getQuantity()-1);
        inventoryClient.updateInventory(inventory);

    }


}