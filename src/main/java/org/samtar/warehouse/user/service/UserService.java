package org.samtar.warehouse.user.service;

import org.samtar.warehouse.common.dto.JwtClaimsDto;
import org.samtar.warehouse.common.dto.JwtTokenDto;
import org.samtar.warehouse.common.enums.Roles;
import org.samtar.warehouse.common.enums.TokenTypes;
import org.samtar.warehouse.common.exceptions.AuthException;
import org.samtar.warehouse.common.utils.JwtUtils;
import org.samtar.warehouse.user.dto.request.CreateUserReqDto;
import org.samtar.warehouse.user.dto.request.UserSigninReqDto;
import org.samtar.warehouse.user.dto.response.CreateUserResDto;
import org.samtar.warehouse.user.dto.response.UserSigninResDto;
import org.samtar.warehouse.user.entity.UserEntity;
import org.samtar.warehouse.user.mapper.userMapper;
import org.samtar.warehouse.user.repository.UserProfileRepository;
import org.samtar.warehouse.user.repository.UserRepository;
import org.samtar.warehouse.user.service.imp.UserPrincipleImp;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


//Terms usesd
// 1. User means = all user
// 2. Customer means = role customer
@Service
public class UserService {
    UserProfileRepository userProfileRepository;
    UserRepository userRepository;
    userMapper mapper;
    PasswordEncoder passwordEncoder;
    JwtUtils jwtUtils;
    AuthenticationManager authenticationManager;

    public UserService(AuthenticationManager authenticationManager, JwtUtils jwtUtils, userMapper mapper, PasswordEncoder passwordEncoder, UserProfileRepository userProfileRepository, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
    }

    public CreateUserResDto customerSignUp(CreateUserReqDto payload) {
        UserEntity newCustomer = mapper.toEntity(payload);
        String password = passwordEncoder.encode(payload.password());
        newCustomer.setPassword(password);
        newCustomer.setRole(Roles.CUSTOMER);
        newCustomer = userRepository.save(newCustomer);
        return new CreateUserResDto(newCustomer.getId(), newCustomer.getRole(), getToken(newCustomer));
    }

    public UserSigninResDto userSignIn(UserSigninReqDto payload) {
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.username(), payload.password()));
            UserPrincipleImp userPrinciple = (UserPrincipleImp) auth.getPrincipal();
            assert userPrinciple != null;
            UserEntity user = userPrinciple.getUser();
            System.out.println("--------------------------------Testing");
            return new UserSigninResDto(user.getId(), user.getRole(), getToken(user));
        } catch (BadCredentialsException badCredentialsException) {
            throw AuthException.InvalidCredentials(null);
        }catch (DisabledException ex){
            throw AuthException.accountBlocked();
        }
    }

    private JwtTokenDto getToken(UserEntity user) {
        JwtClaimsDto claims = new JwtClaimsDto(user.getUsername(), user.getEmail());
        return new JwtTokenDto(jwtUtils.generateToken(TokenTypes.ACCESS_TOKEN, claims), jwtUtils.generateToken(TokenTypes.REFRESH_TOKEN, claims));
    }

}
