package br.edu.ibmec.service;

import java.util.Collection;

import br.edu.ibmec.dao.DisciplinaRepository;
import br.edu.ibmec.dao.ProfessorRepository;
import br.edu.ibmec.dao.TurmaRepository;
import br.edu.ibmec.dto.TurmaDTO;
import br.edu.ibmec.entity.Disciplina;
import br.edu.ibmec.entity.Professor;
import br.edu.ibmec.entity.Turma;
import br.edu.ibmec.exception.DaoException;
import br.edu.ibmec.exception.ServiceException;
import br.edu.ibmec.exception.ServiceException.ServiceExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TurmaService {

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    public TurmaDTO buscarTurma(int codigo, int ano, int semestre)
            throws DaoException {
        try {
            Turma turma = turmaRepository.findById(codigo)
                    .orElseThrow(() -> new DaoException("Turma não encontrada"));

            TurmaDTO turmaDTO = new TurmaDTO(
                    turma.getCodigo(),
                    turma.getAno(),
                    turma.getSemestre(),
                    turma.getDisciplina().getCodigo(),
                    turma.getProfessor().getCodigo());
            return turmaDTO;
        } catch (DaoException e) {
            throw new DaoException("Erro ao buscar turma");
        }
    }

    public Collection<Turma> listarTurmas() throws DaoException {
        return turmaRepository.findAll();
    }

    @Transactional
    public void cadastrarTurma(TurmaDTO turmaDTO) throws ServiceException,
            DaoException {
        if ((turmaDTO.getCodigo() < 1) || (turmaDTO.getCodigo() > 99)) {
            throw new ServiceException(
                    ServiceExceptionEnum.CURSO_CODIGO_INVALIDO);
        }
        if ((turmaDTO.getAno() < 1900) || (turmaDTO.getAno() > 2030)) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_NOME_INVALIDO);
        }

        try {
            if (turmaRepository.existsById(turmaDTO.getCodigo())) {
                throw new DaoException("Turma já existe");
            }

            Disciplina disciplina = disciplinaRepository.findById(turmaDTO.getDisciplina())
                    .orElseThrow(() -> new DaoException("Disciplina não encontrada"));

            Professor professor = professorRepository.findById(turmaDTO.getProfessor())
                    .orElseThrow(() -> new DaoException("Professor não encontrado"));

            Turma turma = new Turma(turmaDTO.getCodigo(), turmaDTO.getAno(),
                    turmaDTO.getSemestre(), disciplina, professor);

            turmaRepository.save(turma);
        } catch (DaoException e) {
            throw new DaoException("erro do dao no service throw");
        }
    }

    @Transactional
    public void alterarTurma(TurmaDTO turmaDTO) throws ServiceException,
            DaoException {
        if ((turmaDTO.getCodigo() < 1) || (turmaDTO.getCodigo() > 99)) {
            throw new ServiceException(
                    ServiceExceptionEnum.CURSO_CODIGO_INVALIDO);
        }
        if ((turmaDTO.getAno() < 1900) || (turmaDTO.getAno() > 2030)) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_NOME_INVALIDO);
        }

        try {

            Turma turma = turmaRepository.findById(turmaDTO.getCodigo())
                    .orElseThrow(() -> new DaoException("Turma não encontrada"));


            Disciplina disciplina = disciplinaRepository.findById(turmaDTO.getDisciplina())
                    .orElseThrow(() -> new DaoException("Disciplina não encontrada"));


            Professor professor = professorRepository.findById(turmaDTO.getProfessor())
                    .orElseThrow(() -> new DaoException("Professor não encontrado"));


            turma.setAno(turmaDTO.getAno());
            turma.setSemestre(turmaDTO.getSemestre());
            turma.setDisciplina(disciplina);
            turma.setProfessor(professor);

            turmaRepository.save(turma);
        } catch (DaoException e) {
            throw new DaoException("erro do dao no service throw");
        }
    }

    @Transactional
    public void removerTurma(int codigo, int ano, int semestre)
            throws DaoException {
        try {
            Turma turma = turmaRepository.findById(codigo)
                    .orElseThrow(() -> new DaoException("Turma não encontrada"));

            if (!turma.getInscricoes().isEmpty()) {
                throw new DaoException("Não é possível remover turma com inscrições");
            }

            turmaRepository.deleteById(codigo);
        } catch (DaoException e) {
            throw new DaoException("Erro ao remover turma");
        }
    }
}
