package com.kylukz.fooddev.JavaBeans;


import android.content.Context;
import android.graphics.Bitmap;

/**
 * Entidade responsável por gerar o menu
 * de todos os pontos
 */
public class EntidadeEmpresa {

    // MenuPrincipal
    private int id;
    private Bitmap imagem;
    private String nome;
    private String endereco;
    private String descricao;
    private String categoria;
    private Object modeloNegocio;
    private Context ctx;

    public EntidadeEmpresa() {

    }

    public EntidadeEmpresa(int id, Bitmap imagem, String nome, String endereco, String descricao, String categoria, Object modeloNegocio, Context ctx) {
        this.id = id;
        this.imagem = imagem;
        this.nome = nome;
        this.endereco = endereco;
        this.descricao = descricao;
        this.categoria = categoria;
        this.modeloNegocio = modeloNegocio;
        this.ctx = ctx;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Object getModeloNegocio() {
        return modeloNegocio;
    }

    public void setModeloNegocio(Object modeloNegocio) {
        this.modeloNegocio = modeloNegocio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bitmap getImagem() {
        return imagem;
    }

    public void setImagem(Bitmap imagem) {
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }
}
