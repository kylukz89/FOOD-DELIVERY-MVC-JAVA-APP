package com.kylukz.fooddev.JavaBeans;

public class EntidadeLog extends EntidadeErro {

    private int id;
    private String descricao;
    private int linha;
    private String nivelEnd;
    private int fkEmpresa;
    private int fkAppUsuario;
    private int fKAppVersionamento;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public String getNivelEnd() {
        return nivelEnd;
    }

    public void setNivelEnd(String nivelEnd) {
        this.nivelEnd = nivelEnd;
    }

    public int getFkEmpresa() {
        return fkEmpresa;
    }

    public void setFkEmpresa(int fkEmpresa) {
        this.fkEmpresa = fkEmpresa;
    }

    public int getFkAppUsuario() {
        return fkAppUsuario;
    }

    public void setFkAppUsuario(int fkAppUsuario) {
        this.fkAppUsuario = fkAppUsuario;
    }

    public int getfKAppVersionamento() {
        return fKAppVersionamento;
    }

    public void setfKAppVersionamento(int fKAppVersionamento) {
        this.fKAppVersionamento = fKAppVersionamento;
    }
}
