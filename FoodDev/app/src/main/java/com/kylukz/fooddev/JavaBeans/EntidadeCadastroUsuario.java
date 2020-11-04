package com.kylukz.fooddev.JavaBeans;

/**
 * Entidade responsável por armazenar
 * os dados referentes ao cadastro por PIN
 *
 * @author      Igor Maximo <igormaximo_1989@hotmail.com>
 * @date        16/06/2020
 */
public class EntidadeCadastroUsuario {

    private int id;
    private String nome;
    private String sobreNome;
    private String cpf;
    private String dataNascimento;
    private String celular;
    private String email;
    private String senha;
    private boolean seMotoboy;
    private boolean seAtivo;
    // Para completar o cadastro
    private String cep;
    private String endereco;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;
    private String referencia;
    private String longitude;
    private String latitude;

    public boolean isSeMotoboy() {
        return seMotoboy;
    }

    public void setSeMotoboy(boolean seMotoboy) {
        this.seMotoboy = seMotoboy;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobreNome() {
        return sobreNome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isSeAtivo() {
        return seAtivo;
    }

    public void setSeAtivo(boolean seAtivo) {
        this.seAtivo = seAtivo;
    }
}
