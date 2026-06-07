package org.samtar.warehouse.products.service;

import jakarta.transaction.Transactional;
import org.samtar.warehouse.common.exceptions.ProductException;
import org.samtar.warehouse.inventory.dto.req.CreateInventoryReqDto;
import org.samtar.warehouse.inventory.service.InventoryService;
import org.samtar.warehouse.products.dto.req.CreateProductReqDto;
import org.samtar.warehouse.products.dto.req.UpdateProductReqDto;
import org.samtar.warehouse.products.dto.res.ProductResDto;
import org.samtar.warehouse.products.entity.ProductEntity;
import org.samtar.warehouse.products.mapper.ProductMapper;
import org.samtar.warehouse.products.repository.ProductRepository;
import org.samtar.warehouse.shared.services.UserSharedService;
import org.samtar.warehouse.user.entity.UserEntity;
import org.samtar.warehouse.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    ProductMapper productMapper;
    ProductRepository productRepository;
    UserRepository userRepository;
    UserSharedService userSharedService;
    InventoryService inventoryService;

    public ProductService(ProductMapper productMapper,
                          InventoryService inventoryService,
                          ProductRepository productRepository, UserRepository userRepository, UserSharedService userSharedService) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.userSharedService = userSharedService;
        this.inventoryService= inventoryService;
    }

    @Transactional
    public ProductResDto createProduct(CreateProductReqDto payload) {
        boolean isAlreadyExists = productRepository.existsByProductNameIgnoreCase(payload.productName().trim());
        if (isAlreadyExists) throw ProductException.alreadyExists();
        UserEntity currentUser = userSharedService.getCurrentUser();
        ProductEntity newProduct = productMapper.toEntity(payload);
        newProduct.setCreatedBy(userRepository.getReferenceById(currentUser.getId()));
        newProduct = productRepository.save(newProduct);
        createInventory(newProduct);
        return productMapper.toResponse(newProduct);
    }

    public ProductResDto updateProduct(UpdateProductReqDto payload) {
        Long productId = payload.productID();
        ProductEntity productToBeUpdate = productRepository.findByProductId(productId).orElseThrow(() -> ProductException.notExists(payload.productID()));
        if (payload.productName() != null) {
            boolean isNameAlreadyExists = productRepository.existsByProductNameIgnoreCase(payload.productName().trim());
            if(isNameAlreadyExists) throw ProductException.alreadyExists();
        }
        productMapper.updateEntity(payload, productToBeUpdate);
        productRepository.save(productToBeUpdate);
        return productMapper.toResponse(productToBeUpdate);
    }

    public void deleteProduct(Long productID){
        boolean isProductExist = productRepository.existsById(productID);
        if(!isProductExist) throw ProductException.notExists(productID);
        productRepository.deleteById(productID);
    }

    public List<ProductResDto> getAllProducts(Long userID){
            List<ProductEntity> allProduct = productRepository.findAllByCreatedBy_id(userID);
            return allProduct.stream().map(e->productMapper.toResponse(e)).toList();
            }

    public List<ProductResDto> getAllProducts(){
        List<ProductEntity> allProduct =productRepository.findAll();
        return allProduct.stream().map(e->productMapper.toResponse(e)).toList();
    }

    public boolean createInventory(ProductEntity product){
        CreateInventoryReqDto data = new CreateInventoryReqDto(true,null,0,0, product.getProductId());
        return inventoryService.createInventory(data);
    }
}
