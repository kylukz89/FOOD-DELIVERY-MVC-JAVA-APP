<?php

namespace App\Model\DAO;

use App\Model\DAO\ConexaoDAO;

require_once '/home2/versa357/public_html/app/desenvolvimento/App/Model/DAO/ErroDAO.php';
require_once '/home2/versa357/public_html/app/desenvolvimento/App/Model/DAO/ConexaoDAO.php';

/**
 * Classe responsável por manipular
 * as cidades do sistema
 * 
 * @author      Igor Maximo
 * @date        21/06/2020
 */
class CidadeDAO /* extends ErroDAO */ {

    private $NOME_TABELA = "core_cidade";
    private $NOME_TABELA_RELACIONAMENTO = "core_uf";

    /////////////////////////////////////////////
    //                  SELECT                 //
    /////////////////////////////////////////////

    /**
     * Retorna ID da cidade por NOme
     * 
     * @author      Igor Maximo
     * @date        05/06/2020
     */
    public function getIdCidadePorNome($cidade) {
        try {
            $conexao = new ConexaoDAO();
            $conn = $conexao->conexao();
            mysqli_query($conn, "SET NAMES 'utf8'");
            $sql = "select id from " . $this->NOME_TABELA . " where upper(nome) = '" . strtoupper($cidade) . "'";
            $result = mysqli_fetch_assoc(mysqli_query($conn, $sql));
            if (intval($result['id']) > 0) {
                return intval($result['id']);
            }  
        } catch (Exception $ex) {
//            $this->setErro(__CLASS__, __METHOD__, __LINE__, $ex->getMessage());
        }
        return 0;
    }
    
    /**
     * Retorna ID da uf por id da cidade
     * 
     * @author      Igor Maximo
     * @date        05/06/2020
     */
    public function getIdUfPorIdCidade($idCidade) {
        try {
            $conexao = new ConexaoDAO();
            $conn = $conexao->conexao();
            mysqli_query($conn, "SET NAMES 'utf8'");
            $sql = "select fk_uf from " . $this->NOME_TABELA . " where id = " . $idCidade;
            $result = mysqli_fetch_assoc(mysqli_query($conn, $sql));
            if (intval($result['fk_uf']) > 0) {
                return intval($result['fk_uf']);
            }  
        } catch (Exception $ex) {
//            $this->setErro(__CLASS__, __METHOD__, __LINE__, $ex->getMessage());
        }
        return 0;
    }
}
