package com.facens.gamificacao.dto;

import com.facens.gamificacao.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private Long id;
    private String name;
    private int commentsCount;
    private int availableCourses;
    private String email;

    public StudentDTO(Student s) {
        this.id = s.getId();
        this.name = s.getName();
        this.commentsCount = s.getCommentsCount();
        this.availableCourses = s.getAvailableCourses();
        this.email = s.getEmail() != null ? s.getEmail().getAddress() : null;
    }
}
