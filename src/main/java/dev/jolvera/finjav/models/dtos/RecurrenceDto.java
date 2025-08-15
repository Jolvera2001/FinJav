package dev.jolvera.finjav.models.dtos;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.UUID;

public record RecurrenceDto(
        UUID id,
        @NotBlank String name,
        @NotBlank Boolean isIncome,
        @NotBlank LocalDate recurringDate
) {
}
