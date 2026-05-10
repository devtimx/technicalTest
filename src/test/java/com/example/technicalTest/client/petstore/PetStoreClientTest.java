package com.example.technicaltest.client.petstore;

import com.example.technicaltest.client.petstore.dto.CreatePetExternalRequest;
import com.example.technicaltest.client.petstore.dto.PetStoreResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("PetStoreClient Tests")
class PetStoreClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PetStoreClient petStoreClient;

    private static final String BASE_URL = "https://petstore.swagger.io/v2/pet/";

    private PetStoreResponse mockResponse;

    @BeforeEach
    void setUp() {
        mockResponse = new PetStoreResponse();
        mockResponse.setId(1L);
        mockResponse.setName("Firulais");
        mockResponse.setStatus("available");
    }

    @Test
    @DisplayName("getPetById - Should call RestTemplate.getForObject with correct URL")
    void testGetPetById_Success() {
        // Arrange
        Long petId = 1L;
        when(restTemplate.getForObject(eq(BASE_URL + petId), eq(PetStoreResponse.class)))
                .thenReturn(mockResponse);

        // Act
        PetStoreResponse response = petStoreClient.getPetById(petId);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Firulais", response.getName());
        assertEquals("available", response.getStatus());
        verify(restTemplate).getForObject(eq(BASE_URL + petId), eq(PetStoreResponse.class));
    }

    @Test
    @DisplayName("getPetById - Should construct URL correctly with petId")
    void testGetPetById_UrlConstruction() {
        // Arrange
        Long petId = 123L;
        String expectedUrl = BASE_URL + petId;
        when(restTemplate.getForObject(eq(expectedUrl), eq(PetStoreResponse.class)))
                .thenReturn(mockResponse);

        // Act
        petStoreClient.getPetById(petId);

        // Assert
        verify(restTemplate).getForObject(eq(expectedUrl), eq(PetStoreResponse.class));
    }

    @Test
    @DisplayName("getPetById - Should handle null response")
    void testGetPetById_NullResponse() {
        // Arrange
        Long petId = 999L;
        when(restTemplate.getForObject(anyString(), any()))
                .thenReturn(null);

        // Act
        PetStoreResponse response = petStoreClient.getPetById(petId);

        // Assert
        assertNull(response);
    }

    @Test
    @DisplayName("createPet - Should call RestTemplate.postForObject with correct parameters")
    void testCreatePet_Success() {
        // Arrange
        CreatePetExternalRequest request = CreatePetExternalRequest.builder()
                .id(1L)
                .name("Firulais")
                .status("available")
                .build();

        when(restTemplate.postForObject(eq(BASE_URL), eq(request), eq(PetStoreResponse.class)))
                .thenReturn(mockResponse);

        // Act
        PetStoreResponse response = petStoreClient.createPet(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Firulais", response.getName());
        verify(restTemplate).postForObject(eq(BASE_URL), eq(request), eq(PetStoreResponse.class));
    }

    @Test
    @DisplayName("createPet - Should post to correct URL")
    void testCreatePet_UrlCorrect() {
        // Arrange
        CreatePetExternalRequest request = CreatePetExternalRequest.builder()
                .id(2L)
                .name("Boby")
                .status("sold")
                .build();

        when(restTemplate.postForObject(eq(BASE_URL), any(CreatePetExternalRequest.class), eq(PetStoreResponse.class)))
                .thenReturn(mockResponse);

        // Act
        petStoreClient.createPet(request);

        // Assert
        verify(restTemplate).postForObject(eq(BASE_URL), eq(request), eq(PetStoreResponse.class));
    }

    @Test
    @DisplayName("createPet - Should return response with correct data")
    void testCreatePet_ResponseData() {
        // Arrange
        CreatePetExternalRequest request = CreatePetExternalRequest.builder()
                .id(1L)
                .name("Test")
                .status("available")
                .build();

        PetStoreResponse apiResponse = new PetStoreResponse();
        apiResponse.setId(1L);
        apiResponse.setName("TestPet");
        apiResponse.setStatus("pending");

        when(restTemplate.postForObject(anyString(), any(), any()))
                .thenReturn(apiResponse);

        // Act
        PetStoreResponse response = petStoreClient.createPet(request);

        // Assert
        assertEquals(1L, response.getId());
        assertEquals("TestPet", response.getName());
        assertEquals("pending", response.getStatus());
    }

    @Test
    @DisplayName("createPet - Should handle null response")
    void testCreatePet_NullResponse() {
        // Arrange
        CreatePetExternalRequest request = CreatePetExternalRequest.builder()
                .id(1L)
                .name("Test")
                .status("available")
                .build();

        when(restTemplate.postForObject(anyString(), any(), any()))
                .thenReturn(null);

        // Act
        PetStoreResponse response = petStoreClient.createPet(request);

        // Assert
        assertNull(response);
    }
}
