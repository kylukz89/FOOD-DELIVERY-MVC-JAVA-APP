package com.kylukz.fooddev.JavaBeans;



/**
 * Entidade responsável por armazenar
 * os dados do usuário
 *
 * @author Igor Maximo <igormaximo_1989@hotmail.com>
 * @date 09/05/2020
 */
public class EntidadeUsuario {

    // Dados do usuário
    private int id;
    private int modoAutenticacao; // Se por PIN, Gmail ou Facebook
    private boolean seInativo; // Também serve para "se bloqueado"
    private String nome;
    private String sobreNome;
    private String cpfCnpj;
    private String tipoCliente; // F ou J
    private String dataNascimento;
    private String dataCadastro;
    private String celular;
    private String email;
    // Notification Push (Firebase FCM)
    private String token;
    // PIN Auth
    private String pin; // Se por PIN
    private String senha;
    // Facebook Auth
    private String facebookLoginSocial;
    private String facebookNome;
    // Gmail Auth
    private String gmailLogin;
    private String gmailSenha;
    // Para todas auth
    private boolean seAuth;
    private boolean seCompletouCadastro;
    private String msgAuth;
    // Versão
    private int fkVersionamento;
    private int fkAppVersionamento;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getFkAppVersionamento() {
        return fkAppVersionamento;
    }

    public void setFkAppVersionamento(int fkAppVersionamento) {
        this.fkAppVersionamento = fkAppVersionamento;
    }

    public boolean isSeCompletouCadastro() {
        return seCompletouCadastro;
    }

    public void setSeCompletouCadastro(boolean seCompletouCadastro) {
        this.seCompletouCadastro = seCompletouCadastro;
    }

    public boolean isSeAuth() {
        return seAuth;
    }

    public void setSeAuth(boolean seAuth) {
        this.seAuth = seAuth;
    }

    public String getMsgAuth() {
        return msgAuth;
    }

    public void setMsgAuth(String msgAuth) {
        this.msgAuth = msgAuth;
    }

    public int getFkVersionamento() {
        return fkVersionamento;
    }

    public void setFkVersionamento(int fkVersionamento) {
        this.fkVersionamento = fkVersionamento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getModoAutenticacao() {
        return modoAutenticacao;
    }

    public void setModoAutenticacao(int modoAutenticacao) {
        this.modoAutenticacao = modoAutenticacao;
    }

    public boolean isSeInativo() {
        return seInativo;
    }

    public void setSeInativo(boolean seInativo) {
        this.seInativo = seInativo;
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

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
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

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getFacebookLoginSocial() {
        return facebookLoginSocial;
    }

    public void setFacebookLoginSocial(String facebookLoginSocial) {
        this.facebookLoginSocial = facebookLoginSocial;
    }

    public String getFacebookNome() {
        return facebookNome;
    }

    public void setFacebookNome(String facebookNome) {
        this.facebookNome = facebookNome;
    }

    public String getGmailLogin() {
        return gmailLogin;
    }

    public void setGmailLogin(String gmailLogin) {
        this.gmailLogin = gmailLogin;
    }

    public String getGmailSenha() {
        return gmailSenha;
    }

    public void setGmailSenha(String gmailSenha) {
        this.gmailSenha = gmailSenha;
    }
}
