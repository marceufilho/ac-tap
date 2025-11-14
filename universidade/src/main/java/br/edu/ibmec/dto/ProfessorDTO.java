package br.edu.ibmec.dto;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@XmlRootElement(name="professor")
public class ProfessorDTO {
	private int codigo;
	private String nome;

    public ProfessorDTO(int codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "ProfessorDTO [codigo=" + codigo + ", nome=" + nome + "]";
	}

}
