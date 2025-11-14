package br.edu.ibmec.dto;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@XmlRootElement(name="disciplina")
public class DisciplinaDTO {
    private int codigo;
    private String nome;
    private int curso;

    /*
     * private List<TurmaDTO> turmas = new ArrayList<TurmaDTO>(); private
     * List<AlunoMonitor> monitores = new ArrayList<AlunoMonitor>();
     */

    public DisciplinaDTO(int codigo, String nome, int curso) {
        this.codigo = codigo;
        this.nome = nome;
        this.curso = curso;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + codigo;
        result = prime * result + curso;
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DisciplinaDTO other = (DisciplinaDTO) obj;
        if (codigo != other.codigo)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "DisciplinaDTO [codigo=" + codigo + ", curso=" + curso
                + ", nome=" + nome + "]";
    }
}
