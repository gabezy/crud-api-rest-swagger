package com.gabezy.projects.api.domain.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Setter
@Getter
@Slf4j
public class LoginDTO {

    @NotNull
    private String username;

    @NotNull
    private String password;
}
