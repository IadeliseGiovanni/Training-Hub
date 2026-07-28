package com.traininghub.course_service.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponseDto {
    private Long id;
    private String courseCode;
    private String title;
    private String description;
    private String instructor;
    private String category;
    private Integer totalHours;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer maxCapacity;
    private String status;
    private List<LessonResponseDto> lessons;
}