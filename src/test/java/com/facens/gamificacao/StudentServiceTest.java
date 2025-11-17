package com.facens.gamificacao.service;

import com.facens.gamificacao.dto.StudentDTO;
import com.facens.gamificacao.entity.Student;
import com.facens.gamificacao.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Student s1 = new Student(1L, "Joao", 5, 2);
        Student s2 = new Student(2L, "Maria", 10, 3);

        when(studentRepository.findAll()).thenReturn(Arrays.asList(s1, s2));

        List<StudentDTO> result = studentService.findAll();

        assertEquals(2, result.size());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void testSave() {
        Student s = new Student(1L, "Ana", 7, 1);
        when(studentRepository.save(any(Student.class))).thenReturn(s);

        Student saved = studentService.save(s);

        assertNotNull(saved);
        assertEquals("Ana", saved.getName());
        verify(studentRepository, times(1)).save(s);
    }

    @Test
    void testFindByIdFound() {
        Student s = new Student(1L, "Carlos", 4, 0);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(s));

        Optional<Student> result = studentService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Carlos", result.get().getName());
    }

    @Test
    void testFindByIdNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Student> result = studentService.findById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void testFindDtoById() {
        Student s = new Student(1L, "Test", 8, 1);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(s));

        StudentDTO dto = studentService.findDtoById(1L);

        assertEquals("Test", dto.getName());
    }

    @Test
    void testProcessRewardsNoStudents() {
        when(studentRepository.findAll()).thenReturn(Arrays.asList());

        studentService.processRewards();

        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void testProcessRewardsSelectMostActive() {
        Student s1 = new Student(1L, "Low", 1, 0);
        Student s2 = new Student(2L, "High", 10, 3);

        when(studentRepository.findAll()).thenReturn(Arrays.asList(s1, s2));

        studentService.processRewards();

        assertEquals(4, s2.getAvailableCourses());
        verify(studentRepository, times(1)).save(s2);
    }
}
