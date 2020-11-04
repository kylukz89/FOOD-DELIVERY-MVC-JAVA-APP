package com.kylukz.fooddev.JavaBeans;

import android.content.Context;

public class EntidadePagamentoCartaoCredito {

    // Atributos da etapa 02
    private int tipoPagamento;
    private double valor;
    private String etapa;
    private Context ctx;

    public EntidadePagamentoCartaoCredito(int tipoPagamento, double valor, String etapa, Context ctx) {
        this.tipoPagamento = tipoPagamento;
        this.valor = valor;
        this.etapa = etapa;
    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public int getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(int tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getEtapa() {
        return etapa;
    }

    public void setEtapa(String etapa) {
        this.etapa = etapa;
    }
}
