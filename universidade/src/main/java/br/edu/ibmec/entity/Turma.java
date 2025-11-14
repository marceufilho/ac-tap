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
@Table(name = "turmas")
public class Turma {

    @Id
    private int codigo;

    @Column(nullable = false)
    private int ano;

    @Column(nullable = false)
    private int semestre;

    @Column(nullable = false)
    private double valorTurma = 300.0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disciplina_codigo")
    private Disciplina disciplina;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_codigo")
    private Professor professor;

    @OneToMany(mappedBy = "id.turma", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Inscricao> inscricoes = new ArrayList<Inscricao>();

    public Turma(int codigo, int ano, int semestre, Disciplina disciplina, Professor professor) {
        this.codigo = codigo;
        this.ano = ano;
        this.semestre = semestre;
        this.disciplina = disciplina;
        this.professor = professor;
    }

    public void addInscricao(Inscricao inscricao) {
        inscricoes.add(inscricao);
    }

    public void removeInscricao(Inscricao inscricao) {
        inscricoes.remove(inscricao);
    }
}
