package br.edu.ibmec.dto;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@XmlRootElement(name="turma")
public class TurmaDTO {
    private int codigo;
    private int ano;
    private int semestre;
    private int disciplina;
    private int professor;

    //private List<InscricaoDTO> inscricoes = new ArrayList<InscricaoDTO>();

    public TurmaDTO(int codigo, int ano, int semestre, int disciplina, int professor) {
        this.codigo = codigo;
        this.ano = ano;
        this.semestre = semestre;
        this.disciplina = disciplina;
        this.professor = professor;
    }

    @Override
    public String toString() {
        return "TurmaDTO [ano=" + ano + ", codigo=" + codigo + ", disciplina="
                + disciplina + ", professor=" + professor + ", semestre=" + semestre + "]";
    }
}
