package com.traininghub.enrollment_service.service;

import com.traininghub.enrollment_service.dto.AttendanceRequestDto;
import com.traininghub.enrollment_service.dto.AttendanceResponseDto;

import java.util.List;

public interface AttendanceService {

    List<AttendanceResponseDto> findByEnrollment(Long enrollmentId);

    AttendanceResponseDto create(AttendanceRequestDto request);

    AttendanceResponseDto update(
            Long id,
            AttendanceRequestDto request
    );

    void delete(Long id);

    double calculateAttendancePercentage(Long enrollmentId);
}