package com.traininghub.enrollment_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceRequestDto(

        @NotNull(message = "L'identificativo dell'iscrizione è obbligatorio")
        @Positive(message = "L'identificativo dell'iscrizione deve essere positivo")
        Long enrollmentId,

        @NotNull(message = "La data della lezione è obbligatoria")
        @PastOrPresent(message = "La data della lezione non può essere futura")
        LocalDate lessonDate,

        LocalTime entryTime,

        LocalTime exitTime,

        boolean absent,

        boolean justified,

        @Size(
                max = 500,
                message = "La giustificazione non può superare 500 caratteri"
        )
        String justification
) {
}