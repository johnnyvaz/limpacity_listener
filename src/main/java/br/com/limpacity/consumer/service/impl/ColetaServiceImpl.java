package br.com.limpacity.consumer.service.impl;

import br.com.limpacity.consumer.dto.SolicitaColetaDTO;
import br.com.limpacity.consumer.exception.ColetaNotFoundException;
import br.com.limpacity.consumer.listener.Consumer;
import br.com.limpacity.consumer.service.ColetaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ColetaServiceImpl implements ColetaService {

    private final Consumer<SolicitaColetaDTO> consumer;

    @Override
    public void saveColeta(final SolicitaColetaDTO coleta) {
        final String coletaId = coleta.getId();
        log.info("Mensagem sendo consumida na serviçe");
        try {
            consumer.execute(coleta);
        } catch (final Exception e) {
            log.error("Error unexpected for loadId={}", coletaId);
            throw new ColetaNotFoundException();
        }

    }
}
