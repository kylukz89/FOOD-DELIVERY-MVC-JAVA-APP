<?php

namespace App\Model\DAO;

use App\Model\DAO\ConexaoDAO;

require_once '/home2/versa357/public_html/app/desenvolvimento/App/Model/DAO/ErroDAO.php';
require_once '/home2/versa357/public_html/app/desenvolvimento/App/Model/DAO/ConexaoDAO.php';

/**
 * Entidade responsa´vel por gerenciar
 * os dados dos grupos de itens por empresa
 * 
 * @author      Igor Maximo
 * @date        31/05/2020
 */
class VersionamentoDAO /* extends ErroDAO */ {

    private $NOME_TABELA = "app_versionamento";
    private $NOME_TABELA_RELACIONAMENTO = "app_usuario_versionamento";

    /////////////////////////////////////////////
    //                  SELECT                 //
    /////////////////////////////////////////////
    

    /**
     * Retorna fk versionamento
     * que usuário estava na última vez
     * que logou
     * 
     * @author      Igor Maximo
     * @date        21/06/2020
     */
    public function getFkVersionamentoUltimaVezUsuarioLogou($fkUsuario) {
        try {
            $conexao = new ConexaoDAO();
            $conn = $conexao->conexao();
            mysqli_query($conn, "SET NAMES 'utf8'");
            $sql = "SELECT fk_versionamento FROM " . $this->NOME_TABELA_RELACIONAMENTO . " where fk_usuario = " . $fkUsuario . " order by id desc limit 1";
            $result = mysqli_fetch_assoc(mysqli_query($conn, $sql));
            return $result['fk_versionamento'];
        } catch (Exception $ex) {
//            $this->setErro(__CLASS__, __METHOD__, __LINE__, $ex->getMessage());
        }
    }
    
    /**
     * Retorna fk versionamento
     * que usuário estava na última vez
     * que logou
     * 
     * @author      Igor Maximo
     * @date        21/06/2020
     */
    private function getIdUltimoVersionamentoDefinidoUsuario($fkUsuario) {
        try {
            $conexao = new ConexaoDAO();
            $conn = $conexao->conexao();
            mysqli_query($conn, "SET NAMES 'utf8'");
            $sql = "SELECT id FROM " . $this->NOME_TABELA_RELACIONAMENTO . " where fk_usuario = " . $fkUsuario . " order by id desc limit 1";
            error_log($sql);
            $result = mysqli_fetch_assoc(mysqli_query($conn, $sql));
            return $result['id'];
        } catch (Exception $ex) {
//            $this->setErro(__CLASS__, __METHOD__, __LINE__, $ex->getMessage());
        }
    }
     
    
    
    /////////////////////////////////////////////
    //                  INSERT                 //
    /////////////////////////////////////////////

    /**
     * Insere um novo versionamento do
     * usuário caso o mesmo ainda não tenha
     * ou tenha mudado
     * 
     * @author      Igor Maximo
     * @date        21/06/2020
     */
    public function setInserirVersionamentoUsuario($fkVersionamento, $fkUsuario) {
        try {
            $conexao = new ConexaoDAO();
            $conn = $conexao->conexao();
            mysqli_query($conn, "SET NAMES 'utf8'");
            // Gerador de PIN
            $sql = "insert into " . $this->NOME_TABELA_RELACIONAMENTO . " (fk_usuario, fk_versionamento, ultima_atualizacao) "
                    . "values "
                    . "(" . $fkUsuario . ", " . $fkVersionamento . ", '" . date('Y-m-d H:i:s') . "')";

            // Atesta que o cliente completou o cadastro dele
            if (mysqli_query($conn, $sql)) {
                return $this->getIdUltimoVersionamentoDefinidoUsuario($fkUsuario);
            } else {
                return 0;
            }
            mysqli_close($conn);
        } catch (Exception $ex) {
//            $this->setErro(__CLASS__, __METHOD__, __LINE__, $ex->getMessage());
        }
    }

    /////////////////////////////////////////////
    //                  UPDATE                 //
    /////////////////////////////////////////////

    /**
     * Atualiza versionamento do
     * usuário sempre que ele logar
     * 
     * @author      Igor Maximo
     * @date        21/06/2020
     */
//    public function setAtualizarVersionamentoUsuario($fkVersionamento, $id) {
//        try {
//            $conexao = new ConexaoDAO();
//            $conn = $conexao->conexao();
//            mysqli_query($conn, "SET NAMES 'utf8'");
//            // Gerador de PIN
//            $sql = "update " . $this->NOME_TABELA_RELACIONAMENTO . " set fk_versionamento = " . $fkVersionamento . " where fk_usuario = " . $id;
//            // Atesta que o cliente completou o cadastro dele
//            if (mysqli_query($conn, $sql)) {
//                return true;
//            } else {
//                return false;
//            }
//            mysqli_close($conn);
//        } catch (Exception $ex) {
////            $this->setErro(__CLASS__, __METHOD__, __LINE__, $ex->getMessage());
//        }
//    }

}
