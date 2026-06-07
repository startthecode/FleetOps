package org.samtar.warehouse.inventory.dto.req;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

public record CreateInventoryReqDto(
        Boolean selfStorage,
        Long warehouseId,
        Integer stock_warehouse,
        Integer stock_vendor,

        @NotNull(message = "Product id can not be blank")
        Long productId
) {
    public CreateInventoryReqDto{
        if(stock_warehouse == null) stock_warehouse = 0;
        if(stock_vendor == null) stock_vendor = 0;
    }
    @AssertTrue(message = "self storage or warehouse shoud be available")
    public boolean isStorageGiven(){
        return selfStorage != null || warehouseId != null;
    }


}
