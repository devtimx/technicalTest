package com.example.technicalTest.controller;

import com.example.technicalTest.dto.response.PetResponse;
import com.example.technicalTest.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.technicalTest.dto.request.CreatePetRequest;
import com.example.technicalTest.dto.response.CreatePetResponse;

@RestController
@RequestMapping("/api/pet")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @GetMapping("/{petId}")
    public ResponseEntity<PetResponse> getPetById(
            @PathVariable Long petId) {

        PetResponse response = petService.getPetById(petId);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CreatePetResponse> createPet(
            @RequestBody CreatePetRequest request) {

        CreatePetResponse response
                = petService.createPet(request);

        return ResponseEntity.ok(response);
    }
}
