package com.gabezy.projects.api.domain.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@Slf4j
public class TokenDTO {
    private String token;
    private LocalDateTime expireAt;
}
