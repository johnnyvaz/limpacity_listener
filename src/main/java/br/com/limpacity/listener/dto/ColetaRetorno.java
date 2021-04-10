package br.com.limpacity.listener.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColetaRetorno {

    private String mensagem;
    private Boolean coletaRegistrada;
}
