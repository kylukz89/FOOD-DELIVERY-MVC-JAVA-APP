<?php

/**
 * Contém todos os paramêtros do App
 * 
 */
class Parametros {
    
    /**
     * Consulta se o App está fechado para
     * o público, aberto apenas para manutenção
     * por login de admin
     * 
     * @author        Igor Maximo 
     * @date          13/04/2020
     */
    public static function consultaSeAppEstaEmManutencao() {
        try {
            return true;
        } catch (Exception $ex) {
            echo $ex;
        }
        return false;
    }
}