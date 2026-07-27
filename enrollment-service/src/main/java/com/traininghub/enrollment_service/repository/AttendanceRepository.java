package com.traininghub.enrollment_service.repository;

import com.traininghub.enrollment_service.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository
        extends JpaRepository<Attendance, Long> {

    List<Attendance> findByEnrollmentId(Long enrollmentId);

    Optional<Attendance> findByEnrollmentIdAndLessonDate(
            Long enrollmentId,
            LocalDate lessonDate
    );

    boolean existsByEnrollmentIdAndLessonDate(
            Long enrollmentId,
            LocalDate lessonDate
    );

    void deleteByEnrollmentId(Long enrollmentId);
}