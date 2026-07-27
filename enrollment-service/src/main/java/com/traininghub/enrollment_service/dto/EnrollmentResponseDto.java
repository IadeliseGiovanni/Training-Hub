package com.traininghub.enrollment_service.dto;

import com.traininghub.enrollment_service.model.EnrollmentStatus;

import java.time.LocalDate;

public record EnrollmentResponseDto(
        Long id,
        Long courseId,
        Long participantId,
        LocalDate enrollmentDate,
        EnrollmentStatus status
) {
}