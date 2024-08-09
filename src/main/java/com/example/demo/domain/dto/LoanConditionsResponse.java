package com.example.demo.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Conditions for loans")
public class LoanConditionsResponse {
    @Schema(description = "Max amount of loan")
    private int maxAmount;

    @Schema(description = "Period of loan")
    private int period;

    @Schema(description = "Percent of loan")
    private double percent;
}
