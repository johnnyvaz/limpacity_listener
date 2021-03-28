package br.com.limpacity.consumer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SolicitaColetaDTO {

    private String id;

    private String type;

    private String endereco;

    private Boolean reciclavel;

    private Date dataLimite;

    private Long quantidade;

    private String integrationStatus;

    Date creationDate;
}
