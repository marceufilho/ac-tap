package br.edu.ibmec.service;

import java.util.Collection;

import br.edu.ibmec.dao.CursoRepository;
import br.edu.ibmec.dao.DisciplinaRepository;
import br.edu.ibmec.dto.DisciplinaDTO;
import br.edu.ibmec.entity.Curso;
import br.edu.ibmec.entity.Disciplina;
import br.edu.ibmec.exception.DaoException;
import br.edu.ibmec.exception.ServiceException;
import br.edu.ibmec.exception.ServiceException.ServiceExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private CursoRepository cursoRepository;

    public DisciplinaDTO buscarDisciplina(int codigo) throws DaoException {
        try {
            Disciplina disciplina = disciplinaRepository.findById(codigo)
                    .orElseThrow(() -> new DaoException("Disciplina não encontrada"));

            DisciplinaDTO disciplinaDTO = new DisciplinaDTO(
                    disciplina.getCodigo(),
                    disciplina.getNome(),
                    disciplina.getCurso().getCodigo());
            return disciplinaDTO;
        } catch (DaoException e) {
            throw new DaoException("Erro ao buscar disciplina");
        }
    }

    public Collection<Disciplina> listarDisciplinas() {
        return disciplinaRepository.findAll();
    }

    @Transactional
    public void cadastrarDisciplina(DisciplinaDTO disciplinaDTO)
            throws ServiceException, DaoException {
        if ((disciplinaDTO.getCodigo() < 1) || (disciplinaDTO.getCodigo() > 99)) {
            throw new ServiceException(
                    ServiceExceptionEnum.CURSO_CODIGO_INVALIDO);
        }
        if ((disciplinaDTO.getNome().length() < 1)
                || (disciplinaDTO.getNome().length() > 50)) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_NOME_INVALIDO);
        }

        try {
            if (disciplinaRepository.existsById(disciplinaDTO.getCodigo())) {
                throw new DaoException("Disciplina já existe");
            }

            Curso curso = cursoRepository.findById(disciplinaDTO.getCurso())
                    .orElseThrow(() -> new DaoException("Curso não encontrado"));

            Disciplina disciplina = new Disciplina(disciplinaDTO.getCodigo(),
                    disciplinaDTO.getNome(), curso);

            disciplinaRepository.save(disciplina);
        } catch (DaoException e) {
            throw new DaoException("erro do dao no service throw");
        }
    }

    @Transactional
    public void alterarDisciplina(DisciplinaDTO disciplinaDTO)
            throws ServiceException, DaoException {
        if ((disciplinaDTO.getCodigo() < 1) || (disciplinaDTO.getCodigo() > 99)) {
            throw new ServiceException(
                    ServiceExceptionEnum.CURSO_CODIGO_INVALIDO);
        }
        if ((disciplinaDTO.getNome().length() < 1)
                || (disciplinaDTO.getNome().length() > 50)) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_NOME_INVALIDO);
        }

        try {
            Disciplina disciplina = disciplinaRepository.findById(disciplinaDTO.getCodigo())
                    .orElseThrow(() -> new DaoException("Disciplina não encontrada"));


            Curso curso = cursoRepository.findById(disciplinaDTO.getCurso())
                    .orElseThrow(() -> new DaoException("Curso não encontrado"));


            disciplina.setNome(disciplinaDTO.getNome());
            disciplina.setCurso(curso);

            // Salva JPA
            disciplinaRepository.save(disciplina);
        } catch (DaoException e) {
            throw new DaoException("erro do dao no service throw");
        }
    }

    @Transactional
    public void removerDisciplina(int codigo) throws DaoException {
        try {
            Disciplina disciplina = disciplinaRepository.findById(codigo)
                    .orElseThrow(() -> new DaoException("Disciplina não encontrada"));

            if (!disciplina.getTurmas().isEmpty()) {
                throw new DaoException("Não é possível remover disciplina com turmas");
            }

            disciplinaRepository.deleteById(codigo);
        } catch (DaoException e) {
            throw new DaoException("Erro ao remover disciplina");
        }
    }
}
