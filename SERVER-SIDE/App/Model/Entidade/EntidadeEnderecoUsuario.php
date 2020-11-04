<?php



namespace App\Model\Entidade;


/**
 * Entidade responsável por manipular
 * os dados demais dados cadastrais do usuario
 * 
 * @author      Igor Maximo
 * @date        21/06/2020
 */
class EntidadeEnderecoUsuario {
 
    private $id;
    private $fkUsuario;
    private $fkCidade;
    private $fkUf;
    private $nomeCidade;
    private $numero;
    private $bairro;
    private $cep;
    private $pontoRef;
    private $obs;
    private $latitude;
    private $longitude;
    
    function getLatitude() {
        return $this->latitude;
    }

    function getLongitude() {
        return $this->longitude;
    }

    function setLatitude($latitude) {
        $this->latitude = $latitude;
    }

    function setLongitude($longitude) {
        $this->longitude = $longitude;
    }

        
    
    function getFkUsuario() {
        return $this->fkUsuario;
    }

    function setFkUsuario($fkUsuario) {
        $this->fkUsuario = $fkUsuario;
    }

        
    function getId() {
        return $this->id;
    }

    function getFkCidade() {
        return $this->fkCidade;
    }

    function getFkUf() {
        return $this->fkUf;
    }

    function getNomeCidade() {
        return $this->nomeCidade;
    }

    function getNumero() {
        return $this->numero;
    }

    function getBairro() {
        return $this->bairro;
    }

    function getCep() {
        return $this->cep;
    }

    function getPontoRef() {
        return $this->pontoRef;
    }

    function getObs() {
        return $this->obs;
    }

    function setId($id) {
        $this->id = $id;
    }

    function setFkCidade($fkCidade) {
        $this->fkCidade = $fkCidade;
    }

    function setFkUf($fkUf) {
        $this->fkUf = $fkUf;
    }

    function setNomeCidade($nomeCidade) {
        $this->nomeCidade = $nomeCidade;
    }

    function setNumero($numero) {
        $this->numero = $numero;
    }

    function setBairro($bairro) {
        $this->bairro = $bairro;
    }

    function setCep($cep) {
        $this->cep = $cep;
    }

    function setPontoRef($pontoRef) {
        $this->pontoRef = $pontoRef;
    }

    function setObs($obs) {
        $this->obs = $obs;
    }


    

}