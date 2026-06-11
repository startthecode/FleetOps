package org.samtar.warehouse.products.controller;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import org.samtar.warehouse.common.dto.response.GenericResponseDto;
import org.samtar.warehouse.products.dto.req.CreateProductReqDto;
import org.samtar.warehouse.products.dto.req.UpdateProductReqDto;
import org.samtar.warehouse.products.dto.res.ProductPaginatedResDto;
import org.samtar.warehouse.products.dto.res.ProductResDto;
import org.samtar.warehouse.products.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<GenericResponseDto<ProductPaginatedResDto>> getAllProducts(
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "10") Integer size,
        @RequestParam(defaultValue = "asc") String ord,
        @RequestParam(defaultValue = "productId") String sortBy,
        @RequestParam(defaultValue = "") String keyword
    
    ){
        GenericResponseDto<ProductPaginatedResDto> response = new GenericResponseDto<>("Product Created Successfully",true,productService.getAllProducts(
            page,size,ord,sortBy,keyword
        ));
        return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/all/{userid}")
    public ResponseEntity<GenericResponseDto<List<ProductResDto>>> getAllProducts(@Valid @PathVariable long userid, 
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "10") Integer size,
        @RequestParam(defaultValue = "asc") String ord,
        @RequestParam(defaultValue = "productId") String sortBy){
        GenericResponseDto<List<ProductResDto>> response = new GenericResponseDto<>("Product Created Successfully",true,productService.getAllProducts(userid,page,size,ord,sortBy));
        return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/add")
    public ResponseEntity<GenericResponseDto<ProductResDto>> createProduct(@Valid @RequestBody CreateProductReqDto payload){
        GenericResponseDto<ProductResDto> response = new GenericResponseDto<>("Product Created Successfully",true,productService.createProduct(payload));
        return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/update")
    public ResponseEntity<GenericResponseDto<ProductResDto>> updateProduct(@Valid @RequestBody UpdateProductReqDto payload){
        GenericResponseDto<ProductResDto> response = new GenericResponseDto<>("Product Updated Successfully",true,productService.updateProduct(payload));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/delete/{productID}")
    public ResponseEntity<GenericResponseDto<Null>> deleteProduct(@PathVariable Long productID){
        productService.deleteProduct(productID);
        GenericResponseDto<Null> response = new GenericResponseDto<>("Product with ID: " +productID+" Deleted Successfully",true,null);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

}
