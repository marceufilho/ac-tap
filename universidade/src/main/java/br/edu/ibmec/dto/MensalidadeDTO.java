package br.edu.ibmec.dto;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@XmlRootElement(name="mensalidade")
public class MensalidadeDTO {
    private int matricula;
    private String nomeAluno;
    private int numTurmas;
    private double valorPorTurma;
    private double valorBase;
    private String categoriaCobranca;
    private double valorFinal;
    private String descricao;

    public MensalidadeDTO(int matricula, String nomeAluno, int numTurmas,
                          double valorPorTurma, double valorBase,
                          String categoriaCobranca, double valorFinal, String descricao) {
        this.matricula = matricula;
        this.nomeAluno = nomeAluno;
        this.numTurmas = numTurmas;
        this.valorPorTurma = valorPorTurma;
        this.valorBase = valorBase;
        this.categoriaCobranca = categoriaCobranca;
        this.valorFinal = valorFinal;
        this.descricao = descricao;
    }
}
