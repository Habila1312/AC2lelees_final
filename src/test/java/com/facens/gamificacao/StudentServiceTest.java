package com.facens.gamificacao;

import com.facens.gamificacao.dto.StudentDTO;
import com.facens.gamificacao.entity.Student;
import com.facens.gamificacao.entity.StudentEmail;
import com.facens.gamificacao.repository.StudentRepository;
import com.facens.gamificacao.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentServiceTest {

    private StudentRepository repository = mock(StudentRepository.class);
    private StudentService service;

    @BeforeEach
    public void setup() {
        service = new StudentService();
        // injeta o mock
        java.lang.reflect.Field field;
        try {
            field = StudentService.class.getDeclaredField("studentRepository");
            field.setAccessible(true);
            field.set(service, repository);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testFindById() {
        Student s = new Student(1L, "Lara", 10, 1, new StudentEmail("a@b.com"));
        when(repository.findById(1L)).thenReturn(Optional.of(s));

        StudentDTO dto = service.findById(1L);

        assertEquals("Lara", dto.getName());
    }

    @Test
    public void testFindEntityById() {
        Student s = new Student(1L, "Lara", 10, 1, new StudentEmail("a@b.com"));
        when(repository.findById(1L)).thenReturn(Optional.of(s));

        Student result = service.findEntityById(1L);

        assertEquals("Lara", result.getName());
    }

    @Test
    public void testProcessRewards() {
        Student s1 = new Student(1L, "A", 5, 1, new StudentEmail("a@a.com"));
        Student s2 = new Student(2L, "B", 10, 2, new StudentEmail("b@b.com"));
        when(repository.findAll()).thenReturn(Arrays.asList(s1, s2));

        service.processRewards();

        verify(repository, times(1)).save(s2);
        assertEquals(3, s2.getAvailableCourses());
    }
}
