package br.edu.ibmec.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InscricaoId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "aluno_matricula")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "turma_codigo")
    private Turma turma;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InscricaoId that = (InscricaoId) o;
        return Objects.equals(aluno, that.aluno) && Objects.equals(turma, that.turma);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aluno, turma);
    }
}
