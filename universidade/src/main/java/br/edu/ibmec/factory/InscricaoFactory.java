package br.edu.ibmec.factory;

import br.edu.ibmec.dao.AlunoRepository;
import br.edu.ibmec.dao.TurmaRepository;
import br.edu.ibmec.dto.InscricaoDTO;
import br.edu.ibmec.entity.Aluno;
import br.edu.ibmec.entity.Inscricao;
import br.edu.ibmec.entity.InscricaoId;
import br.edu.ibmec.entity.Turma;
import br.edu.ibmec.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InscricaoFactory {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    public Inscricao criarInscricao(InscricaoDTO inscricaoDTO) throws DaoException {
        Aluno aluno = buscarAluno(inscricaoDTO.getAluno());
        Turma turma = buscarTurma(inscricaoDTO.getCodigo());

        // Factory MEthod
        return criarInscricao(
                inscricaoDTO.getAvaliacao1(),
                inscricaoDTO.getAvaliacao2(),
                inscricaoDTO.getNumFaltas(),
                aluno,
                turma);
    }


    public Inscricao criarInscricao(float avaliacao1, float avaliacao2, int numFaltas,
                                     Aluno aluno, Turma turma) {
        return new Inscricao(avaliacao1, avaliacao2, numFaltas, aluno, turma);
    }


    public InscricaoId criarInscricaoId(int matricula, int codigo) throws DaoException {
        Aluno aluno = buscarAluno(matricula);
        Turma turma = buscarTurma(codigo);
        return new InscricaoId(aluno, turma);
    }

    private Aluno buscarAluno(int matricula) throws DaoException {
        return alunoRepository.findById(matricula)
                .orElseThrow(() -> new DaoException("Aluno não encontrado"));
    }

    private Turma buscarTurma(int codigo) throws DaoException {
        return turmaRepository.findById(codigo)
                .orElseThrow(() -> new DaoException("Turma não encontrada"));
    }
}
