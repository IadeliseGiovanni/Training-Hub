package com.traininghub.enrollment_service.service;

import com.traininghub.enrollment_service.dto.EnrollmentRequestDto;
import com.traininghub.enrollment_service.dto.EnrollmentResponseDto;
import com.traininghub.enrollment_service.dto.EnrollmentStatusUpdateDto;

import java.util.List;

public interface EnrollmentService {

    List<EnrollmentResponseDto> findAll();

    EnrollmentResponseDto findById(Long id);

    List<EnrollmentResponseDto> findByCourseId(Long courseId);

    List<EnrollmentResponseDto> findByParticipantId(Long participantId);

    EnrollmentResponseDto create(EnrollmentRequestDto request);

    EnrollmentResponseDto updateStatus(
            Long id,
            EnrollmentStatusUpdateDto request
    );

    void delete(Long id);
}