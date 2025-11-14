package br.edu.ibmec.service;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
import br.edu.ibmec.template.AlterarAlunoOperation;
import br.edu.ibmec.template.CadastrarAlunoOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private CadastrarAlunoOperation cadastrarOperation;

    @Autowired
    private AlterarAlunoOperation alterarOperation;

    public static final Data getData(String dataString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            LocalDate localDate = LocalDate.parse(dataString, formatter);

            Data dataRetorno = new Data();
            dataRetorno.setAno(localDate.getYear());           // Retorna o ano completo (ex: 2001)
            dataRetorno.setMes(localDate.getMonthValue());     // Retorna o mês correto (ex: 10 para Outubro)
            dataRetorno.setDia(localDate.getDayOfMonth());     // Retorna o dia do mês (ex: 1)

            return dataRetorno;

        } catch (Exception e) {
            System.out.println("Erro na conversão da data: " + e.getMessage());
            return null;
        }
    }

    public AlunoDTO buscarAluno(int matricula) throws DaoException {
        try {
            Aluno aluno = alunoRepository.findById(matricula)
                    .orElseThrow(() -> new DaoException("Aluno não encontrado"));

            AlunoDTO alunoDTO = new AlunoDTO(
                    aluno.getMatricula(),
                    aluno.getNome(),
                    aluno.getDataNascimento().toString(),
                    aluno.isMatriculaAtiva(),
                    null,
                    aluno.getCurso().getCodigo(),
                    aluno.getTelefones());
            return alunoDTO;
        } catch (Exception e) {
            throw new DaoException("Erro ao buscar aluno");
        }
    }

    public Collection<Aluno> listarAlunos() {
        return alunoRepository.findAll();
    }


    @Transactional
    public void cadastrarAluno(AlunoDTO alunoDTO) throws ServiceException,
            DaoException {
        // Use Template Method pattern - delegates to concrete implementation
        cadastrarOperation.execute(alunoDTO);
    }

    @Transactional
    public void alterarAluno(AlunoDTO alunoDTO) throws ServiceException,
            DaoException {
        // Use Template Method pattern - delegates to concrete implementation
        alterarOperation.execute(alunoDTO);
    }

    @Transactional
    public void removerAluno(int matricula) throws DaoException {
        try {
            if (!alunoRepository.existsById(matricula)) {
                throw new DaoException("Aluno não encontrado");
            }
            alunoRepository.deleteById(matricula);
        } catch (Exception e) {
            throw new DaoException("Erro ao remover aluno");
        }
    }
}
