package br.edu.ibmec.service;

import br.edu.ibmec.dao.AlunoRepository;
import br.edu.ibmec.dto.MensalidadeDTO;
import br.edu.ibmec.entity.Aluno;
import br.edu.ibmec.entity.Inscricao;
import br.edu.ibmec.exception.DaoException;
import br.edu.ibmec.strategy.CalculoBolsista;
import br.edu.ibmec.strategy.CalculoComDesconto;
import br.edu.ibmec.strategy.CalculoMensalidadeStrategy;
import br.edu.ibmec.strategy.CalculoPadrao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MensalidadeService {

    @Autowired
    private AlunoRepository alunoRepository;

    public MensalidadeDTO calcularMensalidade(int matricula) throws DaoException {
        // Find the student
        Aluno aluno = alunoRepository.findById(matricula)
                .orElseThrow(() -> new DaoException("Aluno não encontrado"));


        int numTurmas = aluno.getInscricoes().size();


        double valorPorTurma = 300.0;
        if (numTurmas > 0) {
            Inscricao primeiraInscricao = aluno.getInscricoes().get(0);
            if (primeiraInscricao != null && primeiraInscricao.getTurma() != null) {
                valorPorTurma = primeiraInscricao.getTurma().getValorTurma();
            }
        }


        double valorBase = numTurmas * valorPorTurma;

        // Strategy Aluno
        String categoria = aluno.getCategoriaCobranca();
        CalculoMensalidadeStrategy estrategia = getEstrategia(categoria);

        // Applica a strategy
        double valorFinal = estrategia.calcular(valorBase);

        // novo dto
        return new MensalidadeDTO(
                aluno.getMatricula(),
                aluno.getNome(),
                numTurmas,
                valorPorTurma,
                valorBase,
                categoria,
                valorFinal,
                estrategia.getDescricao()
        );
    }


    private CalculoMensalidadeStrategy getEstrategia(String categoria) {
        return switch (categoria.toLowerCase()) {
            case "desconto" -> new CalculoComDesconto();
            case "bolsista" -> new CalculoBolsista();
            default -> new CalculoPadrao();
        };
    }
}
