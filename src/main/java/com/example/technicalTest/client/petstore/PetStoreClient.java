package com.example.technicaltest.client.petstore;

import com.example.technicaltest.client.petstore.dto.CreatePetExternalRequest;
import com.example.technicaltest.client.petstore.dto.PetStoreResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class PetStoreClient {

    private final RestTemplate restTemplate;

    private static final String BASE_URL =
            "https://petstore.swagger.io/v2/pet/";

    public PetStoreResponse getPetById(Long petId) {

        return restTemplate.getForObject(
                BASE_URL + petId,
                PetStoreResponse.class
        );
    }

    public PetStoreResponse createPet(
            CreatePetExternalRequest request) {

        return restTemplate.postForObject(
                BASE_URL,
                request,
                PetStoreResponse.class
        );
    }
}