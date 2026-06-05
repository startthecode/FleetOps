package org.samtar.warehouse.products.controller;


import jakarta.validation.Valid;
import org.samtar.warehouse.common.dto.response.GenericResponseDto;
import org.samtar.warehouse.products.dto.req.CreateProductReqDto;
import org.samtar.warehouse.products.dto.res.ProductResDto;
import org.samtar.warehouse.products.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public ResponseEntity<GenericResponseDto<ProductResDto>> createProduct(@Valid @RequestBody CreateProductReqDto payload){
        GenericResponseDto<ProductResDto> response = new GenericResponseDto<>("Product Created Successfully",true,productService.createProduct(payload));
        return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
