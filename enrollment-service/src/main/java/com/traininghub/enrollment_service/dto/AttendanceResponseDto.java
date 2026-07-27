package com.traininghub.enrollment_service.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceResponseDto(
        Long id,
        Long enrollmentId,
        LocalDate lessonDate,
        LocalTime entryTime,
        LocalTime exitTime,
        boolean absent,
        boolean justified,
        String justification,
        double attendedHours
) {
}