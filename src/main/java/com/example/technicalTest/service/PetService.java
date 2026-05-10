
package com.example.technicaltest.service;

import com.example.technicaltest.dto.request.CreatePetRequest;
import com.example.technicaltest.dto.response.CreatePetResponse;
import com.example.technicaltest.dto.response.PetResponse;

public interface PetService {

    PetResponse getPetById(Long petId);
    CreatePetResponse createPet(CreatePetRequest request);
}