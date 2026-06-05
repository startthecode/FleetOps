package org.samtar.warehouse.products.service;

import org.samtar.warehouse.products.dto.req.CreateProductReqDto;
import org.samtar.warehouse.products.dto.res.ProductResDto;
import org.samtar.warehouse.products.entity.ProductEntity;
import org.samtar.warehouse.products.mapper.ProductMapper;
import org.samtar.warehouse.products.repository.ProductRepository;
import org.samtar.warehouse.shared.services.UserSharedService;
import org.samtar.warehouse.user.entity.UserEntity;
import org.samtar.warehouse.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
ProductMapper productMapper;
ProductRepository productRepository;
UserRepository userRepository;
UserSharedService userSharedService;

    public ProductService(ProductMapper productMapper, ProductRepository productRepository, UserRepository userRepository, UserSharedService userSharedService) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.userSharedService = userSharedService;
    }

    public ProductResDto createProduct(CreateProductReqDto payload){
        UserEntity currentUser = userSharedService.getCurrentUser();
        ProductEntity newProduct = productMapper.toEntity(payload);
        newProduct.setCreatedBy(userRepository.getReferenceById(currentUser.getId()));
        return productMapper.toResponse(productRepository.save(newProduct));
    }
}
