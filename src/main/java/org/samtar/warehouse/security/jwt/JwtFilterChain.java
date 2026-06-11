package org.samtar.warehouse.security.jwt;

import java.io.IOException;
import java.util.List;

import org.samtar.warehouse.common.dto.JwtClaimsDto;
import org.samtar.warehouse.common.dto.response.GenericResponseDto;
import org.samtar.warehouse.common.enums.TokenTypes;
import org.samtar.warehouse.common.exceptions.AuthException;
import org.samtar.warehouse.common.utils.JwtUtils;
import org.samtar.warehouse.user.service.imp.UserDetailImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Null;

@Component
public class JwtFilterChain extends OncePerRequestFilter {

    private final ObjectMapper mapper;
    private final UserDetailImp userDetailImp;
    private final JwtUtils jwtUtils;

    private final List<String> unProtectedRoutes = List.of(
            "/api/v1/auth/**",
            "/api/testing/**",
            "/api/v1/vendor/**",
            "/api/v1/driver/**");
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public JwtFilterChain(ObjectMapper mapper, UserDetailImp userDetailImp, JwtUtils jwtUtils) {
        this.mapper = mapper;
        this.userDetailImp = userDetailImp;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String requestPath = request.getServletPath();
        return unProtectedRoutes.stream().anyMatch(p -> pathMatcher.match(p, requestPath));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String header = request.getHeader("Authorization");
            if (header == null || !header.startsWith("Bearer")) {
                throw new Exception("Invalid headers");
            }

            String accessToken = header.substring(7);
            if (accessToken.isEmpty()) {
                throw new Exception("Invalid headers");
            }
            JwtClaimsDto decodedToken = jwtUtils.decodeToken(accessToken, TokenTypes.ACCESS_TOKEN);
            String username = decodedToken.username();
            if (username.isEmpty()) {
                throw new Exception("Invalid headers");
            }
            boolean isNotAuthenticated = SecurityContextHolder.getContext().getAuthentication() == null;
            if (isNotAuthenticated) {
                UserDetails userDetails = userDetailImp.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authrticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authrticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authrticationToken);
            }
            ;
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            sendErrorResponse(response, e);
        }
    }

    private void sendErrorResponse(HttpServletResponse response, Exception exception) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        if (exception instanceof AuthException) {
            GenericResponseDto<Null> res = new GenericResponseDto<>(exception.getMessage(), false, null);
            mapper.writeValue(response.getWriter(), res);
        } else {
            GenericResponseDto<Null> res = new GenericResponseDto<>("Something went wrong", true, null);
            mapper.writeValue(response.getWriter(), res);
        }
    }

}
