package org.samtar.warehouse.user.controller;

import jakarta.validation.Valid;
import org.samtar.warehouse.common.dto.response.GenericResponseDto;
import org.samtar.warehouse.user.dto.request.CreateUserReqDto;
import org.samtar.warehouse.user.dto.request.UserSigninReqDto;
import org.samtar.warehouse.user.dto.response.CreateUserResDto;
import org.samtar.warehouse.user.dto.response.UserSigninResDto;
import org.samtar.warehouse.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Cutomer
    @PostMapping("/customer-signup")
    public ResponseEntity<GenericResponseDto<CreateUserResDto>> customerSigin(@Valid @RequestBody CreateUserReqDto payload){
        CreateUserResDto data = userService.customerSignUp(payload);
        GenericResponseDto<CreateUserResDto> response = new GenericResponseDto<>("Account has been created",true,data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    // Generic
    @PostMapping({"/customer-signin","/vendor-signin","/admin-signin"})
    public ResponseEntity<GenericResponseDto<UserSigninResDto>> genericSigin(@Valid @RequestBody UserSigninReqDto payload){
        UserSigninResDto data = userService.userSignIn(payload);
        GenericResponseDto<UserSigninResDto> response = new GenericResponseDto<>("Login successfully",true,data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



}
