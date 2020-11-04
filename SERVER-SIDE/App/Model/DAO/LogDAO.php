<?php

namespace App\Model\DAO;

use App\Model\DAO\ConexaoDAO;

require_once '/home2/versa357/public_html/app/desenvolvimento/App/Model/DAO/ErroDAO.php';
require_once '/home2/versa357/public_html/app/desenvolvimento/App/Model/DAO/ConexaoDAO.php';

/**
 * Classe responsável por manipular
 * o LOG de atividades dos usuários
 * 
 * @author      Igor Maximo
 * @date        21/06/2020
 */
class LogDAO {
    
    private $NOME_TABELA = "app_log";
    
     
    
    /////////////////////////////////////////////
    //                  INSERT                 //
    /////////////////////////////////////////////

    /**
     * Realiza o cadastro do cliente
     * 
     * @author      Igor Maximo
     * @date        31/05/2020
     */
    public function setAcao($acao, $fkUsuario, $fkAppVersionamento) {
        $retorno = [];
        try {
            $conexao = new ConexaoDAO();
            $conn = $conexao->conexao();

            mysqli_query($conn, "SET NAMES 'utf8'");

            $sql = "INSERT INTO " . $this->NOME_TABELA . " "
                    . "(acao, fk_app_usuario, fk_app_versionamento, data_acao)"
                    . " values "
                    . "('" . $acao . "', " . $fkUsuario . ", " . $fkAppVersionamento . ", '" . date('Y-m-d H:i:s') . "')";
            try {
                // Verifica se o cliente existe primeiro
                if (mysqli_query($conn, $sql)) {
                    return true;
                } else {
                    return false;
                }
                mysqli_close($conn);
            } catch (Exception $e1) {
//                $this->setErro(__CLASS__, __METHOD__, __LINE__, $e1->getMessage());
            }
        } catch (Exception $ex) {
//            $this->setErro(__CLASS__, __METHOD__, __LINE__, $ex->getMessage());
        }
        return $retorno;
    }

}
   