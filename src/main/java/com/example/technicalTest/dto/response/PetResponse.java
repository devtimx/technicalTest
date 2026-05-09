
package com.example.technicalTest.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PetResponse {

    private Long id;
    private String name;
    private String status;
}