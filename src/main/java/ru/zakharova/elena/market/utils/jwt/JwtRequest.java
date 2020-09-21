package ru.zakharova.elena.market.utils.jwt;

import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;
}
