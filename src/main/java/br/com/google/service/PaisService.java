package br.com.google.service;


import br.com.google.domain.Pais;
import br.com.google.dto.PaisDto;
import br.com.google.exception.BDException;
import br.com.google.repository.PaisRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
public class PaisService {

    @Autowired
    private PaisRepository   paisRepository;
    public PaisDto inserirPais(PaisDto paisDto) throws BDException{
        PaisDto paisPers = null;
        try {
            ModelMapper modelMapper = new ModelMapper();
            Pais pais = modelMapper.map(paisDto, Pais.class);

            paisPers = modelMapper.map( paisRepository.save(pais), PaisDto.class);

        }catch(Exception e){
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
}
