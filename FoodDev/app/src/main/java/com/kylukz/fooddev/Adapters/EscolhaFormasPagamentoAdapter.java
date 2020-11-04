package com.kylukz.fooddev.Adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.aide.aiDelivery.R;
import com.kylukz.fooddev.JavaBeans.EntidadePagamentoEscolhaModo;
import com.kylukz.fooddev.View.EmpresaActivity;

import java.util.List;


public class EscolhaFormasPagamentoAdapter extends RecyclerView.Adapter<EscolhaFormasPagamentoAdapter.EscolhaOpcoesPagamentoViewHolder> {


    private List<EntidadePagamentoEscolhaModo> opcoesPagamento;
    public static Context ctx;
    public static Object MODELO_NEGOCIO;
    public static int POSITION_CLICADA;
    private int pos;
    private EmpresaActivity empresaActivity = new EmpresaActivity();

    public EscolhaFormasPagamentoAdapter(List<EntidadePagamentoEscolhaModo> planoList) {
        this.opcoesPagamento = planoList;
    }


    View itemView;

    @Override
    public EscolhaFormasPagamentoAdapter.EscolhaOpcoesPagamentoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_etapa_um_pagamento, parent, false);
        return new EscolhaOpcoesPagamentoViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint({"ResourceAsColor", "SetTextI18n", "ClickableViewAccessibility"})
    @Override
    public void onBindViewHolder(final EscolhaOpcoesPagamentoViewHolder holder, final int position) {

        holder.buttonBotaoCredito.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(opcoesPagamento.get(position).getCtx(), "Crédito selecionado!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        holder.buttonBotaoDebito.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(opcoesPagamento.get(position).getCtx(), "Débito selecionado!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        holder.buttonBotaoPagarEntrega.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(opcoesPagamento.get(position).getCtx(), "Pagar no local selecionado!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });


    }


    @Override
    public int getItemCount() {
        return opcoesPagamento.size();
    }

    class EscolhaOpcoesPagamentoViewHolder extends RecyclerView.ViewHolder {
        LinearLayout buttonBotaoCredito;
        LinearLayout buttonBotaoDebito;
        LinearLayout buttonBotaoPagarEntrega;


        @SuppressLint("WrongViewCast")
        public EscolhaOpcoesPagamentoViewHolder(View view) {
            super(view);
            buttonBotaoCredito = (LinearLayout) view.findViewById(R.id.buttonBotaoCredito);
            buttonBotaoDebito = (LinearLayout) view.findViewById(R.id.buttonBotaoDebito);
            buttonBotaoPagarEntrega = (LinearLayout) view.findViewById(R.id.buttonBotaoPagarEntrega);
        }
    }


}
