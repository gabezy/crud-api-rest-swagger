package com.gabezy.projects.api.domain.entity;

import com.gabezy.projects.api.domain.dtos.CreateUserDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Setter
@Getter
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String username;

    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public User(CreateUserDTO dto) {
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.username = dto.getUsername();
        this.password = dto.getPassword();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
    }
}
