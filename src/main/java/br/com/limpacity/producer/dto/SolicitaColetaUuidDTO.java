package br.com.limpacity.producer.dto;

import br.com.limpacity.listener.dto.SolicitaColetaDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SolicitaColetaUuidDTO {

    private UUID uuid;

    private SolicitaColetaDTO solicitaColeta;

}
