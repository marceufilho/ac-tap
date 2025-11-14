package br.edu.ibmec.resource;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.ibmec.service.DisciplinaService;
import br.edu.ibmec.dto.DisciplinaDTO;
import br.edu.ibmec.exception.DaoException;
import br.edu.ibmec.exception.ServiceException;
import br.edu.ibmec.exception.ServiceException.ServiceExceptionEnum;

@RestController
@RequestMapping("/disciplina")
public class DisciplinaResource {

    private DisciplinaService disciplinaService;

    @Autowired
    public DisciplinaResource(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    @GetMapping(value = "/{codigo}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<DisciplinaDTO> buscarDisciplina(@PathVariable("codigo") String codigo) {
        try {
            DisciplinaDTO disciplinaDTO = disciplinaService.buscarDisciplina(Integer.parseInt(codigo));
            return ResponseEntity.ok(disciplinaDTO);
        } catch (DaoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> cadastrarDisciplina(@RequestBody DisciplinaDTO disciplinaDTO)
            throws ServiceException, DaoException {
        try {
            disciplinaService.cadastrarDisciplina(disciplinaDTO);
            return ResponseEntity.created(new URI("" + disciplinaDTO.getCodigo())).build();
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
    public ResponseEntity<Void> alterarDisciplina(@RequestBody DisciplinaDTO disciplinaDTO) {
        try {
            disciplinaService.alterarDisciplina(disciplinaDTO);
            return ResponseEntity.created(new URI("" + disciplinaDTO.getCodigo())).build();
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

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> removerDisciplina(@PathVariable("codigo") String codigo) {
        try {
            disciplinaService.removerDisciplina(Integer.parseInt(codigo));
            return ResponseEntity.ok().build();
        } catch (DaoException e) {
            return ResponseEntity.status(404).build();
        }
    }
}
