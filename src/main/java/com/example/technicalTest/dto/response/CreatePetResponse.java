package com.example.technicalTest.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePetResponse {

    private String transactionId;
    private String dateCreated;
    private String status;
    private String name;
}