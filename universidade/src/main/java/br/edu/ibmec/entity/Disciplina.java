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
@Table(name = "disciplinas")
public class Disciplina {

    @Id
    private int codigo;

    @Column(nullable = false)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_codigo")
    private Curso curso;

    @OneToMany(mappedBy = "disciplina", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Turma> turmas = new ArrayList<Turma>();

    public Disciplina(int codigo, String nome, Curso curso) {
        this.codigo = codigo;
        this.nome = nome;
        this.curso = curso;
    }

    public void addTurma(Turma turma) {
        turmas.add(turma);
    }

    public void removeTurma(Turma turma) {
        turmas.remove(turma);
    }
}
