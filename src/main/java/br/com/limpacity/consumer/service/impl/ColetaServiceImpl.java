package br.com.limpacity.consumer.service.impl;

import br.com.limpacity.consumer.dto.SolicitaColetaDTO;
import br.com.limpacity.consumer.service.ColetaService;

public class ColetaServiceImpl implements ColetaService {


    @Override
    public void saveColeta(SolicitaColetaDTO coleta) {
        System.out.println("chegou aqui" + coleta);


    }
}
