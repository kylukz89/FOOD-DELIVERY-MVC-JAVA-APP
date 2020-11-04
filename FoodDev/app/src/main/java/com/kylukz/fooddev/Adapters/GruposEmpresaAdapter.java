package com.kylukz.fooddev.Adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.aide.aiDelivery.R;
import com.kylukz.fooddev.JavaBeans.EntidadeGrupoProdutosEmpresa;
import com.kylukz.fooddev.Toolbox.Ferramentas;
import com.kylukz.fooddev.Toolbox.VariaveisGlobais;
import com.kylukz.fooddev.View.EmpresaActivity;

import java.util.List;


public class GruposEmpresaAdapter extends RecyclerView.Adapter<GruposEmpresaAdapter.OpcoesPontoViewHolder> {

    private EmpresaActivity empresaActivity = new EmpresaActivity();
    private List<EntidadeGrupoProdutosEmpresa> gruposEmpresaList;
    public static Context ctx;
    private int pos;


    public GruposEmpresaAdapter(List<EntidadeGrupoProdutosEmpresa> planoList) {
        this.gruposEmpresaList = planoList;
    }

    @Override
    public GruposEmpresaAdapter.OpcoesPontoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_opcoes_ponto, parent, false);
        return new OpcoesPontoViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(OpcoesPontoViewHolder holder, final int position) {

      //  holder.setIsRecyclable(false);





        // Setando dados nos campos
        holder.textViewNomeOpcao.setText(Html.fromHtml("<span><b>"+ gruposEmpresaList.get(position).getNomeGrupo()+"</b></span>"));
        holder.imageViewOpcoesPonto.setImageBitmap(gruposEmpresaList.get(position).getImagem());
        // Sombreamento
        Ferramentas.setSombraTextView(holder.textViewNomeOpcao, 0, 0, VariaveisGlobais.ESPESSURA_BRILHO_OU_SOMBRA_TEXTOS, Color.BLACK);


        holder.cardOpcoesPonto.setPreventCornerOverlap(false);




        // CLICAR NA RECYCLERVIEW
        holder.cardOpcoesPonto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos  = position;
                System.out.println("GRU === > "+gruposEmpresaList.get(position).getId());
                System.out.println("GURPO === > " + EmpresaActivity.FK_GRUPO_PRODUTO_ESCOLHIDO + " >> "+gruposEmpresaList.get(position).getId());
                EmpresaActivity.FK_GRUPO_PRODUTO_ESCOLHIDO = gruposEmpresaList.get(position).getId();

                // Exibe a roleta de produtos da opções escolhida
//                pontoActivity.exibeColapsaBottomSheet(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return gruposEmpresaList.size();
    }

    public void setOnItemClickListener(EmpresaActivity empresaActivity) {

    }


    public static int FUNCAO;

    class OpcoesPontoViewHolder extends RecyclerView.ViewHolder {
        // Para função 0
        public TextView textViewNomeOpcao;
        public TextView textViewNomePonto;
        public ImageView imageViewOpcoesPonto;
        public CardView cardOpcoesPonto;

        // Para função 1

        @SuppressLint("WrongViewCast")
        public OpcoesPontoViewHolder(View view) {
            super(view);

            switch (FUNCAO) {
                case 0:
                    try {
                        textViewNomeOpcao = (TextView) view.findViewById(R.id.textViewNomeOpcao);
                        imageViewOpcoesPonto = (ImageView) view.findViewById(R.id.imageViewOpcoesPonto);
                        textViewNomePonto = (TextView) view.findViewById(R.id.textViewNomePonto);
                        cardOpcoesPonto = (CardView) view.findViewById(R.id.cardOpcoesPonto);
                    } catch (Exception e) {
                        System.err.println(e);
                    }
                    break;
                case 1:
                    break;
                case 2:
                    break;
            }

        }
    }






}
