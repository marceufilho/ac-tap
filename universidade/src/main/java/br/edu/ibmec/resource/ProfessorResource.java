package br.edu.ibmec.resource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.ibmec.service.ProfessorService;
import br.edu.ibmec.dto.ProfessorDTO;
import br.edu.ibmec.entity.Professor;
import br.edu.ibmec.exception.DaoException;
import br.edu.ibmec.exception.ServiceException;
import br.edu.ibmec.exception.ServiceException.ServiceExceptionEnum;

@RestController
@RequestMapping("/professor")
public class ProfessorResource {

	private ProfessorService professorService;

    @Autowired
    public ProfessorResource(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping(value = "/{codigo}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ProfessorDTO> buscarProfessor(@PathVariable("codigo") String codigo) {
        try {
            ProfessorDTO professorDTO = professorService.buscarProfessor(Integer.parseInt(codigo));
            return ResponseEntity.ok(professorDTO);
        } catch (DaoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> cadastrarProfessor(@RequestBody ProfessorDTO professorDTO)
            throws ServiceException, DaoException {
        try {
            professorService.cadastrarProfessor(professorDTO);
            return ResponseEntity.created(new URI("" + professorDTO.getCodigo())).build();
        } catch (ServiceException e) {
            if (e.getTipo() == ServiceExceptionEnum.CURSO_CODIGO_INVALIDO) {
                return ResponseEntity.status(400)
                        .header("Motivo", e.getTipo().toString())
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
    public ResponseEntity<Void> alterarProfessor(@RequestBody ProfessorDTO professorDTO) {
        try {
            professorService.alterarProfessor(professorDTO);
            return ResponseEntity.created(new URI("" + professorDTO.getCodigo())).build();
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
    public ResponseEntity<Void> removerProfessor(@PathVariable("codigo") String codigo) {
        try {
            professorService.removerProfessor(Integer.parseInt(codigo));
            return ResponseEntity.ok().build();
        } catch (DaoException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public String listarProfessores() {
        List<String> nomes = new ArrayList<String>();
        for (Iterator<Professor> it = professorService.listarProfessores().iterator(); it
                .hasNext();) {
            Professor professor = (Professor) it.next();
            nomes.add(professor.getNome());
        }
        return nomes.toString();
    }
}
