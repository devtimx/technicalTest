package com.example.technicaltest.service.impl;

import com.example.technicaltest.client.petstore.PetStoreClient;
import com.example.technicaltest.client.petstore.dto.CreatePetExternalRequest;
import com.example.technicaltest.client.petstore.dto.PetStoreResponse;
import com.example.technicaltest.dto.request.CreatePetRequest;
import com.example.technicaltest.dto.response.CreatePetResponse;
import com.example.technicaltest.dto.response.PetResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("PetServiceImpl Tests")
class PetServiceImplTest {

    @Mock
    private PetStoreClient petStoreClient;

    @InjectMocks
    private PetServiceImpl petService;

    private PetStoreResponse externalPetResponse;

    @BeforeEach
    void setUp() {
        externalPetResponse = new PetStoreResponse();
        externalPetResponse.setId(1L);
        externalPetResponse.setName("Firulais");
        externalPetResponse.setStatus("available");
    }

    @Test
    @DisplayName("getPetById - Should return PetResponse with correct mapping")
    void testGetPetById_Success() {
        // Arrange
        Long petId = 1L;
        when(petStoreClient.getPetById(eq(petId)))
                .thenReturn(externalPetResponse);

        // Act
        PetResponse response = petService.getPetById(petId);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Firulais", response.getName());
        assertEquals("available", response.getStatus());
        verify(petStoreClient).getPetById(eq(petId));
    }

    @Test
    @DisplayName("getPetById - Should handle null response from external API")
    void testGetPetById_NullResponse() {
        // Arrange
        Long petId = 999L;
        when(petStoreClient.getPetById(eq(petId)))
                .thenReturn(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            petService.getPetById(petId);
        });
    }

    @Test
    @DisplayName("getPetById - Should handle exception from external API")
    void testGetPetById_ExternalApiError() {
        // Arrange
        Long petId = 1L;
        when(petStoreClient.getPetById(eq(petId)))
                .thenThrow(new RuntimeException("External API error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            petService.getPetById(petId);
        });
    }

    @Test
    @DisplayName("createPet - Should create pet and return response with transactionId and timestamp")
    void testCreatePet_Success() {
        // Arrange
        CreatePetRequest request = new CreatePetRequest();
        request.setId(1L);
        request.setName("Firulais");
        request.setStatus("available");

        when(petStoreClient.createPet(any(CreatePetExternalRequest.class)))
                .thenReturn(externalPetResponse);

        // Act
        CreatePetResponse response = petService.createPet(request);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getTransactionId());
        assertNotNull(response.getDateCreated());
        assertEquals("Firulais", response.getName());
        assertEquals("available", response.getStatus());
        verify(petStoreClient).createPet(any(CreatePetExternalRequest.class));
    }

    @Test
    @DisplayName("createPet - Should map request fields correctly to external request")
    void testCreatePet_MappingCorrect() {
        // Arrange
        CreatePetRequest request = new CreatePetRequest();
        request.setId(2L);
        request.setName("Boby");
        request.setStatus("sold");

        PetStoreResponse responseFromApi = new PetStoreResponse();
        responseFromApi.setId(2L);
        responseFromApi.setName("Boby");
        responseFromApi.setStatus("sold");

        when(petStoreClient.createPet(any(CreatePetExternalRequest.class)))
                .thenReturn(responseFromApi);

        // Act
        CreatePetResponse response = petService.createPet(request);

        // Assert
        assertEquals("Boby", response.getName());
        assertEquals("sold", response.getStatus());
    }

    @Test
    @DisplayName("createPet - Should generate unique transactionId for each call")
    void testCreatePet_UniqueTransactionId() {
        // Arrange
        CreatePetRequest request = new CreatePetRequest();
        request.setId(1L);
        request.setName("Firulais");
        request.setStatus("available");

        when(petStoreClient.createPet(any(CreatePetExternalRequest.class)))
                .thenReturn(externalPetResponse);

        // Act
        CreatePetResponse response1 = petService.createPet(request);
        CreatePetResponse response2 = petService.createPet(request);

        // Assert
        assertNotEquals(response1.getTransactionId(), response2.getTransactionId());
    }

    @Test
    @DisplayName("createPet - Should handle exception from external API")
    void testCreatePet_ExternalApiError() {
        // Arrange
        CreatePetRequest request = new CreatePetRequest();
        request.setId(1L);
        request.setName("Firulais");
        request.setStatus("available");

        when(petStoreClient.createPet(any(CreatePetExternalRequest.class)))
                .thenThrow(new RuntimeException("External API error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            petService.createPet(request);
        });
    }
}
