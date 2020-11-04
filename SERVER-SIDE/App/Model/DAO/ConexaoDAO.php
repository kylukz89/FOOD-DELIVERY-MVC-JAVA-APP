<?php

namespace App\Model\DAO;

/**
 * Credenciais de acesso ao banco de dados
 * 
 * @author Igor Maximo
 * @data 23/12/2019
 */
class ConexaoDAO {
 
    
    // NCAREDEAPP API
    public static $SERVIDOR_IP = "localhost";
    public static $USUARIO = "versa357_app";
    public static $SENHA = "app2020";
    public static $BANCO_NOME = "versa357_app";
     
    /**
    * Abre conexão com banco de dados
    * 
    * @author Igor Maximo
    * data 23/12/2019
    */
    public function conexao() {
        $conn = mysqli_connect(ConexaoDAO::$SERVIDOR_IP, ConexaoDAO::$USUARIO, ConexaoDAO::$SENHA, ConexaoDAO::$BANCO_NOME) or die(mysqli_error());
        return $conn;
    }
    
     
}

?>
