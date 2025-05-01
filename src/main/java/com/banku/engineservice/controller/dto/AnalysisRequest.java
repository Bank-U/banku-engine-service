package com.banku.engineservice.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request for financial data analysis")
public class AnalysisRequest {
    @Schema(description = "List of account IDs to analyze", example = "[\"acc_123\", \"acc_456\"]")
    @NotEmpty(message = "At least one account ID must be provided")
    private List<String> accountIds;

    @Schema(description = "Start date for the analysis period", example = "2024-01-01")
    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @Schema(description = "End date for the analysis period", example = "2024-03-15")
    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @Schema(description = "Categories to include in the analysis", example = "[\"INCOME\", \"EXPENSES\", \"SAVINGS\"]")
    private List<String> categories;

    @Schema(description = "Whether to include detailed transaction analysis", example = "true")
    private boolean includeTransactions;

    @Schema(description = "Whether to generate recommendations based on the analysis", example = "true")
    private boolean generateRecommendations;
} 