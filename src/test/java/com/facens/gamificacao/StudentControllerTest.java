package com.facens.gamificacao.controller;

import com.facens.gamificacao.dto.StudentDTO;
import com.facens.gamificacao.entity.Student;
import com.facens.gamificacao.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController controller;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        StudentDTO s1 = new StudentDTO(new Student(1L, "A", 2, 1));
        StudentDTO s2 = new StudentDTO(new Student(2L, "B", 5, 2));

        when(studentService.findAll()).thenReturn(Arrays.asList(s1, s2));

        List<StudentDTO> result = controller.getAll();

        assertEquals(2, result.size());
    }

    @Test
    void testGetByIdFound() {
        Student s = new Student(1L, "Teste", 4, 1);
        when(studentService.findById(1L)).thenReturn(Optional.of(s));

        ResponseEntity<StudentDTO> response = controller.getById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Teste", response.getBody().getName());
    }

    @Test
    void testGetByIdNotFound() {
        when(studentService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<StudentDTO> response = controller.getById(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreate() {
        Student s = new Student(1L, "Novo", 3, 0);
        when(studentService.save(any(Student.class))).thenReturn(s);

        ResponseEntity<StudentDTO> response = controller.create(s);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(URI.create("/students/1"), response.getHeaders().getLocation());
    }

    @Test
    void testProcessRewards() {
        ResponseEntity<Void> response = controller.processRewards();

        verify(studentService, times(1)).processRewards();
        assertEquals(200, response.getStatusCodeValue());
    }
}
