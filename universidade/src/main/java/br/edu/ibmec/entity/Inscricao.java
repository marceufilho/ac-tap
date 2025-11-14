package br.edu.ibmec.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "inscricoes")
public class Inscricao {

    @EmbeddedId
    @Setter
    private InscricaoId id;

    @Column(nullable = false)
    private float avaliacao1;

    @Column(nullable = false)
    private float avaliacao2;

    private float media;

    @Setter
    private int numFaltas;

    private String situacao;

    public Inscricao(float avaliacao1, float avaliacao2, int numFaltas,
                     Aluno aluno, Turma turma) {
        this.id = new InscricaoId(aluno, turma);
        this.avaliacao1 = avaliacao1;
        this.avaliacao2 = avaliacao2;
        this.numFaltas = numFaltas;
        calcularMediaESituacao();
    }


    public void setAvaliacao1(float avaliacao1) {
        this.avaliacao1 = avaliacao1;
        calcularMediaESituacao();
    }

    public void setAvaliacao2(float avaliacao2) {
        this.avaliacao2 = avaliacao2;
        calcularMediaESituacao();
    }


    @PrePersist
    @PreUpdate
    private void calcularMediaESituacao() {
        this.media = (this.avaliacao1 + this.avaliacao2) / 2;

        if (this.avaliacao1 == 0.0f || this.avaliacao2 == 0.0f) {
            this.situacao = "pendente";
        } else if (this.media >= 7.0f) {
            this.situacao = "aprovado";
        } else {
            this.situacao = "reprovado";
        }
    }


    public Aluno getAluno() {
        return id != null ? id.getAluno() : null;
    }

    public void setAluno(Aluno aluno) {
        if (id == null) {
            id = new InscricaoId();
        }
        id.setAluno(aluno);
    }

    public Turma getTurma() {
        return id != null ? id.getTurma() : null;
    }

    public void setTurma(Turma turma) {
        if (id == null) {
            id = new InscricaoId();
        }
        id.setTurma(turma);
    }
}
