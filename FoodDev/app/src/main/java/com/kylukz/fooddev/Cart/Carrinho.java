package com.kylukz.fooddev.Cart;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aide.aiDelivery.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.kylukz.fooddev.Adapters.ProdutoGrupoPizzaAdapter;
import com.kylukz.fooddev.JavaBeans.EntidadeProdutosGrupoEscolhido;
import com.kylukz.fooddev.View.EmpresaActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Interface responsável por montar
 * o carrinho de compras do cliente
 * e é usado para somar a comanda
 *
 * @author Igor Maximo <igormaximo_1989@hotmail.com>
 * @date 14/05/2020
 */
public class Carrinho extends AtributoCarrinho {

    private EmpresaActivity empresaActivity = new EmpresaActivity();

//    private ArrayList<EntidadeProdutosGrupoEscolhido> carrinho = new ArrayList<EntidadeProdutosGrupoEscolhido>();

    /**
     * Set adiciona item escolhido no carrinho, porém verifica
     * se há etapas diferentes neste ramo de negócio, na boolean
     * seGrupoPizza, configura o ambiente para o total
     * de etapas com a totalEtapasPeronalizadas e percorre o HashMap
     * das etapas para configurar e ajustar o layout de acordo com o
     * ramo de negócio parametrizado no banco
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 22/04/2020
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setAddItemCarrinho(final int position, Context context, final EntidadeProdutosGrupoEscolhido entidadeItemEscolhido, int modeloNegocio, boolean seGrupoPizza, HashMap<Integer, ArrayList> itensAdicionais) {
        AlertDialog.Builder builder = new AlertDialog.Builder(entidadeItemEscolhido.getCtx(), R.style.CardInflateTheme);
        LayoutInflater inflater = (LayoutInflater) entidadeItemEscolhido.getCtx().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.card_add_carrinho_padrao, null);
        final EditText textViewObs = (EditText) view.findViewById(R.id.textViewObs);
        TextView textViewMsg = (TextView) view.findViewById(R.id.textViewMsg);
        builder.setView(view);
        Dialog dialogAddCarrinho = builder.create();
        final Dialog finalDialogAddCarrinho = dialogAddCarrinho;
        ///////////////////////////////////
        //       DEFINIÇÃO LAYOUT        //
        ///////////////////////////////////
        // Se for grupo pizza, infla layout personalizado para pizzas
        if (seGrupoPizza) {
            // Layout para seleção de pizza
            view = inflater.inflate(R.layout.card_add_carrinho_pizza, null);
            builder.setView(view);
            dialogAddCarrinho = builder.create();
            // Pizza 1,2,3 ou 4 pedaços
            ImageView imageViewPizza1 = (ImageView) view.findViewById(R.id.imageViewPizza1);
            ImageView imageViewPizza2 = (ImageView) view.findViewById(R.id.imageViewPizza2);
            ImageView imageViewPizza3 = (ImageView) view.findViewById(R.id.imageViewPizza3);
            ImageView imageViewPizza4 = (ImageView) view.findViewById(R.id.imageViewPizza4);


            //Eventos

            // 1 pedaço
            final Dialog finalDialogAddCarrinho1 = dialogAddCarrinho;
            imageViewPizza1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Segue add carrinho
                    try {
                        Thread.sleep(700);
                        // Set Observação
                        entidadeItemEscolhido.setObservacao(textViewObs.getText().toString());
                        // Seta a entidade do respectivo produto escolhido
                        ENTIDADES_ITENS_ESCOLHIDOS_DO_CARRINHO.add(entidadeItemEscolhido);
                        // Soma o valor do produto escolhido ao valor total do carrinho
                        setSomarItemCarrinho(entidadeItemEscolhido.getValor());
                        // Msg para o usuário
                        empresaActivity.geraPopUpAlertaMsg((entidadeItemEscolhido.getNomeProduto() + " adicionado(a) ao carrinho!"), entidadeItemEscolhido.getCtx(), R.layout.toast_msg_sucesso);
                        // Carrega roleta de botões de opções gerais
                        ((EmpresaActivity) entidadeItemEscolhido.getCtx()).setCarregaRecyclerViewRoletaBotoesMenuPrincipal();
                        // Esconde bottomsheet
                        ((EmpresaActivity) entidadeItemEscolhido.getCtx()).setExpandeColapsaBottomSheet(BottomSheetBehavior.STATE_COLLAPSED);
                        finalDialogAddCarrinho1.dismiss();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            // 2 pedaço
            imageViewPizza2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Escolher dois sabores
                    int totalSaboresPodemSerSelecionados = 1; //  Total de sabores que podem ser escolhidos
                    finalDialogAddCarrinho.dismiss();
                    // Layout para seleção de pizza
                    AlertDialog.Builder builder = new AlertDialog.Builder(entidadeItemEscolhido.getCtx(), R.style.CardInflateTheme);
                    LayoutInflater inflater = (LayoutInflater) entidadeItemEscolhido.getCtx().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = inflater.inflate(R.layout.card_add_carrinho_pizza_sabor, null);
                    builder.setView(view);
                    Dialog dialogAddCarrinho = builder.create();
                    final Dialog finalDialogAddCarrinho = dialogAddCarrinho;
                    // Exibe o layout
                    dialogAddCarrinho.show();
                    // RecyclerView sabores de pizza
                    // Variável recyclerViewProdutosGrupo em uso para o Carrinho
                    ((EmpresaActivity) entidadeItemEscolhido.getCtx()).setRecyclerView((RecyclerView) view.findViewById(R.id.recyclerViewProdutosGrupoPizzaEscolherSabores), 2, 0, new ProdutoGrupoPizzaAdapter(EmpresaActivity.listaEntidadeProdutosGrupoEscolhido, totalSaboresPodemSerSelecionados), 1, false, LinearLayoutManager.VERTICAL, false);
                    // S

                }
            });
            // 3 pedaço
            imageViewPizza3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Escolher três sabores
                    int totalSaboresPodemSerSelecionados = 2; //  Total de sabores que podem ser escolhidos
                }
            });
            // 4 pedaço
            imageViewPizza4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Escolher quatro sabores
                    int totalSaboresPodemSerSelecionados = 3; //  Total de sabores que podem ser escolhidos
                }
            });


            //Objects.requireNonNull(d.getWindow()).setBackgroundDrawableResource(R.drawable.border_card_add_carrinho);
        } else {
            // Layout simples para produto sem etapa
            textViewMsg.setText("Deseja adicionar " + entidadeItemEscolhido.getNomeProduto() + " ao carrinho?");
            Objects.requireNonNull(dialogAddCarrinho.getWindow()).setBackgroundDrawableResource(R.drawable.border_card_add_carrinho);

            LinearLayout buttonBotaoConfirmarAddCarrinho = (LinearLayout) view.findViewById(R.id.buttonBotaoConfirmarAddCarrinho);

            buttonBotaoConfirmarAddCarrinho.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalDialogAddCarrinho.dismiss();
                    try {
                        Thread.sleep(700);
                        // Set Observação
                        entidadeItemEscolhido.setObservacao(textViewObs.getText().toString());
                        // Seta a entidade do respectivo produto escolhido
                        ENTIDADES_ITENS_ESCOLHIDOS_DO_CARRINHO.add(entidadeItemEscolhido);
                        // Soma o valor do produto escolhido ao valor total do carrinho
                        setSomarItemCarrinho(entidadeItemEscolhido.getValor());
                        // Msg para o usuário
                        empresaActivity.geraPopUpAlertaMsg((entidadeItemEscolhido.getNomeProduto() + " adicionado(a) ao carrinho!"), entidadeItemEscolhido.getCtx(), R.layout.toast_msg_sucesso);
                        // Carrega roleta de botões de opções gerais
                        ((EmpresaActivity) entidadeItemEscolhido.getCtx()).setCarregaRecyclerViewRoletaBotoesMenuPrincipal();
                        // Esconde bottomsheet
                        ((EmpresaActivity) entidadeItemEscolhido.getCtx()).setExpandeColapsaBottomSheet(BottomSheetBehavior.STATE_COLLAPSED);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        // Exibe o layout
        dialogAddCarrinho.show();
    }

    /**
     * Set adiciona o item no carrinho
     *
     * @author      Igor Maximo <igormaximo_1989@hotmail.com>
     * @date        17/07/2020
     */
    public void setAddCarrinhoPizza(String obs, EntidadeProdutosGrupoEscolhido entidadeItemEscolhido) {
        try {
            Thread.sleep(700);
            // Set Observação
            entidadeItemEscolhido.setObservacao(obs);
            // Seta a entidade do respectivo produto escolhido
            ENTIDADES_ITENS_ESCOLHIDOS_DO_CARRINHO.add(entidadeItemEscolhido);
            // Soma o valor do produto escolhido ao valor total do carrinho
            setSomarItemCarrinho(entidadeItemEscolhido.getValor());
            // Msg para o usuário
            empresaActivity.geraPopUpAlertaMsg((entidadeItemEscolhido.getNomeProduto() + " adicionado(a) ao carrinho!"), entidadeItemEscolhido.getCtx(), R.layout.toast_msg_sucesso);
            // Carrega roleta de botões de opções gerais
            ((EmpresaActivity) entidadeItemEscolhido.getCtx()).setCarregaRecyclerViewRoletaBotoesMenuPrincipal();
            // Esconde bottomsheet
            ((EmpresaActivity) entidadeItemEscolhido.getCtx()).setExpandeColapsaBottomSheet(BottomSheetBehavior.STATE_COLLAPSED);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set remover item escolhido do carrinho
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 17/05/2020
     */
    public void setRemoveItemCarrinho(final int position, Context ctx, String produto) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx, R.style.CardInflateTheme);
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.card_fechar_empresa, null);
        TextView textViewMsg = (TextView) view.findViewById(R.id.textViewMsg);
        textViewMsg.setText("Deseja realmente remover " + produto + " do carrinho?");

