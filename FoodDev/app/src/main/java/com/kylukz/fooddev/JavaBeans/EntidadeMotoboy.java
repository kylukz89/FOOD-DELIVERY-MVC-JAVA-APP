package com.kylukz.fooddev.JavaBeans;

/**
 * Entidade responsável por armazenar
 * os dados do motoboy
 *
 * @author      Igor Maximo <igormaximo_1989@hotmail.com>
 * @date        16/06/2020
 */
public class EntidadeMotoboy {

    private int id;
    private String placa;
    private String veiculo;
    private String foto;
    private int fkUfPlaca;
    private int fkEndereco;
    private int fkUsuario;
    private String longitudeAtual;
    private String latitudeAtual;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getFkUfPlaca() {
        return fkUfPlaca;
    }

    public void setFkUfPlaca(int fkUfPlaca) {
        this.fkUfPlaca = fkUfPlaca;
    }

    public int getFkEndereco() {
        return fkEndereco;
    }

    public void setFkEndereco(int fkEndereco) {
        this.fkEndereco = fkEndereco;
    }

    public int getFkUsuario() {
        return fkUsuario;
    }

    public void setFkUsuario(int fkUsuario) {
        this.fkUsuario = fkUsuario;
    }

    public String getLongitudeAtual() {
        return longitudeAtual;
    }

    public void setLongitudeAtual(String longitudeAtual) {
        this.longitudeAtual = longitudeAtual;
    }

    public String getLatitudeAtual() {
        return latitudeAtual;
    }

    public void setLatitudeAtual(String latitudeAtual) {
        this.latitudeAtual = latitudeAtual;
    }
}
