package com.kylukz.fooddev.Cart;

import com.kylukz.fooddev.JavaBeans.EntidadeCarrinhoPonto;
import com.kylukz.fooddev.JavaBeans.EntidadeProdutosGrupoEscolhido;

import java.util.ArrayList;

/**
 * Entidade de atributos exclusivos o carrinho
 *
 * @author      Igor Maximo <igormaximo_1989@hotmail.com>
 * @date        17/05/2020
 */
public abstract class AtributoCarrinho {
    // MODOS DE PAGAMENTO
    public volatile static int CREDITO_= 0;
    public volatile static int DEBITO_= 1;
    public volatile static int RECEBER_LOCAL_= 2;
    public volatile static int PAGAMENTO_MISTO = 3; // Quando a forma escolhida é metade crédito/débito e a outra metade é "Receber no local"
    // Entidade global do carrinho a ser manipulada
    public volatile static EntidadeCarrinhoPonto ENTIDADE_CARRINHO_PONTO = new EntidadeCarrinhoPonto();
    // Array que incrementa(add) com a entidade por trás de cada item do carrinho
    public volatile static ArrayList<EntidadeProdutosGrupoEscolhido> ENTIDADES_ITENS_ESCOLHIDOS_DO_CARRINHO = new ArrayList<EntidadeProdutosGrupoEscolhido>();
    // VALORES
    public volatile static double VALOR_TOTAL_CARRINHO = 0; // Valor total da venda/carrinho
    public volatile static double VALOR_TOTAL_CREDITO = 0; // Valor total quando crédito é escolhido
    public volatile static double VALOR_TOTAL_DEBITO = 0; // Valor total quando débito é escolhido
    public volatile static double VALOR_TOTAL_RECEBER_LOCAL = 0;// Valor total a receber no local
    // EXTRAS
    private volatile static int TOTAL_FORMAS_PAGAMENTO_USADAS_NA_VENDA = 0;// Total de formas de pagamento usadas na venda
}

