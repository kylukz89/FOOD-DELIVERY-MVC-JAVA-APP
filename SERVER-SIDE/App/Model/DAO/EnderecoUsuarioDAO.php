<?php

namespace App\Model\DAO;

use App\Model\DAO\ConexaoDAO;
use App\Model\Entidade\EntidadeUsuario;
use App\Model\Entidade\EntidadeEnderecoUsuario;

require_once '/home2/versa357/public_html/app/desenvolvimento/App/Model/DAO/ErroDAO.php';
require_once '/home2/versa357/public_html/app/desenvolvimento/App/Model/DAO/ConexaoDAO.php';
require_once '/home2/versa357/public_html/app/desenvolvimento/App/Model/Entidade/EntidadeUsuario.php';
require_once '/home2/versa357/public_html/app/desenvolvimento/App/Model/Entidade/EntidadeEnderecoUsuario.php';

/**
 * Entidade responsa´vel por gerenciar
 * os dados dos grupos de itens por empresa
 * 
 * @author      Igor Maximo
 * @date        31/05/2020
 */
class EnderecoUsuarioDAO /* extends ErroDAO */ {

    private $NOME_TABELA = "app_usuario_endereco";
    
        
    /////////////////////////////////////////////
    //                  SELECT                 //
    /////////////////////////////////////////////
    
     
    /**
     * Retorna se o cliente
     * completou o cadastro dele(a)
     * informando pelo menos 1 endereço
     * válido a primeira vez que logou no app
     * 
     * @author      Igor Maximo
     * @date        21/06/2020
     */
    public function getSeUsuarioCompletouCadastro($idUsuario) {
        try {
            $conexao = new ConexaoDAO();
            $conn = $conexao->conexao();
            mysqli_query($conn, "SET NAMES 'utf8'");

            $sql = "select fk_app_usuario from " . $this->NOME_TABELA ." where fk_app_usuario = " . $idUsuario;
            error_log($sql);
            $result = mysqli_fetch_assoc(mysqli_query($conn, $sql));
            return $result['fk_app_usuario'];
        } catch (Exception $ex) {
//            $this->setErro(__CLASS__, __METHOD__, __LINE__, $ex->getMessage());
        }
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
    public function setCompletarCadastrUsuario(EntidadeEnderecoUsuario $entidade) {
        try {
            $conexao = new ConexaoDAO();
            $conn = $conexao->conexao();

            mysqli_query($conn, "SET NAMES 'utf8'");

            $sql = "INSERT INTO " . $this->NOME_TABELA . " "
                    . "(fk_app_usuario, fk_cidade, fk_uf, nome, numero, bairro, cep, ponto_ref, data_cadastro, latitude, longitude)"
                    . " values "
                    . "("
                    . "" . $entidade->getFkUsuario() . ","
                    . "" . $entidade->getFkCidade() . ","
                    . "" . $entidade->getFkUf() . ","
                    . "'" . $entidade->getNomeCidade() . "',"
                    . "'" . $entidade->getNumero() . "',"
                    . "'" . $entidade->getBairro() . "',"
                    . "" . $entidade->getCep() . ","
                    . "'" . $entidade->getPontoRef() . "',"
                    . "'" . date('Y-m-d H:i:s') . "',"
                    . "'" . $entidade->getLatitude() . "',"
                    . "'" . $entidade->getLongitude() . "')";
            
            
            error_log($sql);
            
            
            
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
    }
    
    
    /////////////////////////////////////////////
    //                  UPDATE                 //
    /////////////////////////////////////////////
    
    /**
     * Realiza o cadastro do cliente
     * 
     * @author      Igor Maximo
     * @date        31/05/2020
     */
    public function setUpdateFkEnderecoCliente($id) {
        $retorno = [];
        try {
            $conexao = new ConexaoDAO();
            $conn = $conexao->conexao();

            mysqli_query($conn, "SET NAMES 'utf8'");

            // Gerador de PIN
            $pinGerado = intval($this->geraPIN());

            $sql = "ujpdate " . $this->NOME_TABELA . " "
                    . "(fk_endereco, nome, sobrenome, apelido, cpf, tipo_cliente, nascimento, celular, fone, email, pin_gerado, senha, facebook_login_social, facebook_nome, google_login, google_senha, se_motoboy, se_completou_cadastro, se_inativo, data_cadastro)"
                    . " values "
                    . "('" . $entidade->getNome() . "',"
                    . "'" . $entidade->getSobrenome() . "',"
                    . "'" . $entidade->getCpf() . "',"
                    . "'" . $entidade->getDataNascimento() . "',"
                    . "'" . $entidade->getCelular() . "',"
                    . "'" . $entidade->getEmail() . "',"
                    . "" . $pinGerado . ","
                    . "'" . $entidade->getSenha() . "')";
            try {
                // Verifica se o cliente existe primeiro
                if (!$this->getSeClienteExiste($entidade->getCpf())) {
                    if (mysqli_query($conn, $sql)) {
                        $retorno['status'] = 'true';
                        $retorno['msg'] = "Cadastrado! Aguarde o PIN para o e-mail: " . $entidade->getEmail();
                        $retorno['pin'] = $pinGerado;
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

    

}
