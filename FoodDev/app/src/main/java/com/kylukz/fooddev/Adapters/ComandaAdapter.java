package com.kylukz.fooddev.Adapters;


import android.annotation.SuppressLint;
import android.content.Context;
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
import com.kylukz.fooddev.Cart.AtributoCarrinho;
import com.kylukz.fooddev.Cart.Carrinho;
import com.kylukz.fooddev.JavaBeans.EntidadeProdutosGrupoEscolhido;
import com.kylukz.fooddev.Toolbox.Ferramentas;
import com.kylukz.fooddev.View.EmpresaActivity;

import java.util.List;


public class ComandaAdapter extends RecyclerView.Adapter<ComandaAdapter.PontoViewHolder> {


    private List<EntidadeProdutosGrupoEscolhido> itensCarrinhoList;
    public static Context ctx;
    private int pos;
    private Carrinho carrinho = new Carrinho();

    public ComandaAdapter() {
        this.itensCarrinhoList = AtributoCarrinho.ENTIDADES_ITENS_ESCOLHIDOS_DO_CARRINHO;
    }

    @Override
    public ComandaAdapter.PontoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_comanda, parent, false);
        return new PontoViewHolder(itemView);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint({"ResourceAsColor", "SetTextI18n", "ClickableViewAccessibility"})
    @Override
    public void onBindViewHolder(PontoViewHolder holder, final int position) {

        // Setando valores nos componentes
        holder.textViewComandaNomeProduto.setText(itensCarrinhoList.get(position).getNomeProduto());
        if (itensCarrinhoList.get(position).getDescricao().length() >= 90) {
            holder.textViewTituloBreveDescricaoComanda.setText(itensCarrinhoList.get(position).getDescricao().substring(0, 90) + "...");
        } else {
            holder.textViewTituloBreveDescricaoComanda.setText(itensCarrinhoList.get(position).getDescricao());
        }
        holder.textViewValorProdutoComanda.setText(Ferramentas.setArredondaValorMoedaReal(String.valueOf(itensCarrinhoList.get(position).getValor())));
        holder.textViewNomeFachadaProduto.setText(itensCarrinhoList.get(position).getNomeProduto());
        holder.imageViewOpcaoProdutoComanda.setImageBitmap(itensCarrinhoList.get(position).getImagemProduto());

        holder.buttonBotaoRemoverCarrinho.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Pergunta se quer remover o item do carrinho
                carrinho.setRemoveItemCarrinho(position, EmpresaActivity.CTX, itensCarrinhoList.get(position).getNomeProduto());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return itensCarrinhoList.size();
    }


    class PontoViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewComandaNomeProduto;
        public TextView textViewTituloBreveDescricaoComanda;
        public TextView textViewValorProdutoComanda;
        public TextView textViewNomeFachadaProduto;
        public LinearLayout buttonBotaoRemoverCarrinho;
        public ImageView imageViewOpcaoProdutoComanda;

        public PontoViewHolder(View view) {
            super(view);

            textViewComandaNomeProduto = (TextView) view.findViewById(R.id.textViewComandaNomeProduto);
            textViewTituloBreveDescricaoComanda = (TextView) view.findViewById(R.id.textViewTituloBreveDescricaoComanda);
            textViewValorProdutoComanda = (TextView) view.findViewById(R.id.textViewValorProdutoComanda);
            textViewNomeFachadaProduto = (TextView) view.findViewById(R.id.textViewNomeFachadaProduto);
            imageViewOpcaoProdutoComanda = (ImageView) view.findViewById(R.id.imageViewOpcaoProdutoComanda);
            buttonBotaoRemoverCarrinho = (LinearLayout) view.findViewById(R.id.buttonBotaoRemoverCarrinho);
        }
    }






}
