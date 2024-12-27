package com.bankingBackend.bankingApi.interceptor;

import com.bankingBackend.bankingApi.DTO.user.EmailRoleDTO;
import com.bankingBackend.bankingApi.enums.UserType;
import com.bankingBackend.bankingApi.exceptions.UnauthorizedUserException;
import com.bankingBackend.bankingApi.services.OtherService;
import com.bankingBackend.bankingApi.services.RolePermissionsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.bankingBackend.bankingApi.enums.UserType.ADMIN;

@Component
public class RoleBasedAuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private OtherService service;
    @Autowired
    private RolePermissionsService rolePermissionsService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){

        String route = request.getRequestURI(), type = request.getMethod();
        if(route.contains("/login") || route.contains("/sign_up")) return true;
        EmailRoleDTO emailRoleDTO = service.getEmailAndRole();
        if(route.contains("/create_permission") && emailRoleDTO.getRole().equals(ADMIN.getValue())) return true;

        if(rolePermissionsService.hasPermission(route , emailRoleDTO.getRole(), type)) return true;

        throw new UnauthorizedUserException("You are not Authorized to access this ENDPOINT !");
    }
}