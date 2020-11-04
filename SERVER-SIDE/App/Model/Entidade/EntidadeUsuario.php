<?php

namespace App\Model\Entidade;

/**
 * Entidade responsável por manipular
 * os dados do usuário
 * 
 * @author      Igor Maximo
 * @date 04/06/2020
 */
class EntidadeUsuario {
    
    private $id;
    private $nome;
    private $sobrenome;
    private $cpf;
    private $dataNascimento;
    private $celular;
    private $email;
    private $senha;
    // Cadastro completo
    private $seCompletouCadastro;
    private $fkVersionamento;
    
    function getFkVersionamento() {
        return $this->fkVersionamento;
    }

    function setFkVersionamento($fkVersionamento) {
        $this->fkVersionamento = $fkVersionamento;
    }

        
    
    function getSeCompletouCadastro() {
        return $this->seCompletouCadastro;
    }

    function setSeCompletouCadastro($seCompletouCadastro) {
        $this->seCompletouCadastro = $seCompletouCadastro;
    }

        
    function getId() {
        return $this->id;
    }

    function getNome() {
        return $this->nome;
    }

    function getSobrenome() {
        return $this->sobrenome;
    }

    function getCpf() {
        return $this->cpf;
    }

    function getDataNascimento() {
        return $this->dataNascimento;
    }

    function getCelular() {
        return $this->celular;
    }

    function setId($id) {
        $this->id = $id;
    }

    function setNome($nome) {
        $this->nome = $nome;
    }

    function setSobrenome($sobrenome) {
        $this->sobrenome = $sobrenome;
    }

    function setCpf($cpf) {
        $this->cpf = $cpf;
    }

    function setDataNascimento($dataNascimento) {
        $this->dataNascimento = $dataNascimento;
    }

    function setCelular($celular) {
        $this->celular = $celular;
    }
    
    function getEmail() {
        return $this->email;
    }

    function getSenha() {
        return $this->senha;
    }

    function setEmail($email) {
        $this->email = $email;
    }

    function setSenha($senha) {
        $this->senha = $senha;
    }


    
}