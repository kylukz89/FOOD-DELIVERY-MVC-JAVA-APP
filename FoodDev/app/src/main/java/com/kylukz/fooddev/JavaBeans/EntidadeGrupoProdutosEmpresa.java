package com.kylukz.fooddev.JavaBeans;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Entidade que auxlilia recyclerview das opções do ponto
 */
public class EntidadeGrupoProdutosEmpresa {

    private int id;
    private String nomeGrupo;
    private Bitmap imagem;
    private int fkEmpresa;
    private boolean sePizza;
    private Context ctx;

    public EntidadeGrupoProdutosEmpresa(int id, String nomeGrupo, Bitmap imagem, Context ctx, int fkEmpresa, boolean sePizza) {
        this.id = id;
        this.nomeGrupo = nomeGrupo;
        this.imagem = imagem;
        this.ctx = ctx;
        this.fkEmpresa = fkEmpresa;
        this.sePizza = sePizza;
    }

    public int getFkEmpresa() {
        return fkEmpresa;
    }

    public void setFkEmpresa(int fkEmpresa) {
        this.fkEmpresa = fkEmpresa;
    }


    public boolean getSePizza() {
        return sePizza;
    }

    public void setSePizza(boolean sePizza) {
        this.sePizza = sePizza;
    }

    public Bitmap getImagem() {
        return imagem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImagem(Bitmap imagem) {
        this.imagem = imagem;
    }

    public String getNomeGrupo() {
        return nomeGrupo;
    }

    public void setNomeGrupo(String nomeGrupo) {
        this.nomeGrupo = nomeGrupo;
    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }
}