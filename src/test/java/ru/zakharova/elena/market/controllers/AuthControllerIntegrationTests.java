package ru.zakharova.elena.market.controllers;
// https://sysout.ru/testirovanie-spring-boot-prilozheniya-s-testresttemplate/

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import ru.zakharova.elena.market.utils.jwt.JwtRequest;
import ru.zakharova.elena.market.utils.jwt.JwtResponse;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerIntegrationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void whenUserAuthenticated_thenStatus200() throws JsonProcessingException {
        JwtRequest authRequest = new JwtRequest();
        authRequest.setUsername("2");
        authRequest.setPassword("100");
        ResponseEntity<JwtResponse> response = restTemplate.postForEntity("/auth", new HttpEntity<>(authRequest), JwtResponse.class);
        JwtResponse jwtResponse = response.getBody();
        System.out.println(response);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(jwtResponse, is(instanceOf(JwtResponse.class)));
        assertThat(jwtResponse.getData(), is(notNullValue()));
        assertThat(jwtResponse.getToken(), is(notNullValue()));

    }
}
