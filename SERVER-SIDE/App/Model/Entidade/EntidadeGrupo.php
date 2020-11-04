<?php
namespace App\Model\Entidade;

/**
 * Entidade responsável por grupo
 * dos produtos de uma empresa
 * 
 * @author      Igor Maximo  
 * @date        31/05/2020
 */
class EntidadeGrupo {
    
    private $id;
    private $imagem;
    private $fkEmpresa;
    private $status;
    private $nome;
    private $sePizza;
 
    function getId() {
        return $this->id;
    }

    function getImagem() {
        return $this->imagem;
    }

    function getFkEmpresa() {
        return $this->fkEmpresa;
    }

    function getStatus() {
        return $this->status;
    }

    function getNome() {
        return $this->nome;
    }

    function getSePizza() {
        return $this->sePizza;
    }

    function setId($id) {
        $this->id = $id;
    }

    function setImagem($imagem) {
        $this->imagem = $imagem;
    }

    function setFkEmpresa($fkEmpresa) {
        $this->fkEmpresa = $fkEmpresa;
    }

    function setStatus($status) {
        $this->status = $status;
    }

    function setNome($nome) {
        $this->nome = $nome;
    }

    function setSePizza($sePizza) {
        $this->sePizza = $sePizza;
    }



}