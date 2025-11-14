package br.edu.ibmec.service;

import java.util.Collection;

import br.edu.ibmec.dao.ProfessorRepository;
import br.edu.ibmec.dto.ProfessorDTO;
import br.edu.ibmec.entity.Professor;
import br.edu.ibmec.exception.DaoException;
import br.edu.ibmec.exception.ServiceException;
import br.edu.ibmec.exception.ServiceException.ServiceExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    public ProfessorDTO buscarProfessor(int codigo) throws DaoException {
        try {
            Professor professor = professorRepository.findById(codigo)
                    .orElseThrow(() -> new DaoException("Professor não encontrado"));

            ProfessorDTO professorDTO = new ProfessorDTO(professor.getCodigo(), professor.getNome());
            return professorDTO;
        } catch (DaoException e) {
            throw new DaoException("Erro ao buscar professor");
        }
    }

    public Collection<Professor> listarProfessores() {
        return professorRepository.findAll();
    }

    @Transactional
    public void cadastrarProfessor(ProfessorDTO professorDTO) throws ServiceException, DaoException {
        if ((professorDTO.getCodigo() < 1) || (professorDTO.getCodigo() > 99)) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_CODIGO_INVALIDO);
        }
        if ((professorDTO.getNome().length() < 1) || (professorDTO.getNome().length() > 50)) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_NOME_INVALIDO);
        }

        try {
            if (professorRepository.existsById(professorDTO.getCodigo())) {
                throw new DaoException("Professor já existe");
            }

            Professor professor = new Professor(professorDTO.getCodigo(), professorDTO.getNome());
            professorRepository.save(professor);
        } catch (DaoException e) {
            throw new DaoException("erro do dao no service throw");
        }
    }

    @Transactional
    public void alterarProfessor(ProfessorDTO professorDTO) throws ServiceException, DaoException {
        if ((professorDTO.getCodigo() < 1) || (professorDTO.getCodigo() > 99)) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_CODIGO_INVALIDO);
        }
        if ((professorDTO.getNome().length() < 1) || (professorDTO.getNome().length() > 50)) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_NOME_INVALIDO);
        }

        try {
            Professor professor = professorRepository.findById(professorDTO.getCodigo())
                    .orElseThrow(() -> new DaoException("Professor não encontrado"));

            professor.setNome(professorDTO.getNome());

            professorRepository.save(professor);
        } catch (DaoException e) {
            throw new DaoException("erro do dao no service throw");
        }
    }

    @Transactional
    public void removerProfessor(int codigo) throws DaoException {
        try {
            Professor professor = professorRepository.findById(codigo)
                    .orElseThrow(() -> new DaoException("Professor não encontrado"));

            if (!professor.getTurmas().isEmpty()) {
                throw new DaoException("Não é possível remover professor com turmas");
            }

            professorRepository.deleteById(codigo);
        } catch (DaoException e) {
            throw new DaoException("Erro ao remover professor");
        }
    }
}
