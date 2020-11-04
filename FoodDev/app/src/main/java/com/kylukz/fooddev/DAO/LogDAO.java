package com.kylukz.fooddev.DAO;

/**
 * Classe responsável por azemazenar
 * todos os erros no servidor
 *
 * @author      Igor Maximo <igormaximo_1989@hotmail.com>
 * @date        09/05/2020
 */
public abstract class LogDAO {

    /**
     * Método principal de cadastramento de erros no servidor
     *
     * @param classe
     * @param metodo
     * @param msg
     * @param linha
     * @param fkEmpresa
     * @param fkUsuario
     * @param fkVersionamento
     * @author      Igor Maximo <igormaximo_1989@hotmail.com>
     * @date        09/05/2020
     */
    public static void setErro(String classe, String metodo, String msg, int linha, int fkEmpresa, int fkUsuario, int fkVersionamento) {
        try {
            String descricao =
                    "Classe: " + classe + ", "
                            + "Método: " + metodo + ", "
                            + "Linha: " + linha + ", "
                            + "Msg: " + msg;

            // Chamada JSON Server

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+descricao + ", " + fkEmpresa+ ", " + fkUsuario+ ", " + fkVersionamento);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

