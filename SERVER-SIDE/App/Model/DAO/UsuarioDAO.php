<?php

namespace App\Model\DAO;

use App\Model\DAO\ConexaoDAO;
use App\Model\DAO\EnderecoUsuarioDAO;
use App\Model\DAO\VersionamentoDAO;
use App\Model\DAO\LogDAO;
use App\Model\Entidade\EntidadeUsuario;

require_once '/home2/versa357/public_html/app/desenvolvimento/App/Model/DAO/ErroDAO.php';
require_once '/home2/versa357/public_html/app/desenvolvimento/App/Model/DAO/ConexaoDAO.php';
require_once '/home2/versa357/public_html/app/desenvolvimento/App/Model/DAO/EnderecoUsuarioDAO.php';
require_once '/home2/versa357/public_html/app/desenvolvimento/App/Model/Entidade/EntidadeUsuario.php';
require_once '/home2/versa357/public_html/app/desenvolvimento/App/Model/DAO/LogDAO.php';
/**
 * Classe responsável por manipular
 * os dados do usuário
 * 
 * @author      Igor Maximo
 * @date        31/05/2020
 */
class UsuarioDAO /* extends ErroDAO */ {

    private $NOME_TABELA = "app_usuario";

    /////////////////////////////////////////////
    //                  SELECT                 //
    /////////////////////////////////////////////

    /**
     * Gera um número de PIN para
     * cadastros novos
     * 
     * @author      Igor Maximo
     * @date        05/06/2020
     */
    private function geraPIN() {
        try {
            $pin = random_int(10000000, 99999999);
            // Verifica se o PIN gerado já existe
            $conexao = new ConexaoDAO();
            $conn = $conexao->conexao();

            mysqli_query($conn, "SET NAMES 'utf8'");

            $sql = "select exists(select pin_gerado "
                    . "from " . $this->NOME_TABELA . " where pin_gerado = " . $pin . ") as existe";

            $result = mysqli_fetch_assoc(mysqli_query($conn, $sql));

            if (intval($result['existe']) == 0) {
                return $pin;
            } else {
                $this->geraPIN();
            }
        } catch (Exception $ex) {
//            $this->setErro(__CLASS__, __METHOD__, __LINE__, $ex->getMessage());
        }
        return $pin;
    }

