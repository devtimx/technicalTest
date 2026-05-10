package com.example.technicaltest.dto.request;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

@Data
public class CreatePetRequest {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "firulais")
    private String name;

    @Schema(example = "available")
    private String status;
}