package com.gabezy.projects.api.controllers;

import com.gabezy.projects.api.domain.dtos.CreateUserDTO;
import com.gabezy.projects.api.domain.dtos.DetailsUserDTO;
import com.gabezy.projects.api.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<DetailsUserDTO> createUser(@RequestBody @Valid CreateUserDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.save(dto));
    }

    @GetMapping
    public ResponseEntity<List<DetailsUserDTO>> getAllUsers() {
        return ResponseEntity.ok(this.userService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailsUserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(this.userService.getById(id));
    }




}
