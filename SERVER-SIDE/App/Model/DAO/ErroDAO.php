<?php


require_once '/home2/versa357/public_html/app/desenvolvimento/App/Model/DAO/ConexaoDAO.php';

abstract class ErroDAO {
    public function setErro($classe, $metodo, $linha, $msgErro) {
        
    }
}