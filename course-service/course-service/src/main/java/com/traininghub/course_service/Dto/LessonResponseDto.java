package com.traininghub.course_service.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonResponseDto {
    private Long id;
    private String title;
    private String videoUrl;
    private Integer durationMinutes;
}