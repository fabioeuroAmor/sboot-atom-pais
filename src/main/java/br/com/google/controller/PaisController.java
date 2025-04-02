package br.com.google.controller;

import br.com.google.dto.PaisDto;
import br.com.google.json.Response;
import br.com.google.service.PaisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/pais")
public class PaisController {

    @Autowired
    private PaisService paisService;

    @PostMapping()
    public ResponseEntity<Response> inserirPais(@RequestParam(value = "nome", required = true) String nome, @RequestParam(value = "codigo", required = true) String codigo, @RequestParam(value = "continente", required = true) String continente) {
        Response response = new Response();
        PaisDto paisDto = null;
        try {
            paisDto = new PaisDto();
            paisDto.setNome(nome);
            paisDto.setCodigo(codigo);
            paisDto.setContinente(continente);

            //paisService.
            //response.setModeloRetorno(paisService.inserirPais(paisDto));
            response.setMensagensRetorno("Insercao do pais foi com sucesso!");


        } catch (Exception e) {
            //
            log.error("Erro ao inserir o pais: " + e.getMessage());
            response.setMensagensRetorno(e.getMessage());
            return (ResponseEntity<Response>) ResponseEntity.status(500);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/body")
    public ResponseEntity<Response> inserirPais(@RequestBody PaisDto paisDto) {
        Response response = new Response();
        PaisDto paisServDto = null;

        try {
            paisDto = new PaisDto();
            paisServDto.setNome(paisDto.getNome());
            paisServDto.setCodigo(paisDto.getCodigo());
            paisServDto.setContinente(paisDto.getContinente());

            response.setModeloRetorno(paisServDto);
            response.setModeloRetorno("Insercao do pais foi com sucesso!");

            //paisService.
            //response.setModeloRetorno(paisService.inserirPais(paisDto));

        } catch (Exception e) {
            log.error("Erro ao inserir o pais: " + e.getMessage());
            response.setMensagensRetorno(e.getMessage());
            return (ResponseEntity<Response>) ResponseEntity.status(500);
        }
        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<Response> consulta(@RequestParam(value = "idPais", required = false) Integer idPais, @RequestParam(value = "nome", required = false) String nome, @RequestParam(value = "codigo", required = false) String codigo, @RequestParam(value = "continente", required = false) String continente) {

        Response response = new Response();
        try {
            PaisDto paisServDto = new PaisDto();
            paisServDto.setIdPais(idPais);
            paisServDto.setNome(nome);
            paisServDto.setCodigo(codigo);
            paisServDto.setContinente(continente);

            response.setModeloRetorno(paisServDto);
            response.setModeloRetorno("Insercao do pais foi com sucesso!");

            //paisService.
            //response.setModeloRetorno(paisService.inserirPais(paisDto));

        } catch (Exception e) {
            log.error("Erro ao inserir o pais: " + e.getMessage());
            response.setMensagensRetorno(e.getMessage());
            return (ResponseEntity<Response>) ResponseEntity.status(500);
        }
        return ResponseEntity.ok(response);

    }

}
