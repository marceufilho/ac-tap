package br.edu.ibmec.service;

import java.util.Collection;

import br.edu.ibmec.dao.InscricaoRepository;
import br.edu.ibmec.dto.InscricaoDTO;
import br.edu.ibmec.entity.Inscricao;
import br.edu.ibmec.entity.InscricaoId;
import br.edu.ibmec.exception.DaoException;
import br.edu.ibmec.exception.ServiceException;
import br.edu.ibmec.exception.ServiceException.ServiceExceptionEnum;
import br.edu.ibmec.factory.InscricaoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InscricaoService {

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Autowired
    private InscricaoFactory inscricaoFactory;

    public InscricaoDTO buscarInscricao(int matricula, int codigo, int ano,
                                        int semestre) throws DaoException {
        try {
            Inscricao inscricao = inscricaoRepository.findByAlunoMatriculaAndTurmaCodigo(matricula, codigo)
                    .orElseThrow(() -> new DaoException("Inscrição não encontrada"));

            InscricaoDTO inscricaoDTO = new InscricaoDTO(
                    inscricao.getAvaliacao1(),
                    inscricao.getAvaliacao2(),
                    inscricao.getNumFaltas(),
                    inscricao.getAluno().getMatricula(),
                    inscricao.getTurma().getCodigo(),
                    inscricao.getTurma().getAno(),
                    inscricao.getTurma().getSemestre());
            // Set the auto-calculated fields from entity
            inscricaoDTO.setMedia(inscricao.getMedia());
            inscricaoDTO.setSituacao(inscricao.getSituacao());
            return inscricaoDTO;
        } catch (DaoException e) {
            throw new DaoException("Erro ao buscar inscrição");
        }
    }

    public Collection<Inscricao> listarInscricoes() throws DaoException {
        return inscricaoRepository.findAll();
    }

    @Transactional
    public void cadastrarInscricao(InscricaoDTO inscricaoDTO)
            throws ServiceException, DaoException {
        if ((inscricaoDTO.getCodigo() < 1) || (inscricaoDTO.getCodigo() > 999)) {
            throw new ServiceException(
                    ServiceExceptionEnum.CURSO_CODIGO_INVALIDO);
        }
        if ((inscricaoDTO.getAno() < 1900) || (inscricaoDTO.getAno() > 2050)) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_NOME_INVALIDO);
        }

        // usa o Factory method
        Inscricao inscricao = inscricaoFactory.criarInscricao(inscricaoDTO);

        try {
            // factory para criar inscrticaoID
            InscricaoId id = inscricaoFactory.criarInscricaoId(
                    inscricaoDTO.getAluno(),
                    inscricaoDTO.getCodigo());

            if (inscricaoRepository.existsById(id)) {
                throw new DaoException("Inscrição já existe");
            }
            inscricaoRepository.save(inscricao);
        } catch (DaoException e) {
            throw new DaoException("erro do dao no service throw");
        }
    }

    @Transactional
    public void alterarInscricao(InscricaoDTO inscricaoDTO)
            throws ServiceException, DaoException {
        if ((inscricaoDTO.getCodigo() < 1) || (inscricaoDTO.getCodigo() > 999)) {
            throw new ServiceException(
                    ServiceExceptionEnum.CURSO_CODIGO_INVALIDO);
        }
        if ((inscricaoDTO.getAno() < 1900) || (inscricaoDTO.getAno() > 2050)) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_NOME_INVALIDO);
        }

        Inscricao inscricao = inscricaoFactory.criarInscricao(inscricaoDTO);

        try {
            InscricaoId id = inscricaoFactory.criarInscricaoId(
                    inscricaoDTO.getAluno(),
                    inscricaoDTO.getCodigo());

            if (!inscricaoRepository.existsById(id)) {
                throw new DaoException("Inscrição não encontrada");
            }
            inscricaoRepository.save(inscricao);
        } catch (DaoException e) {
            throw new DaoException("erro do dao no service throw");
        }
    }

    @Transactional
    public void removerInscricao(int matricula, int codigo, int ano,
                                 int semestre) throws DaoException {
        try {
            Inscricao inscricao = inscricaoRepository.findByAlunoMatriculaAndTurmaCodigo(matricula, codigo)
                    .orElseThrow(() -> new DaoException("Inscrição não encontrada"));

            inscricaoRepository.delete(inscricao);
        } catch (DaoException e) {
            throw new DaoException("Erro ao remover inscrição");
        }
    }
}
