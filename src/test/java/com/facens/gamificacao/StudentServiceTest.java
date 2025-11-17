package com.facens.gamificacao;

import com.facens.gamificacao.dto.StudentDTO;
import com.facens.gamificacao.entity.Student;
import com.facens.gamificacao.entity.StudentEmail;
import com.facens.gamificacao.repository.StudentRepository;
import com.facens.gamificacao.service.StudentService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

public class StudentServiceTest {

    @Mock
    private StudentRepository repository;

    @InjectMocks
    private StudentService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Student s = new Student(1L, "Ana", 10, 1, new StudentEmail("a@b.com"));
        when(repository.findAll()).thenReturn(Arrays.asList(s));

        var list = service.findAll();

        assertEquals(1, list.size());
        assertEquals("Ana", list.get(0).getName());
    }

    @Test
    void testSave() {
        Student s = new Student(2L, "Leo", 9, 1, new StudentEmail("c@d.com"));
        when(repository.save(s)).thenReturn(s);

        Student saved = service.save(s);

        assertEquals("Leo", saved.getName());
    }

    @Test
    void testFindById() {
        Student s = new Student(1L, "Ana", 10, 1, new StudentEmail("a@b.com"));
        when(repository.findById(1L)).thenReturn(Optional.of(s));

        StudentDTO dto = service.findById(1L);

        assertEquals("Ana", dto.getName());
    }

    @Test
    void testProcessRewards() {
        Student s1 = new Student(1L, "A", 5, 0, new StudentEmail("a@b.com"));
        Student s2 = new Student(2L, "B", 10, 0, new StudentEmail("b@b.com"));

        when(repository.findAll()).thenReturn(Arrays.asList(s1, s2));

        service.processRewards();

        assertEquals(1, s2.getAvailableCourses());
        verify(repository).save(s2);
    }
}
