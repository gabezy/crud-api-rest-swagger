package com.gabezy.projects.api.controllers;

import com.gabezy.projects.api.domain.dtos.CreateUserDTO;
import com.gabezy.projects.api.domain.dtos.DetailsUserDTO;
import com.gabezy.projects.api.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "UserController", description = "Endpoint to create, retrieve, alter and delete users ")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Create new User", method = "POST", responses = @ApiResponse(responseCode = "201"))
    public ResponseEntity<DetailsUserDTO> createUser(@RequestBody @Valid CreateUserDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.save(dto));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Get all users", responses = @ApiResponse(responseCode = "200"))
    public ResponseEntity<List<DetailsUserDTO>> getAllUsers() {
        return ResponseEntity.ok(this.userService.getAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Get a specific user by passing the id in the URL")
    public ResponseEntity<DetailsUserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(this.userService.getById(id));
    }




}
