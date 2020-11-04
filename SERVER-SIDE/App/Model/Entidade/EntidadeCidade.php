<?php

namespace App\Model\Entidade;

/**
 * Entidade responsável
 * por manipular as cidades
 * 
 * @author      Igor Maixmo
 * @date        21/06/2020
 */
class EntidadeCidade {
    
    private $id;
    private $fkUf;
    private $nome;
    private $ibge;
    private $apelido;
    private $lat;
    private $long;
    private $obs;
    
    function getId() {
        return $this->id;
    }

    function getFkUf() {
        return $this->fkUf;
    }

    function getNome() {
        return $this->nome;
    }

    function getIbge() {
        return $this->ibge;
    }

    function getApelido() {
        return $this->apelido;
    }

    function getLat() {
        return $this->lat;
    }

    function getLong() {
        return $this->long;
    }

    function getObs() {
        return $this->obs;
    }

    function setId($id) {
        $this->id = $id;
    }

    function setFkUf($fkUf) {
        $this->fkUf = $fkUf;
    }

    function setNome($nome) {
        $this->nome = $nome;
    }

    function setIbge($ibge) {
        $this->ibge = $ibge;
    }

    function setApelido($apelido) {
        $this->apelido = $apelido;
    }

    function setLat($lat) {
        $this->lat = $lat;
    }

    function setLong($long) {
        $this->long = $long;
    }

    function setObs($obs) {
        $this->obs = $obs;
    }


    
}
