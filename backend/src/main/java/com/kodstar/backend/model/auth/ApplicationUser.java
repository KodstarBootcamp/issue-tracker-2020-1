package com.kodstar.backend.model.auth;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ApplicationUser {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
