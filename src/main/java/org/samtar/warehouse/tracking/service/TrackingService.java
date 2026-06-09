package org.samtar.warehouse.tracking.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.samtar.warehouse.cart.entity.CartEntity;
import org.samtar.warehouse.cart.repository.CartRepository;
import org.samtar.warehouse.common.enums.TrackingStatus;
import org.samtar.warehouse.common.exceptions.OrderException;
import org.samtar.warehouse.inventory.entity.InventoryEntity;
import org.samtar.warehouse.inventory.repository.InventoryRepository;
import org.samtar.warehouse.location.entity.CityEntity;
import org.samtar.warehouse.orders.entity.OrderEntity;
import org.samtar.warehouse.orders.repository.OrderRepository;
import org.samtar.warehouse.products.entity.ProductEntity;
import org.samtar.warehouse.products.repository.ProductRepository;
import org.samtar.warehouse.shared.services.UserSharedService;
import org.samtar.warehouse.tracking.entity.TrackEntity;
import org.springframework.stereotype.Service;

@Service
public class TrackingService {
    OrderRepository orderRepository;
    ProductRepository productRepository;
    TrackingService trackingService;
    UserSharedService userSharedService;
    InventoryRepository inventoryRepository;

    public TrackingService(ProductRepository productRepository,
            TrackingService trackingService,
                           InventoryRepository inventoryRepository,
                           UserSharedService userSharedService) {
        this.productRepository = productRepository;
        this.trackingService = trackingService;
        this.userSharedService = userSharedService;
        this.inventoryRepository = inventoryRepository;
    }

    public void startTacking(Long orderId){
        OrderEntity order = orderRepository.findById(orderId).orElseThrow(OrderException::orderNotfound);
        
        //Product List
        Set<ProductEntity> productsTobeDeliver = order.getOrderItems().stream().map(e->e.getProduct()).collect(Collectors.toSet());
         
        // Start tracking
//        startProductTracing(productsTobeDeliver);



    }

    private void startProductTracing(ProductEntity productsTobeDeliver,CityEntity orderCity){
        InventoryEntity sameLocation = null;
        InventoryEntity nearestPick = null;
       Set<CityEntity> productCity = new HashSet<>();
       Set<InventoryEntity> inventories = productsTobeDeliver.getInventories();
       for(InventoryEntity inv:inventories){
           Long cityIdFromInventory = inv.getWarehouse().getCity().getCityId() != null ? inv.getWarehouse().getCity().getCityId() : inv.getVendor().getCity().getCityId();
           if(Objects.equals(cityIdFromInventory, orderCity.getCityId())){
              sameLocation = inv;
           }
           if(inv.getWarehouse().getWarehouseId() != null) nearestPick = inv;
       }
        nearestPick = nearestPick == null ? (InventoryEntity) inventories.toArray()[0] : nearestPick;


       if(sameLocation != null){
           if(sameLocation.getWarehouse().getWarehouseId() != null){
               TrackEntity track = new TrackEntity();
               track.setStep(1);
               track.setProduct(productsTobeDeliver);
               track.setStatus(TrackingStatus.REACHED);
               track.setReachedTime(LocalDateTime.now());
               track.setWareHouse(sameLocation.getWarehouse());
           }else {
            // from vendor of same location
               Set<TrackEntity> trackPath = new HashSet<>();
               for (int i = 0; i < 2; i++) {
                   TrackEntity track = new TrackEntity();
                   track.setStep(i+1);
                   track.setProduct(productsTobeDeliver);
                   track.setStatus(TrackingStatus.REACHED);
                   track.setReachedTime(LocalDateTime.now());
                   track.setWareHouse(sameLocation.getWarehouse());
//                   track.set
               }
           }
       }else {
           if(nearestPick.getWarehouse().getWarehouseId() != null){
               // wherehouse different location
           }else {
               // from vendor of another location
           }
       }
    }



//    private Set<CityEntity> getProductCity(ProductEntity product){};
    private boolean isVendor(InventoryEntity inventory){
       return inventory.getInventoryId() != null;
    }

    


}
