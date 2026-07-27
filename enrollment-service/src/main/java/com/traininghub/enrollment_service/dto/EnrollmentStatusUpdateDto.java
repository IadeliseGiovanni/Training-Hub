package com.traininghub.enrollment_service.dto;

import com.traininghub.enrollment_service.model.EnrollmentStatus;
import jakarta.validation.constraints.NotNull;

public record EnrollmentStatusUpdateDto(

        @NotNull(message = "Lo stato dell'iscrizione è obbligatorio")
        EnrollmentStatus status
) {
}