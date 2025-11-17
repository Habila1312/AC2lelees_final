package com.facens.gamificacao;

import com.facens.gamificacao.dto.StudentDTO;
import com.facens.gamificacao.entity.Student;
import com.facens.gamificacao.entity.StudentEmail;
import com.facens.gamificacao.repository.StudentRepository;
import com.facens.gamificacao.service.StudentService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    public StudentServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {

        Student s1 = new Student(1L, "Lara", 10, 1, new StudentEmail("a@b.com"));
        Student s2 = new Student(2L, "Joao", 5, 3, new StudentEmail("c@d.com"));

        when(studentRepository.findAll()).thenReturn(Arrays.asList(s1, s2));

        List<StudentDTO> result = studentService.findAll();

        assertEquals(2, result.size());
        assertEquals("Lara", result.get(0).getName());
    }

    @Test
    public void testFindById() {

        Student s1 = new Student(1L, "Lara", 10, 1, new StudentEmail("a@b.com"));

        when(studentRepository.findById(1L)).thenReturn(Optional.of(s1));

        StudentDTO result = studentService.findById(1L);

        assertEquals("Lara", result.getName());
    }

    @Test
    public void testSave() {

        Student s = new Student(null, "Lara", 10, 1, new StudentEmail("a@b.com"));

        when(studentRepository.save(s)).thenReturn(s);

        Student saved = studentService.save(s);

        assertEquals("Lara", saved.getName());
    }

    @Test
    public void testProcessRewards() {

        Student s1 = new Student(1L, "Lara", 20, 1, new StudentEmail("a@b.com"));
        Student s2 = new Student(2L, "Joao", 5, 1, new StudentEmail("c@d.com"));

        when(studentRepository.findAll()).thenReturn(Arrays.asList(s1, s2));

        studentService.processRewards();

        assertEquals(2, s1.getAvailableCourses());
        verify(studentRepository, times(1)).save(s1);
    }
}
