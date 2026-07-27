package com.traininghub.enrollment_service.repository;

import com.traininghub.enrollment_service.model.Enrollment;
import com.traininghub.enrollment_service.model.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository
        extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByCourseId(Long courseId);

    List<Enrollment> findByParticipantId(Long participantId);

    List<Enrollment> findByStatus(EnrollmentStatus status);

    boolean existsByCourseIdAndParticipantId(
            Long courseId,
            Long participantId
    );

    long countByCourseId(Long courseId);

    long countByCourseIdAndStatus(
            Long courseId,
            EnrollmentStatus status
    );
}