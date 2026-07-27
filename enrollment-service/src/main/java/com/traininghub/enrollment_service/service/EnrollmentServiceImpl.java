package com.traininghub.enrollment_service.service;

import com.traininghub.enrollment_service.dto.EnrollmentRequestDto;
import com.traininghub.enrollment_service.dto.EnrollmentResponseDto;
import com.traininghub.enrollment_service.dto.EnrollmentStatusUpdateDto;
import com.traininghub.enrollment_service.exception.DuplicateEnrollmentException;
import com.traininghub.enrollment_service.exception.ResourceNotFoundException;
import com.traininghub.enrollment_service.model.Enrollment;
import com.traininghub.enrollment_service.model.EnrollmentStatus;
import com.traininghub.enrollment_service.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EnrollmentResponseDto> findAll() {
        return enrollmentRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EnrollmentResponseDto findById(Long id) {
        Enrollment enrollment = findEntityById(id);
        return toResponseDto(enrollment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnrollmentResponseDto> findByCourseId(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId)
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnrollmentResponseDto> findByParticipantId(Long participantId) {
        return enrollmentRepository.findByParticipantId(participantId)
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Override
    public EnrollmentResponseDto create(EnrollmentRequestDto request) {
        boolean duplicate =
                enrollmentRepository.existsByCourseIdAndParticipantId(
                        request.courseId(),
                        request.participantId()
                );

        if (duplicate) {
            throw new DuplicateEnrollmentException(
                    "Il partecipante è già iscritto al corso"
            );
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setCourseId(request.courseId());
        enrollment.setParticipantId(request.participantId());

        enrollment.setEnrollmentDate(
                request.enrollmentDate() != null
                        ? request.enrollmentDate()
                        : LocalDate.now()
        );

        enrollment.setStatus(
                request.status() != null
                        ? request.status()
                        : EnrollmentStatus.PENDING
        );

        Enrollment savedEnrollment =
                enrollmentRepository.save(enrollment);

        return toResponseDto(savedEnrollment);
    }

    @Override
    public EnrollmentResponseDto updateStatus(
            Long id,
            EnrollmentStatusUpdateDto request
    ) {
        Enrollment enrollment = findEntityById(id);

        enrollment.setStatus(request.status());

        Enrollment updatedEnrollment =
                enrollmentRepository.save(enrollment);

        return toResponseDto(updatedEnrollment);
    }

    @Override
    public void delete(Long id) {
        Enrollment enrollment = findEntityById(id);
        enrollmentRepository.delete(enrollment);
    }

    private Enrollment findEntityById(Long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Iscrizione non trovata con id " + id
                        )
                );
    }

    private EnrollmentResponseDto toResponseDto(Enrollment enrollment) {
        return new EnrollmentResponseDto(
                enrollment.getId(),
                enrollment.getCourseId(),
                enrollment.getParticipantId(),
                enrollment.getEnrollmentDate(),
                enrollment.getStatus()
        );
    }
}