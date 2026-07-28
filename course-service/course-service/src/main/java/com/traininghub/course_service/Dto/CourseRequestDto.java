package com.traininghub.course_service.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class CourseRequestDto {

    @NotBlank(message = "Il codice corso è obbligatorio")
    private String courseCode;

    @NotBlank(message = "Il titolo del corso non può essere vuoto")
    private String title;

    private String description;

    @NotBlank(message = "L'istruttore è obbligatorio")
    private String instructor;

    @NotBlank(message = "La categoria è obbligatoria")
    private String category;

    @NotNull(message = "Le ore totali sono obbligatorie")
    private Integer totalHours;

    private LocalDate startDate;
    private LocalDate endDate;
    private Integer maxCapacity;
    private String status;
}