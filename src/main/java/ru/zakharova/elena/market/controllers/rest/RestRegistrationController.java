package ru.zakharova.elena.market.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.zakharova.elena.market.entities.User;
import ru.zakharova.elena.market.entities.dtos.SystemUser;
import ru.zakharova.elena.market.services.UsersService;
import ru.zakharova.elena.market.utils.validation.rest.ValidationErrorDTO;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/registration")
@AllArgsConstructor
public class RestRegistrationController {
    private UsersService usersService;


    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SystemUser processRegistrationForm(@Validated @RequestBody SystemUser systemUser) {
        Optional<User> existing = usersService.findByPhone(systemUser.getPhone());
        if (existing.isPresent()) {
            throw new RuntimeException("User with phone number: [" + systemUser.getPhone() + "] is already exist");
        }
        usersService.save(systemUser);
        return systemUser;
        }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public ValidationErrorDTO processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return processFieldErrors(fieldErrors);
    }

    private ValidationErrorDTO processFieldErrors(List<FieldError> fieldErrors) {
        ValidationErrorDTO dto = new ValidationErrorDTO();
        for (FieldError fieldError: fieldErrors) {
            dto.addFieldError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return dto;
    }
}
