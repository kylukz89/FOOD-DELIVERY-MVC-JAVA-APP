package com.kylukz.fooddev.Toolbox;

import com.kylukz.fooddev.DAO.ParametroDAO;

/**
 * Parametros Globais
 * Armazena IP do servidor e demais variáveis diversas
 *
 * @author  Igor Maximo <igormaximo_1989@hotmail.com>
 */
abstract class Parametros {
    private static ParametroDAO parametroDAO = new ParametroDAO();
    // Pagamentos
    public static boolean SE_PAGAMENTO_LIBERADO_CREDITO = parametroDAO.getSeAppLiberadoPagamento(0);
    public static boolean SE_PAGAMENTO_LIBERADO_DEBITO = parametroDAO.getSeAppLiberadoPagamento(1);
    public static boolean SE_PAGAMENTO_LIBERADO_DINHEIRO = parametroDAO.getSeAppLiberadoPagamento(2);
    //
}
