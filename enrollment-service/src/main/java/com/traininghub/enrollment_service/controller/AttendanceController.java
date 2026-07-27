package com.traininghub.enrollment_service.controller;

import com.traininghub.enrollment_service.dto.AttendanceRequestDto;
import com.traininghub.enrollment_service.service.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping("/enrollment/{id}")
    public Object findByEnrollment(
            @PathVariable Long id) {

        return attendanceService.findByEnrollment(id);
    }

    @PostMapping
    public Object create(
            @Valid @RequestBody AttendanceRequestDto dto) {

        return attendanceService.create(dto);
    }

    @PutMapping("/{id}")
    public Object update(
            @PathVariable Long id,
            @Valid @RequestBody AttendanceRequestDto dto) {

        return attendanceService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        attendanceService.delete(id);
    }

    @GetMapping("/enrollment/{id}/percentage")
    public double percentage(
            @PathVariable Long id) {

        return attendanceService.calculateAttendancePercentage(id);
    }
}