    /**
     * Verifica se um cliente já existe
     * na base 
     * 
     * @author      Igor Maximo
     * @date        18/06/2020
     */
    private function getSeClienteExiste($cpf) {
        try {
            $conexao = new ConexaoDAO();
            $conn = $conexao->conexao();
            mysqli_query($conn, "SET NAMES 'utf8'");

            $sql = "select exists(select cpf from " . $this->NOME_TABELA . " where cpf = '" . $cpf . "') as existe";

            $result = mysqli_fetch_assoc(mysqli_query($conn, $sql));

            if (intval($result['existe']) > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception $ex) {
//            $this->setErro(__CLASS__, __METHOD__, __LINE__, $ex->getMessage());
        }
    }

    /**
     * Coleta dados do usuário
     * para autenticação por PIN
     * 
     * @author      Igor Maximo
     * @date        18/06/2020
     */
    public function getUsuarioPorPin($pin) {
        $retorno = [];
        try {
            $enderecoUusuarioDAO = new EnderecoUsuarioDAO();
            $conexao = new ConexaoDAO();
            $conn = $conexao->conexao();
            mysqli_query($conn, "SET NAMES 'utf8'");

            $sql = "select * from " . $this->NOME_TABELA
                    . " where pin_gerado = '" . $pin . "'";
            $result = mysqli_fetch_array(mysqli_query($conn, $sql));
            if (count($result) > 0) {
                // Msg
                $retorno['status'] = "true";
                // Coleta retorno
                $retorno['id'] = $result['id'];
                $retorno['nome'] = $result['nome'];
                $retorno['sobrenome'] = $result['sobrenome'];
                $retorno['cpf'] = $result['cpf'];
                $retorno['pin_gerado'] = $result['pin_gerado'];
                $retorno['senha'] = $result['senha'];
                $retorno['seCompletouCadastro'] = $result['se_completou_cadastro'];
            } else {
                // Msg
                $retorno['status'] = "false";
                $retorno['msg'] = "PIN inexistente!";
            }
        } catch (Exception $ex) {
//            $this->setErro(__CLASS__, __METHOD__, __LINE__, $ex->getMessage());
        }
        return $retorno;
    }

    /**
     * Coleta dados do usuário
     * para autenticação por PIN
     * 
     * @author      Igor Maximo
     * @date        18/06/2020
     */
    public function getUsuarioPorCPF($cpf) {
        $retorno = [];
        try {
            $conexao = new ConexaoDAO();
            $conn = $conexao->conexao();
            mysqli_query($conn, "SET NAMES 'utf8'");

            $sql = "select * from " . $this->NOME_TABELA 
                    . " where cpf = '" . $cpf . "'";
            
            error_log($sql);
            
            $result = mysqli_fetch_array(mysqli_query($conn, $sql));
            
            if ($result['id'] > 0) {
                // Msg
                $retorno['status'] = 'true';
                // Coleta retorno
                $retorno['nome'] = $result['nome'];
                $retorno['sobrenome'] = $result['sobrenome'];
                $retorno['email'] = $result['email'];
                $retorno['pin_gerado'] = $result['pin_gerado'];
                $retorno['senha'] = $result['senha'];
            } else {
                // Msg
                $retorno['status'] = 'false';
                $retorno['msg'] = "CPF inexistente!";
            }
        } catch (Exception $ex) {
            error_log($ex);
//            $this->setErro(__CLASS__, __METHOD__, __LINE__, $ex->getMessage());
        }
        return $retorno;
    }

    /////////////////////////////////////////////
    //                  INSERT                 //
    /////////////////////////////////////////////

    /**
     * Realiza o cadastro do cliente
     * 
     * @author      Igor Maximo
     * @date        31/05/2020
     */
    public function setCadastrarUsuario(EntidadeUsuario $entidade) {
        $retorno = [];
        try {
            $logDAO = new LogDAO();
            $conexao = new ConexaoDAO();
            $conn = $conexao->conexao();

            mysqli_query($conn, "SET NAMES 'utf8'");

            // Gerador de PIN
            $pinGerado = intval($this->geraPIN());

            $sql = "INSERT INTO " . $this->NOME_TABELA . " "
                    . "(nome, sobrenome, cpf, nascimento, celular, email, pin_gerado, senha, data_cadastro)"
                    . " values "
                    . "('" . $entidade->getNome() . "',"
                    . "'" . $entidade->getSobrenome() . "',"
                    . "'" . $entidade->getCpf() . "',"
                    . "'" . $entidade->getDataNascimento() . "',"
                    . "'" . $entidade->getCelular() . "',"
                    . "'" . $entidade->getEmail() . "',"
                    . "" . $pinGerado . ","
                    . "'" . $entidade->getSenha() . "'"
                    . "'" . date('Y-m-d H:i:s') . "'"
                    . ")";
            try {
                // Verifica se o cliente existe primeiro
                if (!$this->getSeClienteExiste($entidade->getCpf())) {
                    if (mysqli_query($conn, $sql)) {
                        $retorno['status'] = 'true';
                        $retorno['msg'] = "Cadastrado! Aguarde o PIN para o e-mail: " . $entidade->getEmail();
                        $retorno['pin'] = $pinGerado;
                        // Cria o primeiro versionamento para o usuário
                        $versionamentoDAO = new VersionamentoDAO();
                        $versionamentoDAO->setInserirVersionamentoUsuario($entidade->getFkVersionamento(), $this->getUsuarioPorPin($pinGerado)['id']);
                    } else {
                        $retorno['status'] = 'false';
                        $retorno['msg'] = "Erro ao cadastrar ou ao receber os dados!";
                        $retorno['pin'] = 0;
                    }
                } else {
                    // Se cliente já existe
                    $retorno['status'] = 'false';
                    $retorno['msg'] = "CPF já cadastrado anteriormente!";
                    $retorno['pin'] = 0;
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
    
    
    /**
     * Realiza o atualizacao do token firebase fcm
     * do usuário
     * 
     * @author      Igor Maximo
     * @date        23/06/2020
     */
    public function setFirebaseFcmTokenUsuario($cpf, $token) {
        try {
            $logDAO = new LogDAO();
            $conexao = new ConexaoDAO();
            $conn = $conexao->conexao();
            mysqli_query($conn, "SET NAMES 'utf8'");
            $sql = "update " . $this->NOME_TABELA 
                    . " set token_firebase_fcm = '" . $token . "',"
                    . " data_ultimo_token_firebase = '" . date('Y-m-d H:i:s') . "'"
                    . " where cpf = '".$cpf."'";
            error_log($sql);
            try {
                mysqli_query($conn, $sql);
                mysqli_close($conn);
            } catch (Exception $e1) {
//                $this->setErro(__CLASS__, __METHOD__, __LINE__, $e1->getMessage());
            }
        } catch (Exception $ex) {
//            $this->setErro(__CLASS__, __METHOD__, __LINE__, $ex->getMessage());
        }
    }

    /////////////////////////////////////////////
    //                  UPDATE                 //
    /////////////////////////////////////////////

    /**
     * Atesta que o usuário completou o cadastro
     * 
     * @author      Igor Maximo
     * @date        21/06/2020
     */
    public function setUpdateSeUsuarioCompletouCadastro($id) {
        try {
            $conexao = new ConexaoDAO();
            $conn = $conexao->conexao();
            mysqli_query($conn, "SET NAMES 'utf8'");
            // Gerador de PIN
            $sql = "update " . $this->NOME_TABELA . " set se_completou_cadastro = 1 where id = " . $id;

            error_log($sql);

            // Atesta que o cliente completou o cadastro dele
            if (mysqli_query($conn, $sql)) {
                return true;
            } else {
                return false;
            }
            mysqli_close($conn);
        } catch (Exception $ex) {
//            $this->setErro(__CLASS__, __METHOD__, __LINE__, $ex->getMessage());
        }
    }

}
