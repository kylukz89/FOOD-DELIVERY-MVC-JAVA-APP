package com.kylukz.fooddev.JavaBeans;

import android.content.Context;

public class EntidadePagamentoEscolhaModo {
    // Atributos Gerais
    private int numeroEtapa;
    private int qtdEtapas;

    // Atributos da etapa 01
    private String credito;
    private String debito;
    private String receberLocal;

    private Context ctx;

    public EntidadePagamentoEscolhaModo(String credito, String debito, String receberLocal, Context ctx) {
        this.credito = credito;
        this.debito = debito;
        this.receberLocal = receberLocal;
        this.ctx = ctx;
    }

    public int getNumeroEtapa() {
        return numeroEtapa;
    }

    public void setNumeroEtapa(int numeroEtapa) {
        this.numeroEtapa = numeroEtapa;
    }

    public int getQtdEtapas() {
        return qtdEtapas;
    }

    public void setQtdEtapas(int qtdEtapas) {
        this.qtdEtapas = qtdEtapas;
    }

    public String getCredito() {
        return credito;
    }

    public void setCredito(String credito) {
        this.credito = credito;
    }

    public String getDebito() {
        return debito;
    }

    public void setDebito(String debito) {
        this.debito = debito;
    }

    public String getReceberLocal() {
        return receberLocal;
    }

    public void setReceberLocal(String receberLocal) {
        this.receberLocal = receberLocal;
    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }
}
