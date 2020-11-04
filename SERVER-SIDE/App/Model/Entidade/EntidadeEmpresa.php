<?php

namespace App\Model\Entidade;


class EntidadeEmpresa {
    
    private $id;
    private $nome;
    private $imagem;
    
    function getId() {
        return $this->id;
    }

    function getNome() {
        return $this->nome;
    }

    function getImagem() {
        return $this->imagem;
    }

    function setId($id) {
        $this->id = $id;
    }

    function setNome($nome) {
        $this->nome = $nome;
    }

    function setImagem($imagem) {
        $this->imagem = $imagem;
    }


}