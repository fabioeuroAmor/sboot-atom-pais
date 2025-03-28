package br.com.google.service;


import br.com.google.domain.Pais;
import br.com.google.repository.PaisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaisService {

    @Autowired
    private PaisRepository paisRepository;


    }

