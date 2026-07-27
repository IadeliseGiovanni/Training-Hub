package com.traininghub.enrollment_service.controller;

import com.traininghub.enrollment_service.dto.EnrollmentRequestDto;
import com.traininghub.enrollment_service.dto.EnrollmentStatusUpdateDto;
import com.traininghub.enrollment_service.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @GetMapping
    public Object getAll() {
        return enrollmentService.findAll();
    }

    @GetMapping("/{id}")
    public Object getById(@PathVariable Long id) {
        return enrollmentService.findById(id);
    }

    @PostMapping
    public Object create(
            @Valid @RequestBody EnrollmentRequestDto dto) {

        return enrollmentService.create(dto);
    }

    @PatchMapping("/{id}/status")
    public Object updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody EnrollmentStatusUpdateDto dto) {

        return enrollmentService.updateStatus(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        enrollmentService.delete(id);
    }
}