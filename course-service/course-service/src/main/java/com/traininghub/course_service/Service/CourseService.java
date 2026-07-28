package com.traininghub.course_service.Service;

import com.traininghub.course_service.Dto.CourseRequestDto;
import com.traininghub.course_service.Dto.CourseResponseDto;
import java.util.List;

public interface CourseService {
    List<CourseResponseDto> getAllCourses();
    CourseResponseDto getCourseById(Long id);
    CourseResponseDto createCourse(CourseRequestDto requestDto);
    CourseResponseDto updateCourse(Long id, CourseRequestDto requestDto);
    void deleteCourse(Long id);
}