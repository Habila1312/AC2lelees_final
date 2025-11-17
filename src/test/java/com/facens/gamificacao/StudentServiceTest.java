package com.facens.gamificacao;

import com.facens.gamificacao.dto.StudentDTO;
import com.facens.gamificacao.entity.Student;
import com.facens.gamificacao.repository.StudentRepository;
import com.facens.gamificacao.service.StudentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentServiceTest {

    @Mock
    private StudentRepository repository;

    @InjectMocks
    private StudentService service;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Student s = new Student(1L, "Ana", 10, 1);

        when(repository.findAll()).thenReturn(Arrays.asList(s));

        var list = service.findAll();

        assertEquals(1, list.size());
        assertEquals("Ana", list.get(0).getName());
    }

    @Test
    void testFindById() {
        Student s = new Student(1L, "Ana", 10, 1);

        when(repository.findById(1L)).thenReturn(Optional.of(s));

        StudentDTO dto = service.findById(1L);

        assertEquals("Ana", dto.getName());
    }

    @Test
    void testSave() {
        Student s = new Student(2L, "Leo", 9, 1);

        when(repository.save(any())).thenReturn(s);

        Student saved = service.save(s);

        assertEquals("Leo", saved.getName());
    }

    @Test
    void testProcessRewards() {
        Student s1 = new Student(1L, "A", 5, 0);
        Student s2 = new Student(2L, "B", 10, 0);

        when(repository.findAll()).thenReturn(Arrays.asList(s1, s2));

        service.processRewards();

        assertEquals(1, s2.getAvailableCourses());
        verify(repository).save(s2);
    }
}
