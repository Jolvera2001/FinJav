package dev.jolvera.finjav.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record RecurrenceDto(
        UUID id,
        @NotNull LocalDate dateCreated,
        @NotNull LocalDate dateModified,
        @NotBlank String name,
        @NotBlank Boolean isIncome,
        @NotBlank LocalDate recurringDate
) {
}
