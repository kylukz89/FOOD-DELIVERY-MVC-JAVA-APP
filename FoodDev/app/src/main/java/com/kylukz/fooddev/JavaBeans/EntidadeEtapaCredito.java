package com.kylukz.fooddev.JavaBeans;

public class EntidadeEtapaCredito {
    // Atributos Gerais
    private int numeroEtapa;
    private int qtdEtapas;

    // Atributos da etapa 01
    private String credito;
    private String debito;
    private String receberLocal;

    public EntidadeEtapaCredito(String credito, String debito, String receberLocal) {
        this.credito = credito;
        this.debito = debito;
        this.receberLocal = receberLocal;
    }

    // Atributos da etapa 02
    private int tipoPagamento;
    private double valor;
    private String etapa;

    public EntidadeEtapaCredito(int tipoPagamento, double valor, String etapa, Object tag) {

        this.tipoPagamento = tipoPagamento;
        this.valor = valor;
        this.etapa = etapa;
    }


}
