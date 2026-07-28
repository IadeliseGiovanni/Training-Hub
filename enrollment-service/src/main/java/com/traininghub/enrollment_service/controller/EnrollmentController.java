package com.traininghub.enrollment_service.controller;

import com.traininghub.enrollment_service.dto.EnrollmentRequestDto;
import com.traininghub.enrollment_service.dto.EnrollmentResponseDto;
import com.traininghub.enrollment_service.dto.EnrollmentStatusUpdateDto;
import com.traininghub.enrollment_service.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @GetMapping
    public ResponseEntity<List<EnrollmentResponseDto>> findAll() {
        return ResponseEntity.ok(
                enrollmentService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentResponseDto> findById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                enrollmentService.findById(id)
        );
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<EnrollmentResponseDto>> findByCourseId(
            @PathVariable Long courseId
    ) {
        return ResponseEntity.ok(
                enrollmentService.findByCourseId(courseId)
        );
    }

    @GetMapping("/participant/{participantId}")
    public ResponseEntity<List<EnrollmentResponseDto>> findByParticipantId(
            @PathVariable Long participantId
    ) {
        return ResponseEntity.ok(
                enrollmentService.findByParticipantId(participantId)
        );
    }

    @PostMapping
    public ResponseEntity<EnrollmentResponseDto> create(
            @Valid @RequestBody EnrollmentRequestDto request
    ) {
        EnrollmentResponseDto response =
                enrollmentService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<EnrollmentResponseDto> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody EnrollmentStatusUpdateDto request
    ) {
        return ResponseEntity.ok(
                enrollmentService.updateStatus(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        enrollmentService.delete(id);

        return ResponseEntity.noContent().build();
    }
}