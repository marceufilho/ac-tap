package br.edu.ibmec.dto;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@XmlRootElement(name="inscricao")
public class InscricaoDTO {

    private float avaliacao1;
    private float avaliacao2;
    private int numFaltas;

    private int aluno;
    private int codigo;
    private int ano;
    private int semestre;


    private float media;
    private String situacao;

    public InscricaoDTO(float avaliacao1, float avaliacao2, int numFaltas, int aluno, int codigoTurma,
                        int ano, int semestre) {
        this.avaliacao1 = avaliacao1;
        this.avaliacao2 = avaliacao2;
        this.numFaltas = numFaltas;
        this.aluno = aluno;
        this.codigo = codigoTurma;
        this.ano = ano;
        this.semestre = semestre;
    }
}
