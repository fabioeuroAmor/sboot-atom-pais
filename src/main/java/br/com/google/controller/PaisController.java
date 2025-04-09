package br.com.google.controller;

import br.com.google.dto.PaisDto;
import br.com.google.json.Response;
import br.com.google.service.PaisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

            response.setModeloRetorno(paisService.inserirPais(paisDto));
            response.setMensagensRetorno("Insercao do pais foi realizada com sucesso!");

        } catch (Exception e) {
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
            paisServDto = new PaisDto();
            paisServDto.setNome(paisDto.getNome());
            paisServDto.setCodigo(paisDto.getCodigo());
            paisServDto.setContinente(paisDto.getContinente());

            response.setModeloRetorno(paisService.inserirPais(paisDto));
            response.setMensagensRetorno("Insercao do pais foi realizada com sucesso!");

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
        PaisDto paisServDto = null;

        try {

            paisServDto = new PaisDto();
            paisServDto.setIdPais(idPais);
            paisServDto.setNome(nome);
            paisServDto.setCodigo(codigo);
            paisServDto.setContinente(continente);

            response.setModeloRetorno(paisService.consulta(paisServDto));
            response.setMensagensRetorno("Consulta realizada com suceeso");

        } catch (Exception e) {
            log.error("Erro ao inserir o pais: " + e.getMessage());
            response.setMensagensRetorno(e.getMessage());
            return (ResponseEntity<Response>) ResponseEntity.status(500);
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<Response> delete(
            @RequestParam(value = "idPais", required = false) Integer idPais,
            @RequestParam(value = "codigo", required = false) String codigo) {

        Response response = new Response();

        try {
            PaisDto paisServDto = new PaisDto();
            paisServDto.setIdPais(idPais);
            paisServDto.setCodigo(codigo);


            response.setModeloRetorno(null);
            response.setMensagensRetorno(paisService.delete(paisServDto));

        } catch (Exception e) {
            response.setMensagensRetorno("Erro ao deletar o país: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/atualizar/partes/{idPais}")
    public ResponseEntity<Response> atualizaPorPartes(@PathVariable("idPais") Integer idPais, @RequestBody  PaisDto paisDto){
        Response response = new Response();
       try{

           response.setModeloRetorno(paisService.atualizaPorPartes(idPais, paisDto));
           response.setMensagensRetorno("Parte do objeto foi atualizada com sucesso!");


       }catch (Exception e) {
           response.setMensagensRetorno("Erro ao deletar o país: " + e.getMessage());
           return ResponseEntity.status(500).body(response);
       }

        return  ResponseEntity.ok(response);
    }

    @PutMapping("/atualizar/completamente/{idPais}")
    public ResponseEntity<Response> AtualizaPorCompleto(@PathVariable("idPais") Integer idPais, @Valid @RequestBody  PaisDto paisDto){
        Response response = new Response();
        try{
            PaisDto paisServDto = new PaisDto();
            paisServDto.setIdPais(idPais);

        }catch (Exception e) {
            response.setMensagensRetorno("Erro ao deletar o país: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }

        return  ResponseEntity.ok(response);
    }

    @GetMapping("/todos")
    public ResponseEntity<Response> buscarTodosPaises(){
        Response response = new Response();
        try {

        }catch (Exception e) {
            response.setMensagensRetorno("Erro ao deletar o país: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
        return   ResponseEntity.ok(response);
    }
}





