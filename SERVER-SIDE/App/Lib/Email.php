<?php

namespace App\Lib;

use App\Model\Entidade\EntidadeUsuario;


/**
 * Classe responsável por disparar
 * e-mails em geral
 * 
 * @author      Igor Maximo
 * @date         18/06/2020
 */
class Email {
 
    /**
     * Método que dispara e-mail com as credenciais de 
     * acesso do usário por PIN
     * 
     * @author      Igor Maximo
     * @date         18/06/2020
     */
    public function setDispararEmailNovoCadastroPIN(EntidadeUsuario $entidade, $pin) {
        
        try {
            // É necessário indicar que o formato do e-mail é html
            $headers = "MIME-Version: 1.1\r\n";
            $headers .= "Content-type: text/html; charset=UTF-8\r\n";
            $headers .= "From: " . "igormaximo_1989@hotmail.com" . "\r\n"; // remetente
            $headers .= "Return-Path: " . trim($entidade->getEmail()) . "\r\n"; // destino
            // Msg
            $msg = "Prezado(a), " . $entidade->getNome() . " " . $entidade->getSobrenome() . "," . "<p>";
            $msg.= "Segue, abaixo, a senha do App JáEntrego." . "<p>";
            $msg.= " " . "<p>";
            $msg.= "<b>PIN:</b>   " . $pin . "<p>";
            $msg.= "<b>Senha:</b> " . $entidade->getSenha() . "<p>";
            $msg.= " " . "<p>";
            $msg.= "Agradecemos a sua preferência e bom apetite!" . "<p>";
            $msg.= " " . "<p>";
            $msg.= "Atenciosamente," . "<p>";
            $msg.= "Equipe JáEntrego!" . "<p>";
            // Fire!
            $email = mail(trim($entidade->getEmail()), "Senha do JáEntrego", $msg, $headers);
        } catch (Exception $ex) {
            error_log($ex);
        }
    }
}