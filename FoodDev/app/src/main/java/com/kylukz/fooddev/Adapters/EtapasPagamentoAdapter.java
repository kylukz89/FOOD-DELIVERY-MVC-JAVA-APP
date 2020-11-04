package com.kylukz.fooddev.Adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.aide.aiDelivery.R;
import com.kylukz.fooddev.JavaBeans.EntidadePagamentoBandeirasCartao;
import com.kylukz.fooddev.JavaBeans.EntidadePagamentoCartaoCredito;
import com.kylukz.fooddev.JavaBeans.EntidadePagamentoEscolhaModo;
import com.kylukz.fooddev.Toolbox.Ferramentas;

import java.util.List;


public class EtapasPagamentoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Ferramentas ferramentas = new Ferramentas();
    private static final int LAYOUT_ONE = 0;
    private static final int LAYOUT_TWO = 1;
    private static final int LAYOUT_THREE = 2;

    private List<EntidadePagamentoEscolhaModo> etapasList;
    private List<EntidadePagamentoBandeirasCartao> bandeiraList;
    List<EntidadePagamentoCartaoCredito> creditoList;
    public static Context ctx;
    private int pos;

    private String[] mDataset;

    public String[] getDataSet() {
        return mDataset;
    }

    public EtapasPagamentoAdapter(List<EntidadePagamentoEscolhaModo> comandaList, List<EntidadePagamentoBandeirasCartao> bandeiraList, List<EntidadePagamentoCartaoCredito> creditoList) {
        this.etapasList = comandaList;
        this.bandeiraList = bandeiraList;
        this.creditoList = creditoList;
        mDataset = new String[3]; // ou 3
    }

    int viewType;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.viewType = viewType;
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case LAYOUT_ONE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_etapa_um_pagamento, parent, false);

                viewHolder = new PontoViewHolder(view);
                break;
            case LAYOUT_TWO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_etapa_bandeira_cartao, parent, false);
                viewHolder = new PontoViewHolderBandeiras(view);
                break;
            case LAYOUT_THREE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_etapa_credito, parent, false);
                viewHolder = new PontoViewHolderDadosCartao(view);
                break;
        }
        return viewHolder;
    }


    @Override
    public long getItemId(int position) {

        return super.getItemId(position);

    }
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {




//        PontoActivity.CTX.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MASK_ADJUST);

        switch (holder.getItemViewType()) {
            case 0:
                PontoViewHolder vh1 = (PontoViewHolder) holder;
                break;
            case 1:
                PontoViewHolderBandeiras vh2 = (PontoViewHolderBandeiras) holder;
                break;
            case 2:
                final PontoViewHolderDadosCartao vh3 = (PontoViewHolderDadosCartao) holder;

//                vh3.textViewInfoCardDadosCartao.setText(String.valueOf(creditoList.get(position)));

                Ferramentas.setSombraTextView(vh3.textViewInfoCardDadosCartao, 0, 0, 8.5f, Color.LTGRAY);
                Ferramentas.setSombraTextView(vh3.textViewTotalCartao, 0, 0, 5.5f, Color.LTGRAY);
                Ferramentas.setSombraTextView(vh3.textViewValorCarrinho, 0, 0, 4.5f, 0xFFEA6262);


                 break;
        }


    }




    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {

        switch (holder.getItemViewType()) {
            case 0:
                PontoViewHolder vh1 = (PontoViewHolder) holder;
                break;
            case 1:
                PontoViewHolderBandeiras vh2 = (PontoViewHolderBandeiras) holder;
                break;
            case 2:
                final PontoViewHolderDadosCartao vh3 = (PontoViewHolderDadosCartao) holder;

                break;
        }
    }



    @Override
    public int getItemCount() {
        return 3; // Constante do total de etapas
    }


    @Override
    public int getItemViewType(int position) {

        switch (position) {
            case 0:
                return LAYOUT_ONE;
            case 1:
                return LAYOUT_TWO;
            case 2:
                return LAYOUT_THREE;
        }
        return -1;
    }

    /****************  VIEW HOLDER MODO PAGAMENTO ******************/
    class PontoViewHolder extends RecyclerView.ViewHolder {
        public PontoViewHolder(View view) {
            super(view);
        }
    }

    /****************  VIEW HOLDER ESCOLHA BANDEIRA ******************/
    class PontoViewHolderBandeiras extends RecyclerView.ViewHolder {
        public PontoViewHolderBandeiras(View view) {
            super(view);
        }
    }

    /****************  VIEW HOLDER DADOS CARTÃO ******************/
    class PontoViewHolderDadosCartao extends RecyclerView.ViewHolder {
        EditText editTextNumeroCartao;
        EditText editTextCVV;

        TextView textViewValorCarrinho;
        TextView textViewInfoCardDadosCartao;
        TextView textViewTotalCartao;

        public PontoViewHolderDadosCartao(View view) {
            super(view);
            editTextNumeroCartao = (EditText) view.findViewById(R.id.editTextNumeroCartao);
            textViewValorCarrinho = (TextView) view.findViewById(R.id.textViewValorCarrinho);
            textViewInfoCardDadosCartao = (TextView) view.findViewById(R.id.textViewInfoCardDadosCartao);
            textViewTotalCartao = (TextView) view.findViewById(R.id.textViewTotalCartao);
            editTextCVV = (EditText) view.findViewById(R.id.editTextCVV);


        }
    }




}
