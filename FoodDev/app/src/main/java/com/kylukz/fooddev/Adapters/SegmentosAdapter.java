package com.kylukz.fooddev.Adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.aide.aiDelivery.R;
import com.kylukz.fooddev.JavaBeans.EntidadeSegmento;
import com.kylukz.fooddev.View.MenuPrincipalEmpresa;

import java.util.List;

/**
 * Adaptador da recyclerview da tela de segmentos (grupos)
 *
 */
public class SegmentosAdapter extends RecyclerView.Adapter<SegmentosAdapter.PontoViewHolder> {


    private List<EntidadeSegmento> entidadeSegmentoList;
    public static Context ctx;
    private int pos;


    public SegmentosAdapter(List<EntidadeSegmento> planoList) {
        this.entidadeSegmentoList = planoList;
    }

    @Override
    public SegmentosAdapter.PontoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_opcoes_segmentos, parent, false);
        return new PontoViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(PontoViewHolder holder, final int position) {


//        DisplayMetrics displayMetrics =  opcoesPontoList.get(position).getCtx().getResources().getDisplayMetrics();



            holder.textViewNomeSegmento.setText(entidadeSegmentoList.get(position).getNome());
            holder.textViewNomeDescricaoSegmento.setText(entidadeSegmentoList.get(position).getDescricao());
            holder.imageViewSegmento.setImageResource(entidadeSegmentoList.get(position).getImagem());


        // CLICAR NA RECYCLERVIEW
        holder.imageViewSegmento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                pos  = position;
                Intent intent = new Intent(entidadeSegmentoList.get(position).getCtx(), MenuPrincipalEmpresa.class);
                entidadeSegmentoList.get(position).getCtx().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return entidadeSegmentoList.size();
    }


    class PontoViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewSegmento;
        private TextView textViewNomeSegmento;
        private TextView textViewNomeDescricaoSegmento;

        public PontoViewHolder(View view) {
            super(view);
            imageViewSegmento = (ImageView) view.findViewById(R.id.imageViewSegmento);
            textViewNomeSegmento = (TextView) view.findViewById(R.id.textViewNomeSegmento);
            textViewNomeDescricaoSegmento = (TextView) view.findViewById(R.id.textViewNomeDescricaoSegmento);

        }
    }






}
