package com.debugd.info.ecomorderservice.service;


import com.debugd.info.ecomorderservice.client.config.InventoryClient;
import com.debugd.info.ecomorderservice.dto.Inventory;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class InventoryService {

    private final InventoryClient inventoryClient;

    public InventoryService(InventoryClient inventoryClient) {
        this.inventoryClient = inventoryClient;
    }

    /* @Retryable(
             retryFor = RuntimeException.class,
             maxAttempts = 3,
             backoff = @Backoff(delay = 2000)
     )
    @RateLimiter(name = "inventoryService",fallbackMethod = "fallbackMethod")
    public Inventory getInventory(Long productId) {
        System.out.println("Calling Inventory Service for productId: " + productId);
        return inventoryClient.getInventory(productId);
    }*/

    public Inventory fallbackMethod(Long productId, Throwable throwable) {
        if (throwable instanceof RequestNotPermitted) {
            throw new ResponseStatusException(
                    HttpStatus.TOO_MANY_REQUESTS,
                    "Inventory rate limit exceeded. Try again later.",
                    throwable
            );
        }
        if (throwable instanceof RuntimeException runtimeException) {
            throw runtimeException;
        }
        throw new IllegalStateException("Inventory lookup failed", throwable);
    }

    @CircuitBreaker(name = "inventoryServiceCircuitBreaker", fallbackMethod = "circuitBreakerFallbackMethod")
    public Inventory getInventory(Long productId) {
        System.out.println("Calling Inventory Service for productId: " + productId);
        return inventoryClient.getInventory(productId);
    }

    public Inventory circuitBreakerFallbackMethod(Long productId, Throwable throwable){
        System.out.println("Fallback Method Called for productId: " + productId);
        return new Inventory(productId.toString(),0);
    }
}
