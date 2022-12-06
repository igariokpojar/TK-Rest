package com.cydeo.controller;

import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.UserDTO;
import com.cydeo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController// if we have only controller we need to return view
@RequestMapping("/api/v1/user") // creating an API
public class UserController { // controller classes are sending http request

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    // let's start to secure all the End points by add annotation @RolesAllowed
    @RolesAllowed({"Manager","Admin"})
    public ResponseEntity<ResponseWrapper> getUsers(){
        List<UserDTO> userDTOList = userService.listAllUsers();
        return ResponseEntity.ok(new ResponseWrapper("Users are successfully retrieved",userDTOList, HttpStatus.OK));
    }

    @GetMapping("/{username}")
    @RolesAllowed({"Admin"})
    public ResponseEntity<ResponseWrapper> getUsersByUserName(@PathVariable("username")String userName){
        UserDTO user = userService.findByUserName(userName);
        return ResponseEntity.ok(new ResponseWrapper("Users is successfully retrieved",user, HttpStatus.OK));
    }

    @PostMapping
    @RolesAllowed({"Admin"})
    public ResponseEntity<ResponseWrapper> createUser(@RequestBody UserDTO user){

        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("Users is successfully created",HttpStatus.CREATED));
    }

    @PutMapping
    @RolesAllowed({"Admin"})
    public ResponseEntity<ResponseWrapper> updateUser(@RequestBody UserDTO user){

        userService.update(user);
        return ResponseEntity.ok(new ResponseWrapper("User is successfully updated",HttpStatus.OK));
    }

    @DeleteMapping("/{username}")
    @RolesAllowed({"Admin"})
    public ResponseEntity<ResponseWrapper> deleteUser(@PathVariable ("username")String userName){
        userService.delete(userName);
        return ResponseEntity.ok(new ResponseWrapper("User is successfully deleted",HttpStatus.OK));
    }
}


