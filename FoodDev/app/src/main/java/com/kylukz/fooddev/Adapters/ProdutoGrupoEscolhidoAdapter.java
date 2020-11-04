package com.kylukz.fooddev.Adapters;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.aide.aiDelivery.R;
import com.kylukz.fooddev.JavaBeans.EntidadeProdutosGrupoEscolhido;
import com.kylukz.fooddev.Cart.Carrinho;
import com.kylukz.fooddev.Toolbox.Ferramentas;
import com.kylukz.fooddev.Toolbox.VariaveisGlobais;
import com.kylukz.fooddev.View.EmpresaActivity;

import java.util.List;


/**
 * Classe responsável pelos montar e manipular
 * a recyclerview dos produtos de um grupo de escolhido
 *
 * @author  Igor Maximo <igormaximo_1989@hotmail.com>
 * @date    19/05/2020
 */
public class ProdutoGrupoEscolhidoAdapter extends RecyclerView.Adapter<ProdutoGrupoEscolhidoAdapter.ProdutoGrupoEscolhidoViewHolder> {


    private List<EntidadeProdutosGrupoEscolhido> produtosGrupoEscolhidoList;
    public static Object MODELO_NEGOCIO;
    public static int POSITION_CLICADA;
    private int pos;
    private EmpresaActivity empresaActivity = new EmpresaActivity();
    private Carrinho carrinho = new Carrinho();


    public ProdutoGrupoEscolhidoAdapter(List<EntidadeProdutosGrupoEscolhido> planoList) {
        this.produtosGrupoEscolhidoList = planoList;
    }

    @Override
    public ProdutoGrupoEscolhidoAdapter.ProdutoGrupoEscolhidoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_opcoes_ponto_produto, parent, false);
        return new ProdutoGrupoEscolhidoViewHolder(itemView);
    }

;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint({"ResourceAsColor", "SetTextI18n", "ClickableViewAccessibility"})
    @Override
    public void onBindViewHolder(final ProdutoGrupoEscolhidoViewHolder holder, final int position) {

        // Setando dados nos campos
        holder.textViewNomeOpcao.setText(produtosGrupoEscolhidoList.get(position).getNomeProduto());
        holder.textViewValorProduto.setText("R$ "+(Ferramentas.setArredondaValorMoedaReal(String.valueOf(produtosGrupoEscolhidoList.get(position).getValor())) ).replace(".", ","));

        holder.imageViewOpcaoProduto.setImageBitmap(produtosGrupoEscolhidoList.get(position).getImagemProduto());
        // Sombreamento
        Ferramentas.setSombraTextView(holder.textViewNomeOpcao, 0, 0, VariaveisGlobais.ESPESSURA_BRILHO_OU_SOMBRA_TEXTOS, Color.BLACK);
        Ferramentas.setSombraTextView(holder.textViewValorProduto, 0, 0, 10.5f, 0xFFEA6262);
        Ferramentas.setSombraTextView(holder.textViewQuantidade, 0, 0, 10f, Color.GRAY);
        Ferramentas.setSombraTextView(holder.buttonInfo, 0, 0, 10.0f, Color.BLACK);

        // Exibir os detalhes do produto
        holder.textViewBotaoDetalhesProduto.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                empresaActivity.setGeraDialogDetalhesProdutoEscolhido(produtosGrupoEscolhidoList.get(position).getCtx(), produtosGrupoEscolhidoList.get(position).getDescricao());
                return false;
            }
        });

        // Adicionar produto ao carrinho
        holder.buttonBotaoAddCarrinho.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Adiciona o item escolhido no carrinho
                carrinho.setAddItemCarrinho(position, produtosGrupoEscolhidoList.get(position).getCtx(), produtosGrupoEscolhidoList.get(position), 0, produtosGrupoEscolhidoList.get(position).isSeTemEtapasPersonalizadas(), produtosGrupoEscolhidoList.get(position).getItensAdicionais());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return produtosGrupoEscolhidoList.size();
    }

    class ProdutoGrupoEscolhidoViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewNomeOpcao;
        public TextView textViewValorProduto;
        public TextView buttonInfo;
        public TextView textViewQuantidade;
        public TextView textViewBotaoDetalhesProduto;
        public TextView textViewDetalheProdutoDescricao;
        public ImageView imageViewOpcaoProduto;
        public LinearLayout buttonBotaoAddCarrinho;

        public ProdutoGrupoEscolhidoViewHolder(View view) {
            super(view);
            textViewNomeOpcao = (TextView) view.findViewById(R.id.textViewNomeOpcao);
            textViewValorProduto = (TextView) view.findViewById(R.id.textViewValorProduto);
            textViewBotaoDetalhesProduto = (TextView) view.findViewById(R.id.textViewBotaoDetalhesProduto);
            textViewDetalheProdutoDescricao = (TextView) view.findViewById(R.id.textViewDetalheProdutoDescricao);
            buttonBotaoAddCarrinho = (LinearLayout) view.findViewById(R.id.buttonBotaoAddCarrinho);
            imageViewOpcaoProduto = (ImageView) view.findViewById(R.id.imageViewOpcaoProduto);


        }

    }


}
