package com.example.technicalTest.service.impl;

import com.example.technicalTest.client.petstore.PetStoreClient;
import com.example.technicalTest.client.petstore.dto.PetStoreResponse;
import com.example.technicalTest.dto.response.PetResponse;
import com.example.technicalTest.service.PetService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.example.technicalTest.dto.request.CreatePetRequest;
import com.example.technicalTest.dto.response.CreatePetResponse;

import com.example.technicalTest.client.petstore.dto.CreatePetExternalRequest;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PetServiceImpl implements PetService {

    private final PetStoreClient petStoreClient;

    @Override
    public PetResponse getPetById(Long petId) {

        log.info("Consultando mascota con ID: {}", petId);

        PetStoreResponse externalResponse
                = petStoreClient.getPetById(petId);

        log.info("Respuesta obtenida desde API externa:");
        log.info("ID: {}", externalResponse.getId());
        log.info("NAME: {}", externalResponse.getName());
        log.info("STATUS: {}", externalResponse.getStatus());

        return PetResponse.builder()
                .id(externalResponse.getId())
                .name(externalResponse.getName())
                .status(externalResponse.getStatus())
                .build();
    }

    @Override
    public CreatePetResponse createPet(
            CreatePetRequest request) {

        log.info("Creando mascota: {}", request);

        CreatePetExternalRequest externalRequest
                = CreatePetExternalRequest.builder()
                        .id(request.getId())
                        .name(request.getName())
                        .status(request.getStatus())
                        .build();

        PetStoreResponse externalResponse
                = petStoreClient.createPet(externalRequest);

        log.info("Respuesta API externa: {}", externalResponse);

        return CreatePetResponse.builder()
                .transactionId(UUID.randomUUID().toString())
                .dateCreated(LocalDateTime.now().toString())
                .status(externalResponse.getStatus())
                .name(externalResponse.getName())
                .build();
    }
}
