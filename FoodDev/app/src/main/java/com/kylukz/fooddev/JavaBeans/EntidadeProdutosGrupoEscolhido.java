package com.kylukz.fooddev.JavaBeans;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Entidade responsável por montar a roleta de
 * produtos de um determinado grupo
 *
 * @usages      PontoActivity
 * @author      Igor Maximo <igormaximo_1989@hotmail.com>
 * @date        12/04/2020
 */
public class EntidadeProdutosGrupoEscolhido {

    private int id;
    private int grupo;
    private String nomeProduto;
    private String descricao;
    private double valor;
    private Bitmap imagemProduto;
    private Context ctx;
    // Atributos extras do produto escolhido
    private boolean seTemEtapasPersonalizadas;
    private int quantidade;
    private HashMap<Integer, ArrayList> itensAdicionais; // Itens adicionais que podem ser comprados junto ao produto
    private ArrayList<String> etapas;
    private String observacao;

    public EntidadeProdutosGrupoEscolhido() {

    }

    public EntidadeProdutosGrupoEscolhido(int grupo, String nomeOpcao, Bitmap imagem, double valor, Context ctx, boolean seTemEtapasPersonalizadas, String descricao, HashMap<Integer, ArrayList> itensAdicionais, ArrayList<String> etapas) {
        this.grupo = grupo;
        this.nomeProduto = nomeOpcao;
        this.imagemProduto = imagem;
        this.valor = valor;
        this.ctx = ctx;
        this.descricao = descricao;
        this.seTemEtapasPersonalizadas = seTemEtapasPersonalizadas;
        this.itensAdicionais = itensAdicionais;
        this.etapas = etapas;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getGrupo() {
        return grupo;
    }

    public void setGrupo(int grupo) {
        this.grupo = grupo;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Bitmap getImagemProduto() {
        return imagemProduto;
    }

    public void setImagemProduto(Bitmap imagemProduto) {
        this.imagemProduto = imagemProduto;
    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public boolean isSeTemEtapasPersonalizadas() {
        return seTemEtapasPersonalizadas;
    }

    public void setSeTemEtapasPersonalizadas(boolean seTemEtapasPersonalizadas) {
        this.seTemEtapasPersonalizadas = seTemEtapasPersonalizadas;
    }


    public HashMap<Integer, ArrayList> getItensAdicionais() {
        return itensAdicionais;
    }

    public void setItensAdicionais(HashMap<Integer, ArrayList> itensAdicionais) {
        this.itensAdicionais = itensAdicionais;
    }

    public ArrayList<String> getEtapas() {
        return etapas;
    }

    public void setEtapas(ArrayList<String> etapas) {
        this.etapas = etapas;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
