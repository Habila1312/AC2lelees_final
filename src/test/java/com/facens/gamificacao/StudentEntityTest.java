package com.facens.gamificacao;

import com.facens.gamificacao.entity.Student;
import com.facens.gamificacao.entity.StudentEmail;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentEntityTest {

    @Test
    public void testStudentLombokAndFields() {

        StudentEmail email = new StudentEmail("a@b.com");
        Student s = new Student(1L, "Lara", 15, 2, email);

        assertEquals("Lara", s.getName());
        assertEquals(15, s.getCommentsCount());
        assertEquals(2, s.getAvailableCourses());
        assertEquals("a@b.com", s.getEmail().getAddress());
        assertNotNull(s.toString());
    }

    @Test
    public void testEqualsAndHashCode() {
        StudentEmail e = new StudentEmail("a@b.com");
        Student s1 = new Student(1L, "Lara", 15, 2, e);
        Student s2 = new Student(1L, "Lara", 15, 2, e);

        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
    }
}
