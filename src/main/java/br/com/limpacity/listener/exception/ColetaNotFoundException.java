package br.com.limpacity.listener.exception;

public class ColetaNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -992179043136943635L;

    public ColetaNotFoundException(){
        super(" Coleta Não encontrada");
    }
}
