package ru.zakharova.elena.market.utils.jwt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Model of request for getting user's jwtToken in the application.")
public class JwtRequest {

    @ApiModelProperty(notes = "The user's phone is used as the username.", example = "2", required = true)
    private String username;

    @ApiModelProperty(notes = "User's password", example = "100", required = true)
    private String password;
}
