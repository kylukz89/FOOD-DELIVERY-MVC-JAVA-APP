package com.kylukz.fooddev.Adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.aide.aiDelivery.R;
import com.kylukz.fooddev.Cart.AtributoCarrinho;
import com.kylukz.fooddev.JavaBeans.EntidadeBotoesMenuPonto;
import com.kylukz.fooddev.Toolbox.Ferramentas;

import java.util.ArrayList;
import java.util.List;


public class BotoesMenuPontoAdapter extends RecyclerView.Adapter<BotoesMenuPontoAdapter.OpcoesPontoViewHolder> {

    private List<EntidadeBotoesMenuPonto> botoesMenuPontos;
    public static Context ctx;
    private int pos;


    public BotoesMenuPontoAdapter(List<EntidadeBotoesMenuPonto> planoList) {
        this.botoesMenuPontos = planoList;
    }

    ArrayList<Integer> arrayListQtds;

    @Override
    public BotoesMenuPontoAdapter.OpcoesPontoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_botao_menu_ponto, parent, false);
        return new OpcoesPontoViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    @Override
    public void onBindViewHolder(final OpcoesPontoViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        holder.textViewNomeBotao.setText(botoesMenuPontos.get(position).getNomeBotao());
        holder.imageViewOpcaoProduto.setImageResource(botoesMenuPontos.get(position).getImagemBotao());

        if (AtributoCarrinho.ENTIDADES_ITENS_ESCOLHIDOS_DO_CARRINHO.size() != 0 && position == 2) {
            holder.textViewFlagNumeroItensCarrinho.setText(((AtributoCarrinho.ENTIDADES_ITENS_ESCOLHIDOS_DO_CARRINHO.size() - 1) + 1) + "");
            // Animação
            Ferramentas.setHeartPulseAnimation((TextView) holder.textViewFlagNumeroItensCarrinho, 1.3f);
        }

        holder.imageViewIconeFlag.setImageResource(botoesMenuPontos.get(position).getImagemFlagContemItens());
//        holder.cardViewBotao.setOnTouchListener(new View.OnTouchListener() {
//            @SuppressLint("ClickableViewAccessibility")
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (position == 0) {
//                    setDescartarCarrinho(botoesMenuPontos.get(position).getCtx());
//                }
//                return false;
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return botoesMenuPontos.size();
    }

    class OpcoesPontoViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewNomeBotao;
        public ImageView imageViewOpcaoProduto;
        public CardView cardViewBotao;
        public TextView textViewFlagNumeroItensCarrinho;
        public ImageView imageViewIconeFlag;

        @SuppressLint("WrongViewCast")
        public OpcoesPontoViewHolder(View view) {
            super(view);
            textViewNomeBotao = (TextView) view.findViewById(R.id.textViewNomeBotao);
            imageViewOpcaoProduto = (ImageView) view.findViewById(R.id.imageViewImagemBotao);
            cardViewBotao = (CardView) view.findViewById(R.id.cardBotaoMenuPonto);
            imageViewIconeFlag = (ImageView) view.findViewById(R.id.imageViewIconeFlag);
            textViewFlagNumeroItensCarrinho = (TextView) view.findViewById(R.id.textViewFlagNumeroItensCarrinho);
        }
    }
}