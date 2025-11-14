package br.edu.ibmec.template;

import br.edu.ibmec.entity.Aluno;
import br.edu.ibmec.entity.Curso;
import org.springframework.stereotype.Component;


@Component
public class CadastrarAlunoOperation extends AlunoOperationTemplate {

    @Override
    protected void afterSave(Aluno aluno, Curso curso) {
        curso.getAlunos().add(aluno);
        cursoRepository.save(curso);
    }
}
