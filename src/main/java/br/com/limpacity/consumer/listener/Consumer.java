package br.com.limpacity.consumer.listener;

public interface Consumer<T> {

    void execute(T message);
}
