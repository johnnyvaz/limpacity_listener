package br.com.limpacity.listener.dto;

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

    private String material;

    private String endereco;

    private String numero;

    private String municipio;

    private String cep;

    private Boolean reciclavel;

    private Date dataLimite;

    private Long quantidade;

    private String integrationStatus;
}
