package ru.zakharova.elena.market.utils.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class JwtResponse {
    private Date data;
    private String token;

}
