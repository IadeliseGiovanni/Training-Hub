package com.traininghub.enrollment_service.service;

import com.traininghub.enrollment_service.dto.AttendanceRequestDto;
import com.traininghub.enrollment_service.dto.AttendanceResponseDto;
import com.traininghub.enrollment_service.exception.DuplicateAttendanceException;
import com.traininghub.enrollment_service.exception.InvalidAttendanceException;
import com.traininghub.enrollment_service.exception.ResourceNotFoundException;
import com.traininghub.enrollment_service.model.Attendance;
import com.traininghub.enrollment_service.repository.AttendanceRepository;
import com.traininghub.enrollment_service.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceResponseDto> findByEnrollment(Long enrollmentId) {
        ensureEnrollmentExists(enrollmentId);

        return attendanceRepository.findByEnrollmentId(enrollmentId)
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Override
    public AttendanceResponseDto create(AttendanceRequestDto request) {
        ensureEnrollmentExists(request.enrollmentId());
        validateAttendance(request);

        boolean duplicate =
                attendanceRepository.existsByEnrollmentIdAndLessonDate(
                        request.enrollmentId(),
                        request.lessonDate()
                );

        if (duplicate) {
            throw new DuplicateAttendanceException(
                    "Presenza già registrata per questa data"
            );
        }

        Attendance attendance = new Attendance();
        attendance.setEnrollmentId(request.enrollmentId());
        attendance.setLessonDate(request.lessonDate());
        attendance.setEntryTime(request.entryTime());
        attendance.setExitTime(request.exitTime());
        attendance.setAbsent(request.absent());
        attendance.setJustified(request.justified());
        attendance.setJustification(request.justification());

        Attendance savedAttendance =
                attendanceRepository.save(attendance);

        return toResponseDto(savedAttendance);
    }

    @Override
    public AttendanceResponseDto update(
            Long id,
            AttendanceRequestDto request
    ) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Presenza non trovata con id " + id
                        )
                );

        ensureEnrollmentExists(request.enrollmentId());
        validateAttendance(request);

        attendanceRepository.findByEnrollmentIdAndLessonDate(
                        request.enrollmentId(),
                        request.lessonDate()
                )
                .filter(existing ->
                        !existing.getId().equals(id)
                )
                .ifPresent(existing -> {
                    throw new DuplicateAttendanceException(
                            "Presenza già registrata per questa data"
                    );
                });

        attendance.setEnrollmentId(request.enrollmentId());
        attendance.setLessonDate(request.lessonDate());
        attendance.setEntryTime(request.entryTime());
        attendance.setExitTime(request.exitTime());
        attendance.setAbsent(request.absent());
        attendance.setJustified(request.justified());
        attendance.setJustification(request.justification());

        Attendance updatedAttendance =
                attendanceRepository.save(attendance);

        return toResponseDto(updatedAttendance);
    }

    @Override
    public void delete(Long id) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Presenza non trovata con id " + id
                        )
                );

        attendanceRepository.delete(attendance);
    }

    @Override
    @Transactional(readOnly = true)
    public double calculateAttendancePercentage(Long enrollmentId) {
        ensureEnrollmentExists(enrollmentId);

        List<Attendance> attendanceList =
                attendanceRepository.findByEnrollmentId(enrollmentId);

        if (attendanceList.isEmpty()) {
            return 0.0;
        }

        long presentLessons = attendanceList.stream()
                .filter(attendance -> !attendance.isAbsent())
                .count();

        double percentage =
                (double) presentLessons / attendanceList.size() * 100;

        return Math.round(percentage * 100.0) / 100.0;
    }

    private void validateAttendance(AttendanceRequestDto request) {
        if (request.absent()) {
            if (request.entryTime() != null
                    || request.exitTime() != null) {
                throw new InvalidAttendanceException(
                        "Un assente non può avere orari di entrata o uscita"
                );
            }

            if (request.justified()
                    && (request.justification() == null
                    || request.justification().isBlank())) {
                throw new InvalidAttendanceException(
                        "Inserire il motivo della giustificazione"
                );
            }

            return;
        }

        if (request.entryTime() == null
                || request.exitTime() == null) {
            throw new InvalidAttendanceException(
                    "Entrata e uscita sono obbligatorie"
            );
        }

        if (!request.exitTime().isAfter(request.entryTime())) {
            throw new InvalidAttendanceException(
                    "L'orario di uscita deve essere successivo all'entrata"
            );
        }
    }

    private void ensureEnrollmentExists(Long enrollmentId) {
        if (!enrollmentRepository.existsById(enrollmentId)) {
            throw new ResourceNotFoundException(
                    "Iscrizione non trovata con id " + enrollmentId
            );
        }
    }

    private AttendanceResponseDto toResponseDto(
            Attendance attendance
    ) {
        return new AttendanceResponseDto(
                attendance.getId(),
                attendance.getEnrollmentId(),
                attendance.getLessonDate(),
                attendance.getEntryTime(),
                attendance.getExitTime(),
                attendance.isAbsent(),
                attendance.isJustified(),
                attendance.getJustification(),
                calculateAttendedHours(attendance)
        );
    }

    private double calculateAttendedHours(Attendance attendance) {
        if (attendance.isAbsent()
                || attendance.getEntryTime() == null
                || attendance.getExitTime() == null) {
            return 0.0;
        }

        long minutes = Duration.between(
                attendance.getEntryTime(),
                attendance.getExitTime()
        ).toMinutes();

        return Math.round((minutes / 60.0) * 100.0) / 100.0;
    }
}