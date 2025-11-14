package br.edu.ibmec.resource;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.ibmec.service.InscricaoService;
import br.edu.ibmec.dto.InscricaoDTO;
import br.edu.ibmec.exception.DaoException;
import br.edu.ibmec.exception.ServiceException;
import br.edu.ibmec.exception.ServiceException.ServiceExceptionEnum;

/**
 * Aplicação com serviços REST para gestão de cursos.
 *
 * @author  Thiago Silva de Souza
 * @version 1.0
 * @since   2012-02-29
 */
@RestController
@RequestMapping("/inscricao")
public class InscricaoResource {

    private InscricaoService inscricaoService;

    @Autowired
    public InscricaoResource(InscricaoService inscricaoService) {
        this.inscricaoService = inscricaoService;
    }

    @GetMapping(value = "/{matricula}/{codigo}/{ano}/{semestre}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<InscricaoDTO> buscarInscricao(@PathVariable("matricula") String matricula,
                                                         @PathVariable("codigo") String codigo,
                                                         @PathVariable("ano") String ano,
                                                         @PathVariable("semestre") String semestre) {
        try {
            InscricaoDTO inscricaoDTO = inscricaoService.buscarInscricao(
                    Integer.parseInt(matricula),
                    Integer.parseInt(codigo),
                    Integer.parseInt(ano),
                    Integer.parseInt(semestre));
            return ResponseEntity.ok(inscricaoDTO);
        } catch (DaoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> cadastrarInscricao(@RequestBody InscricaoDTO inscricaoDTO)
            throws ServiceException, DaoException {
        try {
            inscricaoService.cadastrarInscricao(inscricaoDTO);
            return ResponseEntity.created(
                    new URI("" + inscricaoDTO.getAluno() + "/" + inscricaoDTO.getCodigo() + "/"
                            + inscricaoDTO.getAno() + "/" + inscricaoDTO.getSemestre())).build();
        } catch (ServiceException e) {
            if (e.getTipo() == ServiceExceptionEnum.CURSO_CODIGO_INVALIDO) {
                return ResponseEntity.status(400)
                        .header("Motivo", "Código inválido")
                        .build();
            }
            if (e.getTipo() == ServiceExceptionEnum.CURSO_NOME_INVALIDO) {
                return ResponseEntity.status(400)
                        .header("Motivo", "Nome inválido")
                        .build();
            } else {
                return ResponseEntity.status(400)
                        .header("Motivo", e.getMessage())
                        .build();
            }
        } catch (DaoException e) {
            return ResponseEntity.status(400)
                    .header("Motivo", "Erro no banco de dados")
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException();
        }
    }

    @PutMapping
    public ResponseEntity<Void> alterarInscricao(@RequestBody InscricaoDTO inscricaoDTO) {
        try {
            inscricaoService.alterarInscricao(inscricaoDTO);
            return ResponseEntity.created(
                    new URI("" + inscricaoDTO.getAluno() + "/" + inscricaoDTO.getCodigo() + "/"
                            + inscricaoDTO.getAno() + "/" + inscricaoDTO.getSemestre())).build();
        } catch (ServiceException e) {
            if (e.getTipo() == ServiceExceptionEnum.CURSO_CODIGO_INVALIDO) {
                return ResponseEntity.status(400)
                        .header("Motivo", "Código inválido")
                        .build();
            }
            if (e.getTipo() == ServiceExceptionEnum.CURSO_NOME_INVALIDO) {
                return ResponseEntity.status(400)
                        .header("Motivo", "Nome inválido")
                        .build();
            } else {
                return ResponseEntity.status(400)
                        .header("Motivo", e.getMessage())
                        .build();
            }
        } catch (DaoException e) {
            return ResponseEntity.status(400)
                    .header("Motivo", "Erro no banco de dados")
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException();
        }
    }

    @DeleteMapping("/{matricula}/{codigo}/{ano}/{semestre}")
    public ResponseEntity<Void> removerInscricao(@PathVariable("matricula") String matricula,
                                                  @PathVariable("codigo") String codigo,
                                                  @PathVariable("ano") String ano,
                                                  @PathVariable("semestre") String semestre) {
        try {
            inscricaoService.removerInscricao(
                    Integer.parseInt(matricula),
                    Integer.parseInt(codigo),
                    Integer.parseInt(ano),
                    Integer.parseInt(semestre));
            return ResponseEntity.ok().build();
        } catch (DaoException e) {
            return ResponseEntity.status(404).build();
        }
    }
}
