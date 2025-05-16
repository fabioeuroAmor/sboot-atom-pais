package br.com.google.service;

import br.com.google.domain.Pais;
import br.com.google.dto.ErroDto;
import br.com.google.dto.PaisDto;
import br.com.google.exception.BDException;
import br.com.google.produce.PaisKafkaProduce;
import br.com.google.repository.PaisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaisServiceTest {

    @Mock
    private PaisRepository paisRepository;

    @Mock
    private PaisKafkaProduce paisKafkaProduce;

    @InjectMocks
    private PaisService paisService;

    @Captor
    ArgumentCaptor<Pais> paisCaptor;

    @BeforeEach
    void setUp() {
        // Configurações necessárias antes de cada teste
    }

    @Test
    void testInserirPais() throws BDException {
        ModelMapper modelMapper = new ModelMapper();
        PaisDto paisDto = new PaisDto(); // Assumindo que o PaisDto tem getters e setters adequados
        paisDto.setIdPais(0);
        paisDto.setCodigo("BR");
        paisDto.setNome("Brasil");
        paisDto.setContinente("América do Sul");

        Pais pais = modelMapper.map(paisDto, Pais.class);

        when(paisRepository.save(any(Pais.class))).thenReturn(pais);

        PaisDto retorno = paisService.inserirPais(paisDto);

        verify(paisRepository).save(any(Pais.class));
        assertNotNull(retorno);
        assertEquals("BR", retorno.getCodigo());
    }

    @Test
    void testConsulta() throws BDException {
        Pais pais = new Pais();
        pais.setIdPais(1);
        pais.setNome("Brasil");

        PaisDto paisDto = new PaisDto();
        paisDto.setNome("Brasil");

        when(paisRepository.findByNomeContaining(anyString())).thenReturn(Collections.singletonList(pais));

        var result = paisService.consulta(paisDto);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Brasil", result.get(0).getNome());
    }

    @Test
    void testDelete() throws BDException {
        Pais pais = new Pais();
        pais.setIdPais(1);
        pais.setCodigo("BR");

        PaisDto paisDto = new PaisDto();
        paisDto.setCodigo("BR");

        when(paisRepository.findByCodigo(anyString())).thenReturn(pais);

        String resultado = paisService.delete(paisDto);

        verify(paisRepository).delete(any(Pais.class));
        assertEquals("Pais deletado com sucesso", resultado);
    }

    @Test
    void testAtualizaPorPartes() throws BDException {
        Pais pais = new Pais();
        pais.setIdPais(1);
        pais.setNome("Brasil");

        PaisDto paisDto = new PaisDto();
        paisDto.setNome("Brasil Atualizado");

        when(paisRepository.findById(anyInt())).thenReturn(Optional.of(pais));
        when(paisRepository.saveAndFlush(any(Pais.class))).thenReturn(pais);

        PaisDto resultado = paisService.atualizaPorPartes(1, paisDto);

        assertNotNull(resultado);
        assertEquals("Brasil", resultado.getNome());
    }

    @Test
    void testAtualizaPorCompleto() throws BDException {
        Pais paisBanco = new Pais();
        paisBanco.setIdPais(1);
        paisBanco.setNome("Brasil");

        PaisDto paisDto = new PaisDto();
        paisDto.setNome("Brasil Completo");
        paisDto.setCodigo("BR");
        paisDto.setContinente("América do Sul");

        when(paisRepository.findById(anyInt())).thenReturn(Optional.of(paisBanco));
        when(paisRepository.saveAndFlush(any(Pais.class))).thenReturn(paisBanco);

        PaisDto resultado = paisService.atualizaPorCompleto(1, paisDto);

        assertNotNull(resultado);
        assertEquals("BR", resultado.getCodigo());
        assertEquals("Brasil Completo", resultado.getNome());
    }

}

