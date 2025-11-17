package com.facens.gamificacao;

import com.facens.gamificacao.dto.StudentDTO;
import com.facens.gamificacao.entity.Student;
import com.facens.gamificacao.entity.StudentEmail;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentDTOTest {

    @Test
    public void testDTOConversion() {
        StudentEmail email = new StudentEmail("a@b.com");
        Student s = new Student(1L, "Lara", 15, 2, email);

        StudentDTO dto = new StudentDTO(s);

        assertEquals(1L, dto.getId());
        assertEquals("Lara", dto.getName());
        assertEquals(15, dto.getCommentsCount());
        assertEquals(2, dto.getAvailableCourses());
        assertEquals("a@b.com", dto.getEmail());
    }
}
