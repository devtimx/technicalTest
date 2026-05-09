
package com.example.technicalTest.service;

import com.example.technicalTest.dto.request.CreatePetRequest;
import com.example.technicalTest.dto.response.CreatePetResponse;
import com.example.technicalTest.dto.response.PetResponse;

public interface PetService {

    PetResponse getPetById(Long petId);
    CreatePetResponse createPet(CreatePetRequest request);
}