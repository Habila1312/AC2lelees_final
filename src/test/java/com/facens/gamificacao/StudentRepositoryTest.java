package com.facens.gamificacao;

import com.facens.gamificacao.entity.Student;
import com.facens.gamificacao.entity.StudentEmail;
import com.facens.gamificacao.repository.StudentRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void testSaveAndFind() {
        Student s1 = new Student(null, "Lara", 15, 2, new StudentEmail("l@e.com"));
        Student s2 = new Student(null, "Joao", 5, 1, new StudentEmail("j@e.com"));

        studentRepository.save(s1);
        studentRepository.save(s2);

        List<Student> all = studentRepository.findAll();

        assertEquals(2, all.size());
    }

    @Test
    public void testFindById() {
        Student s1 = new Student(null, "Lara", 15, 2, new StudentEmail("l@e.com"));
        Student saved = studentRepository.save(s1);

        assertTrue(studentRepository.findById(saved.getId()).isPresent());
    }
}
