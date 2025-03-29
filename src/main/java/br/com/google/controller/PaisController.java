package br.com.google.controller;

import br.com.google.dto.PaisDto;
import br.com.google.json.Response;
import br.com.google.service.PaisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            log.error("Erro ao inserir o pais: " + e.getMessage());
            response.setMensagensRetorno(e.getMessage());
            return (ResponseEntity<Response>) ResponseEntity.status(500);
        }
        return ResponseEntity.ok(response);
    }
}