        builder.setView(view);
        final Dialog dialogAddCarrinho = builder.create();

        LinearLayout buttonBotaoDescartarCarrinhoESair = (LinearLayout) view.findViewById(R.id.buttonBotaoDescartarCarrinhoESair);
        buttonBotaoDescartarCarrinhoESair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddCarrinho.dismiss();
                // Soma o valor do produto escolhido ao valor total do carrinho
                setSubtrairItemCarrinho(ENTIDADES_ITENS_ESCOLHIDOS_DO_CARRINHO.get(position).getValor());
                // Seta a entidade do respectivio produto escolhido
                ENTIDADES_ITENS_ESCOLHIDOS_DO_CARRINHO.remove(position);
                // Recarrega roleta dos botões do menu principal
                ((EmpresaActivity) EmpresaActivity.CTX).setCarregaRecyclerViewRoletaBotoesMenuPrincipal();
                // Recarrega lista de itens da comanda (carrinho)
                ((EmpresaActivity) EmpresaActivity.CTX).setCarregaRecyclerViewComanda();
            }
        });
        // Exibe o layout
        dialogAddCarrinho.show();
    }


    /**
     * Retorna uma entidade de um item específico
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 17/05/2020
     */
    public Object getItemCarrinho(int position) {
        return ENTIDADES_ITENS_ESCOLHIDOS_DO_CARRINHO.get(position);
    }


    /**
     * Retorna todas as entidades
     * de seus respectivios itens
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 17/05/2020
     */
    public ArrayList<EntidadeProdutosGrupoEscolhido> getCarrinho() {
        return ENTIDADES_ITENS_ESCOLHIDOS_DO_CARRINHO;
    }

    /**
     * Set somar valor item escolhido do carrinho
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 17/05/2020
     */
    protected void setSomarItemCarrinho(double valorItem) {
        VALOR_TOTAL_CARRINHO += valorItem;
        System.out.println("VALOR CARRINHO + =====> " + VALOR_TOTAL_CARRINHO);
        ((EmpresaActivity) EmpresaActivity.CTX).setValoresComanda();
    }

    /**
     * Set remover item escolhido do carrinho
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 17/05/2020
     */
    protected void setSubtrairItemCarrinho(double valorItem) {
        VALOR_TOTAL_CARRINHO -= valorItem;
        System.out.println("VALOR CARRINHO - =====> " + VALOR_TOTAL_CARRINHO);
        ((EmpresaActivity) EmpresaActivity.CTX).setValoresComanda();
    }

}


