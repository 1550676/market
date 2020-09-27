package ru.zakharova.elena.market.utils.jwt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@ApiModel(description = "Model of response with user's jwtToken in the application.")
public class JwtResponse {

    @ApiModelProperty(notes = "The list of the user's roles.", example = "[\"ROLE_ADMIN\", \"ROLE_CUSTOMER\", \"ROLE_MANAGER\"]", required = true)
    private List<String> roleList;

    @ApiModelProperty(notes = "jwtToken.", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwicm9sZSI6WyJST0xFX0FETUlOIiwiUk9MRV9DVVNUT01FUiIsIlJPTEVfTUFOQUdFUiJdLCJleHAiOjE2MDExMzAxMDcsImlhdCI6MTYwMTEyNjUwN30.3VQc2dDcrxOxDoprWG5F7FPmqoV8sTQYFCdNaspOeEo", required = true)
    private String token;

}
