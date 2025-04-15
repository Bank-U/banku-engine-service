package com.banku.engineservice.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Financial analysis response containing insights and metrics")
public class AnalysisResponse {
    @Schema(description = "Period start date of the analysis", example = "2024-01-01")
    private LocalDate startDate;

    @Schema(description = "Period end date of the analysis", example = "2024-03-15")
    private LocalDate endDate;

    @Schema(description = "Total income during the analysis period", example = "5000.00")
    private BigDecimal totalIncome;

    @Schema(description = "Total expenses during the analysis period", example = "3500.00")
    private BigDecimal totalExpenses;

    @Schema(description = "Net savings during the analysis period", example = "1500.00")
    private BigDecimal netSavings;

    @Schema(description = "Monthly average income", example = "1666.67")
    private BigDecimal averageMonthlyIncome;

    @Schema(description = "Monthly average expenses", example = "1166.67")
    private BigDecimal averageMonthlyExpenses;

    @Schema(description = "Monthly average savings", example = "500.00")
    private BigDecimal averageMonthlySavings;

    @Schema(description = "Expense breakdown by category")
    private Map<String, CategoryAnalysis> categoryBreakdown;

    @Schema(description = "List of identified spending patterns")
    private List<SpendingPattern> spendingPatterns;

    @Schema(description = "List of financial alerts or warnings")
    private List<FinancialAlert> alerts;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "Analysis of spending within a specific category")
    public static class CategoryAnalysis {
        @Schema(description = "Total amount spent in this category", example = "1200.00")
        private BigDecimal total;

        @Schema(description = "Percentage of total expenses", example = "34.29")
        private BigDecimal percentage;

        @Schema(description = "Monthly average for this category", example = "400.00")
        private BigDecimal monthlyAverage;

        @Schema(description = "Month-over-month change percentage", example = "5.5")
        private BigDecimal monthOverMonthChange;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "Identified spending pattern or trend")
    public static class SpendingPattern {
        @Schema(description = "Type of spending pattern", example = "RECURRING_EXPENSE")
        private String type;

        @Schema(description = "Description of the pattern", example = "Monthly subscription payment identified")
        private String description;

        @Schema(description = "Amount involved in the pattern", example = "14.99")
        private BigDecimal amount;

        @Schema(description = "Frequency of the pattern", example = "MONTHLY")
        private String frequency;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "Financial alert or warning")
    public static class FinancialAlert {
        @Schema(description = "Type of alert", example = "HIGH_SPENDING")
        private String type;

        @Schema(description = "Severity level of the alert", example = "WARNING")
        private String severity;

        @Schema(description = "Description of the alert", example = "Unusual high spending detected in dining category")
        private String description;

        @Schema(description = "Category related to the alert", example = "DINING")
        private String category;

        @Schema(description = "Amount related to the alert", example = "450.00")
        private BigDecimal amount;
    }
} 