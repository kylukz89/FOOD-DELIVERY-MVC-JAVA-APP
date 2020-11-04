package com.kylukz.fooddev.JavaBeans;

import android.content.Context;
import android.content.Intent;

public class EntidadeBotoesMenuPonto {

    private int id;
    private String nomeBotao;
    private int imagemBotao;
    private int imagemFlagContemItens;
    private Intent intent;
    private Context ctx;

    public EntidadeBotoesMenuPonto(int id, String nomeBotao, int imagemBotao, int imagemFlagContemItens, Intent intent, Context ctx) {
        this.id = id;
        this.nomeBotao = nomeBotao;
        this.imagemBotao = imagemBotao;
        this.imagemFlagContemItens = imagemFlagContemItens;
        this.intent = intent;
        this.ctx = ctx;
    }

    public EntidadeBotoesMenuPonto(int id, String nomeBotao, int imagemBotao, Intent intent, Context ctx) {
        this.id = id;
        this.nomeBotao = nomeBotao;
        this.imagemBotao = imagemBotao;
        this.intent = intent;
        this.ctx = ctx;
    }

    public int getImagemFlagContemItens() {
        return imagemFlagContemItens;
    }

    public void setImagemFlagContemItens(int imagemFlagContemItens) {
        this.imagemFlagContemItens = imagemFlagContemItens;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeBotao() {
        return nomeBotao;
    }

    public void setNomeBotao(String nomeBotao) {
        this.nomeBotao = nomeBotao;
    }

    public int getImagemBotao() {
        return imagemBotao;
    }

    public void setImagemBotao(int imagemBotao) {
        this.imagemBotao = imagemBotao;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }
}
