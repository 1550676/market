package ru.zakharova.elena.market.entities.dtos;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zakharova.elena.market.utils.validation.FieldMatch;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")
public class SystemUser {

    @NotNull(message = "This field is required")
    @Size(min = 2, message = "Phone number length must be 10 symbols")
    private String phone;

    @NotNull(message = "This field is required")
    @Size(min = 4, message = "Password is too short")
    private String password;

    @NotNull(message = "This field is required")
    @Size(min = 4, message = "Password is too short")
    private String matchingPassword;

    @NotNull(message = "This field is required")
    private String firstName;

    @NotNull(message = "This field is required")
    private String lastName;

    @NotNull(message = "This field is required")
    @Email(message = "This field must be field of email")
    private String email;
}
