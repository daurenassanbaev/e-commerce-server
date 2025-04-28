package com.ecommerce.productservice.product.messaging;

import com.ecommerce.common.event.product.ProductEvent;
import com.ecommerce.common.event.product.ProductStatusChangedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductEventProducer {
    private final StreamBridge streamBridge;

    public void sendProductCreatedEvent(ProductEvent productEvent) {
        streamBridge.send("productCreated-out-0", productEvent);
    }

    public void sendProductUpdatedEvent(ProductEvent productEvent) {
        streamBridge.send("productUpdated-out-0", productEvent);
    }

    public void sendProductStatusChangedEvent(ProductStatusChangedEvent changedEvent) {
        streamBridge.send("productStatusChanged-out-0", changedEvent);
    }
}
