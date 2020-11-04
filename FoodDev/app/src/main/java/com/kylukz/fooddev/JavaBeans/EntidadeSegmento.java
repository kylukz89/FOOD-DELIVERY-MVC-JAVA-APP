package com.kylukz.fooddev.JavaBeans;


import android.content.Context;
import android.content.Intent;

/**
 * Entidade responsável por gerar o menu
 * de todos os segmentos
 */
public class EntidadeSegmento {

    private int id;
    private int imagem;
    private String nome;
    private String descricao;
    private Intent intent;
    private Context ctx;

    public EntidadeSegmento(int id, int imagem, String nome, String descricao, Intent intent, Context ctx) {
        this.id = id;
        this.imagem = imagem;
        this.nome = nome;
        this.descricao = descricao;
        this.ctx = ctx;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }
}
