package ru.zakharova.elena.market.controllers.rest;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.zakharova.elena.market.exceptions.MarketError;
import ru.zakharova.elena.market.utils.jwt.JwtRequest;
import ru.zakharova.elena.market.utils.jwt.JwtResponse;
import ru.zakharova.elena.market.beans.jwt.JwtTokenUtil;
import ru.zakharova.elena.market.services.UsersService;

import java.util.List;


@Profile({"ws", "rest", "test"})
@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
public class RestAuthController {
    private final UsersService usersService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    public RestAuthController(UsersService usersService, JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager) {
        this.usersService = usersService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new MarketError(HttpStatus.UNAUTHORIZED.value(), "Incorrect username or password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = usersService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        List<String> roleList = jwtTokenUtil.getRoleList(userDetails);
        return ResponseEntity.ok(new JwtResponse(roleList, token));
    }

}
