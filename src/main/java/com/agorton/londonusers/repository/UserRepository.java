package com.agorton.londonusers.repository;

import com.agorton.londonusers.domain.User;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Repository
public class UserRepository {

    private RestTemplate restTemplate = new RestTemplateBuilder().build();

    private static final String API_URL = "https://bpdts-test-app.herokuapp.com";

    public UserRepository() {
    }

    public List<User> getUsersForCity(String city) {
        ParameterizedTypeReference<List<User>> typeRef = new ParameterizedTypeReference<List<User>>() {};

        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(
                API_URL + "/city/" + city + "/users",
                HttpMethod.GET,
                null,
                typeRef);

        return responseEntity.getBody();
    }

    public List<User> getAllUsers() {
        ParameterizedTypeReference<List<User>> typeRef = new ParameterizedTypeReference<List<User>>() {};

        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(
                API_URL + "/users",
                HttpMethod.GET,
                null,
                typeRef);

        return responseEntity.getBody();
    }
}
