package com.spnsolo.controller;

import lombok.Data;

@Data
public class AuthenticationRequestDto {
    private String email;
    private String password;
}
