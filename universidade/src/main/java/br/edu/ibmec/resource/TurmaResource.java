package br.edu.ibmec.resource;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.ibmec.service.TurmaService;
import br.edu.ibmec.dto.TurmaDTO;
import br.edu.ibmec.exception.DaoException;
import br.edu.ibmec.exception.ServiceException;
import br.edu.ibmec.exception.ServiceException.ServiceExceptionEnum;

@RestController
@RequestMapping("/turma")
public class TurmaResource {

    private TurmaService turmaService;

    @Autowired
    public TurmaResource(TurmaService turmaService) {
        this.turmaService = turmaService;
    }

    @GetMapping(value = "/{codigo}/{ano}/{semestre}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TurmaDTO> buscarTurma(@PathVariable("codigo") String codigo,
                                                 @PathVariable("ano") String ano,
                                                 @PathVariable("semestre") String semestre) {
        try {
            TurmaDTO turmaDTO = turmaService.buscarTurma(
                    Integer.parseInt(codigo),
                    Integer.parseInt(ano),
                    Integer.parseInt(semestre));
            return ResponseEntity.ok(turmaDTO);
        } catch (DaoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> cadastrarTurma(@RequestBody TurmaDTO turmaDTO)
            throws ServiceException, DaoException {
        try {
            turmaService.cadastrarTurma(turmaDTO);
            return ResponseEntity.created(
                    new URI("" + turmaDTO.getCodigo() + "/" + turmaDTO.getAno() + "/"
                            + turmaDTO.getSemestre())).build();
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
    public ResponseEntity<Void> alterarTurma(@RequestBody TurmaDTO turmaDTO) {
        try {
            turmaService.alterarTurma(turmaDTO);
            return ResponseEntity.created(
                    new URI("" + turmaDTO.getCodigo() + "/" + turmaDTO.getAno() + "/"
                            + turmaDTO.getSemestre())).build();
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

    @DeleteMapping("/{codigo}/{ano}/{semestre}")
    public ResponseEntity<Void> removerTurma(@PathVariable("codigo") String codigo,
                                              @PathVariable("ano") String ano,
                                              @PathVariable("semestre") String semestre) {
        try {
            turmaService.removerTurma(
                    Integer.parseInt(codigo),
                    Integer.parseInt(ano),
                    Integer.parseInt(semestre));
            return ResponseEntity.ok().build();
        } catch (DaoException e) {
            return ResponseEntity.status(404).build();
        }
    }
}
