package br.edu.ibmec.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    private int codigo;

    @Column(nullable = false)
    private String nome;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Aluno> alunos = new ArrayList<Aluno>();

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Disciplina> disciplinas = new ArrayList<Disciplina>();

    public Curso(int codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public void addDisciplina(Disciplina disciplina) {
        disciplinas.add(disciplina);
    }

    public void removeDisciplina(Disciplina disciplina) {
        disciplinas.remove(disciplina);
    }
}