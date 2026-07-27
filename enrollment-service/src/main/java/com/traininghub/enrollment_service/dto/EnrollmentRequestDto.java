package com.traininghub.enrollment_service.dto;

import com.traininghub.enrollment_service.model.EnrollmentStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record EnrollmentRequestDto(

        @NotNull(message = "L'identificativo del corso è obbligatorio")
        @Positive(message = "L'identificativo del corso deve essere positivo")
        Long courseId,

        @NotNull(message = "L'identificativo del partecipante è obbligatorio")
        @Positive(message = "L'identificativo del partecipante deve essere positivo")
        Long participantId,

        @PastOrPresent(message = "La data di iscrizione non può essere futura")
        LocalDate enrollmentDate,

        EnrollmentStatus status
) {
}