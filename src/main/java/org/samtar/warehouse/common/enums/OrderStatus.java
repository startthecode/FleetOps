package org.samtar.warehouse.common.enums;

public enum OrderStatus {

    PENDING,           // Order created but not confirmed

    CONFIRMED,         // Stock verified and reserved

    PACKING,           // Warehouse is packing items

    READY_FOR_PICKUP,  // Packed and waiting for driver

    ASSIGNED,          // Driver assigned

    PICKED_UP,         // Driver picked package

    IN_TRANSIT,        // On the way to customer

    DELIVERED,         // Successfully delivered

    CANCELLED,         // Cancelled by customer/admin

    FAILED,            // Delivery failed

    RETURNED,          // Returned to warehouse

    OUT_OF_STOCK       // Cannot fulfill due to stock
}
