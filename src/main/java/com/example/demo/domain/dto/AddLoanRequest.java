package com.example.demo.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Add new loan request")
public class AddLoanRequest {
    @Schema(description = "Amount of loan", example = "100.0")
    @NotBlank(message = "Amount cannot be blank")
    private double amount;

    @Schema(description = "Owner's loan first name")
    @NotBlank(message = "Owner's loan first name cannot be blank")
    private String firstName;

    @Schema(description = "Owner's loan last name")
    @NotBlank(message = "Owner's loan last name cannot be blank")
    private String lastName;

    @Schema(description = "Percent of loan")
    @NotBlank(message = "Percent of loan cannot be blank")
    private double percent;

    @Schema(description = "Period of loan")
    @NotBlank(message = "Period of loan cannot be blank")
    private int period;

    @Schema(description = "Owner's phone number")
    @NotBlank(message = "Owner's phone number cannot be blank")
    private String phoneNumber;
}
