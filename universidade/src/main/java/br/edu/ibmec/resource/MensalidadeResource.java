package br.edu.ibmec.resource;

import br.edu.ibmec.dto.MensalidadeDTO;
import br.edu.ibmec.exception.DaoException;
import br.edu.ibmec.service.MensalidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(produces = {"application/json", "application/xml"})
public class MensalidadeResource {

    @Autowired
    private MensalidadeService mensalidadeService;

    @GetMapping("/alunos/{matricula}/mensalidade")
    public ResponseEntity<MensalidadeDTO> calcularMensalidade(
            @PathVariable("matricula") int matricula) {
        try {
            MensalidadeDTO mensalidade = mensalidadeService.calcularMensalidade(matricula);
            return ResponseEntity.ok(mensalidade);
        } catch (DaoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("motivo", e.getMessage())
                    .build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("motivo", "Erro ao calcular mensalidade: " + e.getMessage())
                    .build();
        }
    }
}
