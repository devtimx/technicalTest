package com.example.technicaltest.client.petstore.dto;

import lombok.Data;

@Data
public class PetStoreResponse {

    private Long id;
    private String name;
    private String status;
}