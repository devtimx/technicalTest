package com.example.technicaltest.controller;

import com.example.technicaltest.dto.request.CreatePetRequest;
import com.example.technicaltest.dto.response.CreatePetResponse;
import com.example.technicaltest.dto.response.PetResponse;
import com.example.technicaltest.service.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("PetController Tests")
class PetControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PetService petService;

    @BeforeEach
    void setUp() {
        PetController controller = new PetController(petService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new com.example.technicaltest.exception.GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("GET /api/pet/{petId} - Should return pet when found")
    void testGetPetById_Success() throws Exception {
        // Arrange
        Long petId = 1L;
        PetResponse petResponse = PetResponse.builder()
                .id(petId)
                .name("Firulais")
                .status("available")
                .build();

        when(petService.getPetById(eq(petId))).thenReturn(petResponse);

        // Act & Assert
        mockMvc.perform(get("/api/pet/{petId}", petId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(petId))
                .andExpect(jsonPath("$.name").value("Firulais"))
                .andExpect(jsonPath("$.status").value("available"));
    }

    @Test
    @DisplayName("GET /api/pet/{petId} - Should handle exception")
    void testGetPetById_NotFound() throws Exception {
        // Arrange
        Long petId = 999L;
        when(petService.getPetById(eq(petId)))
                .thenThrow(new RuntimeException("Pet not found"));

        // Act & Assert
        mockMvc.perform(get("/api/pet/{petId}", petId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("POST /api/pet - Should create pet successfully")
    void testCreatePet_Success() throws Exception {
        // Arrange
        CreatePetRequest request = new CreatePetRequest();
        request.setId(1L);
        request.setName("Firulais");
        request.setStatus("available");

        CreatePetResponse response = CreatePetResponse.builder()
                .transactionId("uuid-123")
                .dateCreated("2024-01-01T00:00:00")
                .status("available")
                .name("Firulais")
                .build();

        when(petService.createPet(any(CreatePetRequest.class)))
                .thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/pet")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": 1, \"name\": \"Firulais\", \"status\": \"available\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value("uuid-123"))
                .andExpect(jsonPath("$.name").value("Firulais"))
                .andExpect(jsonPath("$.status").value("available"));
    }

    @Test
    @DisplayName("POST /api/pet - Should handle creation error")
    void testCreatePet_Error() throws Exception {
        // Arrange
        when(petService.createPet(any(CreatePetRequest.class)))
                .thenThrow(new RuntimeException("Error creating pet"));

        // Act & Assert
        mockMvc.perform(post("/api/pet")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": 1, \"name\": \"Firulais\", \"status\": \"available\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").exists());
    }
}
