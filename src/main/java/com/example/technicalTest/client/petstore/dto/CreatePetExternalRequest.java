package com.example.technicalTest.client.petstore.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePetExternalRequest {

    private Long id;
    private String name;
    private String status;
}