package com.traininghub.course_service.Service;

import com.traininghub.course_service.Dto.CourseRequestDto;
import com.traininghub.course_service.Dto.CourseResponseDto;
import com.traininghub.course_service.Dto.LessonResponseDto;
import com.traininghub.course_service.Model.Course;
import com.traininghub.course_service.Repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public List<CourseResponseDto> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CourseResponseDto getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Corso non trovato con ID: " + id));
        return mapToDto(course);
    }

    @Override
    public CourseResponseDto createCourse(CourseRequestDto requestDto) {
        Course course = Course.builder()
                .courseCode(requestDto.getCourseCode())
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .instructor(requestDto.getInstructor())
                .category(requestDto.getCategory())
                .totalHours(requestDto.getTotalHours())
                .startDate(requestDto.getStartDate())
                .endDate(requestDto.getEndDate())
                .maxCapacity(requestDto.getMaxCapacity())
                .status(requestDto.getStatus())
                .build();

        Course savedCourse = courseRepository.save(course);
        return mapToDto(savedCourse);
    }

    @Override
    public CourseResponseDto updateCourse(Long id, CourseRequestDto requestDto) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Corso non trovato con ID: " + id));

        course.setCourseCode(requestDto.getCourseCode());
        course.setTitle(requestDto.getTitle());
        course.setDescription(requestDto.getDescription());
        course.setInstructor(requestDto.getInstructor());
        course.setCategory(requestDto.getCategory());
        course.setTotalHours(requestDto.getTotalHours());
        course.setStartDate(requestDto.getStartDate());
        course.setEndDate(requestDto.getEndDate());
        course.setMaxCapacity(requestDto.getMaxCapacity());
        course.setStatus(requestDto.getStatus());

        Course updatedCourse = courseRepository.save(course);
        return mapToDto(updatedCourse);
    }

    @Override
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new RuntimeException("Impossibile eliminare. Corso non trovato con ID: " + id);
        }
        courseRepository.deleteById(id);
    }

    // Metodo di utilità per convertire Entity -> DTO
    private CourseResponseDto mapToDto(Course course) {
        List<LessonResponseDto> lessonDtos = course.getLessons() != null ?
                course.getLessons().stream().map(lesson -> LessonResponseDto.builder()
                        .id(lesson.getId())
                        .title(lesson.getTitle())
                        .videoUrl(lesson.getVideoUrl())
                        .durationMinutes(lesson.getDurationMinutes())
                        .build()).collect(Collectors.toList()) : List.of();

        return CourseResponseDto.builder()
                .id(course.getId())
                .courseCode(course.getCourseCode())
                .title(course.getTitle())
                .description(course.getDescription())
                .instructor(course.getInstructor())
                .category(course.getCategory())
                .totalHours(course.getTotalHours())
                .startDate(course.getStartDate())
                .endDate(course.getEndDate())
                .maxCapacity(course.getMaxCapacity())
                .status(course.getStatus())
                .lessons(lessonDtos)
                .build();
    }
}