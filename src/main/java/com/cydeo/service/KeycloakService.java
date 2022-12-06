package com.cydeo.service;

import com.cydeo.dto.UserDTO;

import javax.ws.rs.core.Response;

 // how did you create User in spring boot application using Keycloak?
public interface KeycloakService {

    Response userCreate(UserDTO dto);
    void delete(String username);
}
