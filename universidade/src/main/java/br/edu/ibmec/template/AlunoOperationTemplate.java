package br.edu.ibmec.template;

import br.edu.ibmec.dao.AlunoRepository;
import br.edu.ibmec.dao.CursoRepository;
import br.edu.ibmec.dto.AlunoDTO;
import br.edu.ibmec.entity.Aluno;
import br.edu.ibmec.entity.Curso;
import br.edu.ibmec.entity.Data;
import br.edu.ibmec.entity.EstadoCivil;
import br.edu.ibmec.exception.DaoException;
import br.edu.ibmec.exception.ServiceException;
import br.edu.ibmec.exception.ServiceException.ServiceExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public abstract class AlunoOperationTemplate {

    @Autowired
    protected AlunoRepository alunoRepository;

    @Autowired
    protected CursoRepository cursoRepository;

    public final void execute(AlunoDTO alunoDTO) throws ServiceException, DaoException {
        try {
            validateMatricula(alunoDTO);
            validateNome(alunoDTO);

            checkPreconditions(alunoDTO);

            Curso curso = fetchCurso(alunoDTO);

            Aluno aluno = buildAluno(alunoDTO, curso);

            saveAluno(aluno);

            afterSave(aluno, curso);

        } catch (DaoException e) {
            throw new DaoException("erro do dao no service throw");
        }
    }

    protected final void validateMatricula(AlunoDTO alunoDTO) throws ServiceException {
        if ((alunoDTO.getMatricula() < 1) || (alunoDTO.getMatricula() > 99)) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_CODIGO_INVALIDO);
        }
    }

    protected final void validateNome(AlunoDTO alunoDTO) throws ServiceException {
        if ((alunoDTO.getNome().length() < 1) || (alunoDTO.getNome().length() > 50)) {
            throw new ServiceException(ServiceExceptionEnum.ALUNO_NOME_INVALIDO);
        }
    }

    protected final Curso fetchCurso(AlunoDTO alunoDTO) throws DaoException {
        return cursoRepository.findById(alunoDTO.getCurso())
                .orElseThrow(() -> new DaoException("Curso não encontrado"));
    }

    protected final Aluno buildAluno(AlunoDTO alunoDTO, Curso curso) {
        // Handle date conversion - support both String and direct input
        String dateString = alunoDTO.getDtNascimento();
        Data dataNascimento = getData(dateString);

        return new Aluno(
                alunoDTO.getMatricula(),
                alunoDTO.getNome(),
                dataNascimento,
                alunoDTO.isMatriculaAtiva(),
                EstadoCivil.solteiro,
                curso,
                alunoDTO.getTelefones());
    }


    protected final void saveAluno(Aluno aluno) {
        alunoRepository.save(aluno);
    }


    protected void checkPreconditions(AlunoDTO alunoDTO) throws DaoException {
    }

    protected void afterSave(Aluno aluno, Curso curso) {
    }

    protected final Data getData(String dataString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.parse(dataString, formatter);

            Data dataRetorno = new Data();
            dataRetorno.setAno(localDate.getYear());
            dataRetorno.setMes(localDate.getMonthValue());
            dataRetorno.setDia(localDate.getDayOfMonth());

            return dataRetorno;
        } catch (Exception e) {
            System.out.println("Erro na conversão da data: " + e.getMessage());
            return null;
        }
    }
}
