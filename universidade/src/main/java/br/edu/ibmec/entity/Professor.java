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
@Table(name = "professores")
public class Professor {

    @Id
    private int codigo;

    @Column(nullable = false)
    private String nome;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Turma> turmas = new ArrayList<Turma>();

    public Professor(int codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public void addTurma(Turma turma) {
        turmas.add(turma);
    }

    public void removeTurma(Turma turma) {
        turmas.remove(turma);
    }
}
