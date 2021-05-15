package br.com.limpacity.producer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendColetaQrCodeDTO {

    private Long postoId;

    private String uuid;

    private String observacao;

}
