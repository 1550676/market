package ru.zakharova.elena.market.utils.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class JwtResponse {
    private List<String> roleList;
    private String token;

}
