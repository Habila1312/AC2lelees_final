package com.facens.gamificacao.service;

import com.facens.gamificacao.dto.StudentDTO;
import com.facens.gamificacao.entity.Student;
import com.facens.gamificacao.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // Buscar todos os estudantes
    public List<StudentDTO> findAll() {
        return studentRepository.findAll()
                .stream()
                .map(StudentDTO::new)
                .collect(Collectors.toList());
    }

    // Salvar estudante
    public Student save(Student s) {
        return studentRepository.save(s);
    }

    // O QUE O CONTROLLER E OS TESTES ESPERAM
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    // Se você quiser continuar usando DTO internamente (opcional)
    public StudentDTO findDtoById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return new StudentDTO(student);
    }

    // Lógica de gamificação
    public void processRewards() {
        List<Student> students = studentRepository.findAll();
        if (students == null || students.isEmpty()) {
            return;
        }

        Student mostActive = students.stream()
                .max(Comparator.comparingInt(Student::getCommentsCount))
                .orElse(null);

        if (mostActive != null) {
            mostActive.setAvailableCourses(mostActive.getAvailableCourses() + 1);
            studentRepository.save(mostActive);
        }
    }
}
