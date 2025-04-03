package br.com.google.service;


import br.com.google.domain.Pais;
import br.com.google.dto.PaisDto;
import br.com.google.exception.BDException;
import br.com.google.json.Response;
import br.com.google.repository.PaisRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
}
