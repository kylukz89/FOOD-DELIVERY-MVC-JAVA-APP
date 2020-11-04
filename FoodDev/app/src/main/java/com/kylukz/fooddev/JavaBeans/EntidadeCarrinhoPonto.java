package com.kylukz.fooddev.JavaBeans;

import android.content.Context;

/**
 * Entidade responsável por armazenar a comanda
 * do ponto
 *
 * @author      Igor Maximo <igormaximo_1989@hotmail.com>
 * @date        24/04/2020
 */
public class EntidadeCarrinhoPonto extends ClassLoader {

    private int id;
    private String nomeProduto;
    private float valor;
    private int quantidade;
    private Context ctx;

    /**
     * Para usar apenas o carrhino
     */
    public EntidadeCarrinhoPonto() {

    }

    /**
     * Para montar a recyclerview
     */
    public EntidadeCarrinhoPonto(int id, String nomeProduto, float valor, int quantidade, Context ctx) {
        this.id = id;
        this.nomeProduto = nomeProduto;
        this.valor = valor;
        this.quantidade = quantidade;
        this.ctx = ctx;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }
}
