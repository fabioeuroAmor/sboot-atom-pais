package br.com.google.service;


import br.com.google.domain.Pais;
import br.com.google.dto.ErroDto;
import br.com.google.dto.PaisDto;
import br.com.google.exception.BDException;
import br.com.google.produce.PaisKafkaProduce;
import br.com.google.repository.PaisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaisService {

//    @Autowired
//    private PaisRepository   paisRepository;

    private final PaisRepository   paisRepository;
    private final PaisKafkaProduce paisKafkaProduce;

    public PaisDto inserirPais(PaisDto paisDto) throws BDException{
        PaisDto paisPers = null;
        try {
            ModelMapper modelMapper = new ModelMapper();

            if(paisDto.getIdPais().intValue() == 0){
                paisDto.setIdPais(null);
            }




            Pais pais = modelMapper.map(paisDto, Pais.class);

            paisPers = modelMapper.map( paisRepository.save(pais), PaisDto.class);


        }catch(Exception e){
            //Produz o evento de erro em meu-topico-produce-1
            ErroDto erroDto = new ErroDto();
            erroDto.setCodigo(paisDto.getCodigo());
            erroDto.setMenssagem(e.getMessage());
            paisKafkaProduce.send(erroDto.toString());

            log.error("Erro na camada de servico ao realizar a insercao no banco de dados: " + e.getMessage());
            throw new BDException(e.getMessage());
        }

        return  paisPers;
    }


    public List<PaisDto> consulta(PaisDto paisDto)throws BDException{
        ArrayList<Pais> listPais =  new ArrayList<>();
        ArrayList<PaisDto> arrayPaisDto = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        try {

            if (paisDto.getIdPais() != null) {
                listPais.add(paisRepository.findById(paisDto.getIdPais()).orElse(null));
            }

            if (paisDto.getNome() != null && !paisDto.getNome().isEmpty()) {
                listPais.addAll(paisRepository.findByNomeContaining(paisDto.getNome()));
            }

            if (paisDto.getContinente() != null && !paisDto.getContinente().isEmpty()) {
                listPais.addAll(paisRepository.findByContinente(paisDto.getContinente()));
            }

            // Remover duplicatas usando HashSet
            HashSet<Pais> set = new HashSet<>(listPais);
            listPais.clear();
            listPais.addAll(set);


            // Defina o tipo de destino usando TypeToken
            Type destinationListType = new TypeToken<List<PaisDto>>() {}.getType();

            arrayPaisDto  = modelMapper.map(listPais, destinationListType);


        }catch(Exception e){
            log.error("Erro na camada de servico ao realizar a consulta no banco de dados: " + e.getMessage());
            throw new BDException(e.getMessage());
        }

        return arrayPaisDto;

    }

    public String delete(PaisDto paisDto) throws BDException{
        String retorno = "";

        try {
         if(paisDto.getIdPais()!=null){
                  paisRepository.deleteById(paisDto.getIdPais());
         }else if(paisDto.getCodigo() !=null && !paisDto.getCodigo().isEmpty()){
              Pais pais = paisRepository.findByCodigo(paisDto.getCodigo());
             if(pais!=null){
                 paisRepository.delete(pais);
                 retorno = "Pais deletado com sucesso";
             }
         }

        }catch(Exception e){
            log.error("Erro na camada de servico ao realizar o delete no banco de dados: " + e.getMessage());
            throw new BDException(e.getMessage());
        }

        return  retorno;
    }

    public PaisDto atualizaPorPartes(Integer idPais, PaisDto paisDto) throws BDException{
        Pais pais = new Pais();
        PaisDto paisNewDto = null;

     try{
         ModelMapper modelMapper = new ModelMapper();
         paisNewDto =  new PaisDto();
         paisNewDto.setIdPais(idPais);

         Optional<Pais> paisDomain = paisRepository.findById(idPais);

         if(!paisDomain.isEmpty()){
             Pais paisBanco = paisDomain.get();
             if(paisDto.getCodigo() !=null && !paisDto.getCodigo().isEmpty()){
                 paisNewDto.setCodigo(paisDto.getCodigo());
                 paisNewDto.setNome(paisBanco.getNome());
                 paisNewDto.setContinente(paisBanco.getContinente());
             }

             if(paisDto.getNome() !=null && !paisDto.getNome().isEmpty()){
                 paisNewDto.setNome(paisDto.getNome());
                 paisNewDto.setContinente(paisBanco.getContinente());
                 paisNewDto.setCodigo(paisBanco.getCodigo());
             }

             if(paisDto.getContinente() !=null && !paisDto.getContinente().isEmpty()){
                 paisNewDto.setContinente(paisDto.getContinente());
                 paisNewDto.setNome(paisBanco.getNome());
                 paisNewDto.setCodigo(paisBanco.getCodigo());
             }
         }

         pais = modelMapper.map(paisNewDto, Pais.class);

         pais = paisRepository.saveAndFlush(pais);

         paisNewDto = modelMapper.map(pais, PaisDto.class);

     }catch(Exception e){
         log.error("Erro na camada de servico ao realizar a atualização por parte do objeto: " + e.getMessage());
         throw new BDException(e.getMessage());
     }

        return paisNewDto;
    }

    public PaisDto atualizaPorCompleto(Integer idPais, PaisDto paisDto) throws BDException{
        Pais pais = new Pais();
        PaisDto paisNewDto = null;
        ModelMapper modelMapper = new ModelMapper();
        try{
            Optional<Pais> paisDomain = paisRepository.findById(idPais);
            if(!paisDomain.isEmpty()){

                Pais paisBanco = paisDomain.get();
                paisBanco.setCodigo(paisDto.getCodigo());
                paisBanco.setContinente(paisDto.getContinente());
                paisBanco.setNome(paisDto.getNome());

                pais = paisRepository.saveAndFlush(paisBanco);
                paisNewDto =  new PaisDto();
                paisNewDto = modelMapper.map(pais, PaisDto.class);
            }
        }catch(Exception e){
            log.error("Erro na camada de servico ao realizar a atualização por completo do objeto: " + e.getMessage());
            throw new BDException(e.getMessage());
        }

        return paisNewDto;
    }

    public List<PaisDto> buscarTodosPaises() throws BDException{
        ArrayList<Pais> arrayPais = new ArrayList<>();
        ArrayList<PaisDto> arrayPaisDto = new ArrayList<>();
        try{

            arrayPais = (ArrayList<Pais>) paisRepository.findAll();

            ModelMapper modelMapper = new ModelMapper();
            // Defina o tipo de destino usando TypeToken
            Type destinationListType = new TypeToken<List<PaisDto>>() {}.getType();

            arrayPaisDto  = modelMapper.map(arrayPais, destinationListType);


        }catch(Exception e){
            log.error("Erro na camada de servico ao buscar todos os paises da base de dados: " + e.getMessage());
            throw new BDException(e.getMessage());
        }

        return  arrayPaisDto;
    }

    public PaisDto buscarPaisNome(String nome){
        PaisDto paisRetornoDto = new PaisDto();
        try {
            Pais pais = paisRepository.buscarPaisNomeQueryNativa(nome);
            ModelMapper modelMapper = new ModelMapper();
            paisRetornoDto = modelMapper.map(pais, PaisDto.class);
        }catch(Exception e){
            log.error("Erro na camada de servico ao buscar todos os paises da base de dados: " + e.getMessage());
            throw new BDException(e.getMessage());
        }

        return paisRetornoDto;

    }
}
