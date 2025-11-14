package br.edu.ibmec.template;

import br.edu.ibmec.dto.AlunoDTO;
import br.edu.ibmec.exception.DaoException;
import org.springframework.stereotype.Component;

@Component
public class AlterarAlunoOperation extends AlunoOperationTemplate {

    @Override
    protected void checkPreconditions(AlunoDTO alunoDTO) throws DaoException {
        if (!alunoRepository.existsById(alunoDTO.getMatricula())) {
            throw new DaoException("Aluno não encontrado");
        }
    }
}
