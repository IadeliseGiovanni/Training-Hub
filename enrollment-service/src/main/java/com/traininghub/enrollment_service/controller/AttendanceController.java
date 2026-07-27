package com.traininghub.enrollment_service.controller;

import com.traininghub.enrollment_service.dto.AttendanceRequestDto;
import com.traininghub.enrollment_service.dto.AttendanceResponseDto;
import com.traininghub.enrollment_service.service.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping("/enrollment/{enrollmentId}")
    public ResponseEntity<List<AttendanceResponseDto>> findByEnrollment(
            @PathVariable Long enrollmentId
    ) {
        return ResponseEntity.ok(
                attendanceService.findByEnrollment(enrollmentId)
        );
    }

    @PostMapping
    public ResponseEntity<AttendanceResponseDto> create(
            @Valid @RequestBody AttendanceRequestDto request
    ) {
        AttendanceResponseDto response =
                attendanceService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttendanceResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody AttendanceRequestDto request
    ) {
        return ResponseEntity.ok(
                attendanceService.update(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        attendanceService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/enrollment/{enrollmentId}/percentage")
    public ResponseEntity<Double> calculatePercentage(
            @PathVariable Long enrollmentId
    ) {
        return ResponseEntity.ok(
                attendanceService.calculateAttendancePercentage(enrollmentId)
        );
    }
}