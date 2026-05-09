package com.example.technicalTest.dto.request;

import lombok.Data;

@Data
public class CreatePetRequest {

    private Long id;
    private String name;
    private String status;
}