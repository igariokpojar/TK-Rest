package com.cydeo.controller;

import com.cydeo.annotation.ExecutionTime;
import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.UserDTO;
import com.cydeo.exception.TicketingProjectException;
import com.cydeo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController// if we have only controller we need to return view
@RequestMapping("/api/v1/user") // creating an API
@Tag(name = "UserController",description = "User API") // for swagger
public class UserController { // controller classes are sending http request

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    @ExecutionTime
    // let's start to secure all the End points by add annotation @RolesAllowed
    @RolesAllowed({"Manager","Admin"})
    @Operation(summary = "Get users")
    public ResponseEntity<ResponseWrapper> getUsers(){
        List<UserDTO> userDTOList = userService.listAllUsers();
        return ResponseEntity.ok(new ResponseWrapper("Users are successfully retrieved",userDTOList, HttpStatus.OK));
    }

    @GetMapping("/{username}")
    @ExecutionTime
    @RolesAllowed({"Admin"})
    @Operation(summary = "Get users by username")
    public ResponseEntity<ResponseWrapper> getUsersByUserName(@PathVariable("username")String userName){
        UserDTO user = userService.findByUserName(userName);
        return ResponseEntity.ok(new ResponseWrapper("Users is successfully retrieved",user, HttpStatus.OK));
    }

    @PostMapping
    @RolesAllowed({"Admin"})
    @Operation(summary = "Create user")
    public ResponseEntity<ResponseWrapper> createUser(@RequestBody UserDTO user){

        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("Users is successfully created",HttpStatus.CREATED));
    }

    @PutMapping
    @RolesAllowed({"Admin"})
    @Operation(summary = "Update user")
    public ResponseEntity<ResponseWrapper> updateUser(@RequestBody UserDTO user){

        userService.update(user);
        return ResponseEntity.ok(new ResponseWrapper("User is successfully updated",HttpStatus.OK));
    }

    @DeleteMapping("/{username}")
    @RolesAllowed({"Admin"})
    @Operation(summary = "Delete user")
    public ResponseEntity<ResponseWrapper> deleteUser(@PathVariable ("username")String userName)throws TicketingProjectException {
        userService.delete(userName);
        return ResponseEntity.ok(new ResponseWrapper("User is successfully deleted",HttpStatus.OK));
    }
}


