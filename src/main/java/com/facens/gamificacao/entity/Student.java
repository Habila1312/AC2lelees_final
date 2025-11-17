package com.facens.gamificacao.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tb_student")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int commentsCount;
    private int availableCourses;
    @Embedded
    private StudentEmail email;

    // Construtor adicional para testes sem precisar do email
    public Student(Long id, String name, int commentsCount, int availableCourses) {
        this.id = id;
        this.name = name;
        this.commentsCount = commentsCount;
        this.availableCourses = availableCourses;
        this.email = null; // email fica nulo
    }
}
