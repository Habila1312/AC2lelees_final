package com.facens.gamificacao;

import com.facens.gamificacao.entity.Student;
import com.facens.gamificacao.entity.StudentEmail;
import com.facens.gamificacao.repository.StudentRepository;
import com.facens.gamificacao.service.StudentService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentServiceTest {

    @Test
    public void testFindAll() {
        StudentRepository repo = mock(StudentRepository.class);
        StudentService service = new StudentService(repo);

        Student s = new Student(1L, "Lara", 10, 1, new StudentEmail("a@b.com"));

        when(repo.findAll()).thenReturn(Arrays.asList(s));

        List<Student> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals("Lara", result.get(0).getName());
    }

    @Test
    public void testFindById() {
        StudentRepository repo = mock(StudentRepository.class);
        StudentService service = new StudentService(repo);

        Student s = new Student(1L, "Lara", 10, 1, new StudentEmail("a@b.com"));

        when(repo.findById(1L)).thenReturn(Optional.of(s));

        Optional<Student> result = service.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Lara", result.get().getName());
    }

    @Test
    public void testSave() {
        StudentRepository repo = mock(StudentRepository.class);
        StudentService service = new StudentService(repo);

        Student s = new Student(null, "Lara", 10, 1, new StudentEmail("a@b.com"));

        when(repo.save(s)).thenReturn(
                new Student(1L, "Lara", 10, 1, new StudentEmail("a@b.com"))
        );

        Student saved = service.save(s);

        assertEquals(1L, saved.getId());
    }
}
