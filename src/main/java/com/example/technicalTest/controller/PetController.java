package com.example.technicaltest.controller;

import com.example.technicaltest.dto.response.PetResponse;
import com.example.technicaltest.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.technicaltest.dto.request.CreatePetRequest;
import com.example.technicaltest.dto.response.CreatePetResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/pet")
@RequiredArgsConstructor
@Tag(name = "Pet API", description = "Operaciones relacionadas con mascotas")
public class PetController {

    private final PetService petService;
    @Operation(summary = "Obtener mascota por ID")
    @GetMapping("/{petId}")
    public ResponseEntity<PetResponse> getPetById(
            @PathVariable Long petId) {

        PetResponse response = petService.getPetById(petId);

        return ResponseEntity.ok(response);
    }
    @Operation(summary = "Crear una nueva mascota")
    @PostMapping
    public ResponseEntity<CreatePetResponse> createPet(
            @RequestBody CreatePetRequest request) {

        CreatePetResponse response
                = petService.createPet(request);

        return ResponseEntity.ok(response);
    }
}
