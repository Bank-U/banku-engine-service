package com.banku.engineservice.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Financial recommendation response")
public class RecommendationResponse {
    @Schema(description = "Unique identifier of the recommendation", example = "rec_123")
    private String id;

    @Schema(description = "Type of recommendation", example = "SAVINGS_OPPORTUNITY")
    private String type;

    @Schema(description = "Category the recommendation relates to", example = "DINING")
    private String category;

    @Schema(description = "Title of the recommendation", example = "Reduce Dining Expenses")
    private String title;

    @Schema(description = "Detailed description of the recommendation", 
            example = "Your dining expenses are 30% higher than last month. Consider cooking more meals at home.")
    private String description;

    @Schema(description = "Priority level of the recommendation", example = "HIGH")
    private String priority;

    @Schema(description = "Potential impact amount of following the recommendation", example = "200.00")
    private BigDecimal potentialImpact;

    @Schema(description = "Time frame for implementing the recommendation", example = "SHORT_TERM")
    private String timeFrame;

    @Schema(description = "List of specific action steps to implement the recommendation")
    private List<ActionStep> actionSteps;

    @Schema(description = "Metrics related to the recommendation")
    private RecommendationMetrics metrics;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "Action step for implementing the recommendation")
    public static class ActionStep {
        @Schema(description = "Step number in the sequence", example = "1")
        private int stepNumber;

        @Schema(description = "Description of the action step", 
                example = "Review your dining transactions from the past month")
        private String description;

        @Schema(description = "Expected outcome of completing this step", 
                example = "Identify patterns in dining expenses")
        private String expectedOutcome;

        @Schema(description = "Estimated time to complete this step", example = "15")
        private Integer estimatedMinutes;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "Metrics related to the recommendation")
    public static class RecommendationMetrics {
        @Schema(description = "Current amount or value", example = "500.00")
        private BigDecimal currentAmount;

        @Schema(description = "Target amount or value", example = "350.00")
        private BigDecimal targetAmount;

        @Schema(description = "Percentage change needed", example = "30.00")
        private BigDecimal percentageChange;

        @Schema(description = "Historical average amount", example = "400.00")
        private BigDecimal historicalAverage;

        @Schema(description = "Peer comparison percentage", example = "25.00")
        private BigDecimal peerComparison;
    }
} 