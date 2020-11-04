package com.kylukz.fooddev.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.aide.aiDelivery.R;
import com.kylukz.fooddev.Adapters.SegmentosAdapter;
import com.kylukz.fooddev.Animation.Animatoo;
import com.kylukz.fooddev.JavaBeans.EntidadeSegmento;

import java.util.ArrayList;
import java.util.List;

public class SegmentoActivity extends AppCompatActivity {

    private static SegmentoActivity CTX;

    private List<EntidadeSegmento> entidadeSegmentos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segmento);


        SegmentoActivity.CTX = this;


        Animatoo.animateSwipeRight(this);

        EntidadeSegmento entidadeSegmento = new EntidadeSegmento
                        (0,
                        Integer.parseInt(String.valueOf(R.drawable.card_mercado)),
                        "Mercados & Afins",
                        "Rápido e prático, como tem que ser",
                                new Intent(this, MenuPrincipalEmpresa.class),
                        this);

        entidadeSegmentos.add(entidadeSegmento);

        entidadeSegmento = new EntidadeSegmento
                (0,
                        Integer.parseInt(String.valueOf(R.drawable.segmento_restaurantes)),
                        "Restaurantes",
                        "Seus produtos favoritos em minutos!",
                        new Intent(this, MenuPrincipalEmpresa.class),
                        this);

        entidadeSegmentos.add(entidadeSegmento);

        entidadeSegmento = new EntidadeSegmento
                (0,
                        Integer.parseInt(String.valueOf(R.drawable.segmento_moda)),
                        "Roupas & Moda",
                        "Suas roupas entregues rapidamente!",
                        new Intent(this, MenuPrincipalEmpresa.class),
                        this);

        entidadeSegmentos.add(entidadeSegmento);

        entidadeSegmento = new EntidadeSegmento
                (0,
                        Integer.parseInt(String.valueOf(R.drawable.segmento_pecas)),
                        "Automotivo",
                        "Suas peças na hora que você precisa!",
                        new Intent(this, MenuPrincipalEmpresa.class),
                        this);

        entidadeSegmentos.add(entidadeSegmento);

        entidadeSegmento = new EntidadeSegmento
                (0,
                        Integer.parseInt(String.valueOf(R.drawable.pizzas2)),
                        "nome",
                        "descricao",
                        new Intent(this, MenuPrincipalEmpresa.class),
                        this);

        entidadeSegmentos.add(entidadeSegmento);

        entidadeSegmento = new EntidadeSegmento
                (0,
                        Integer.parseInt(String.valueOf(R.drawable.pizzas2)),
                        "nome",
                        "descricao",
                        new Intent(this, MenuPrincipalEmpresa.class),
                        this);

        entidadeSegmentos.add(entidadeSegmento);

        entidadeSegmento = new EntidadeSegmento
                (0,
                        Integer.parseInt(String.valueOf(R.drawable.pizzas2)),
                        "nome",
                        "descricao",
                        new Intent(this, MenuPrincipalEmpresa.class),
                        this);

        entidadeSegmentos.add(entidadeSegmento);

        entidadeSegmento = new EntidadeSegmento
                (0,
                        Integer.parseInt(String.valueOf(R.drawable.pizzas2)),
                        "nome",
                        "descricao",
                        new Intent(this, MenuPrincipalEmpresa.class),
                        this);

        entidadeSegmentos.add(entidadeSegmento);

        entidadeSegmento = new EntidadeSegmento
                (0,
                        Integer.parseInt(String.valueOf(R.drawable.pizzas2)),
                        "nome",
                        "descricao",
                        new Intent(this, MenuPrincipalEmpresa.class),
                        this);

        entidadeSegmentos.add(entidadeSegmento);


        RecyclerView recyclerViewentidadeSegmentos = (RecyclerView) findViewById(R.id.recyclerViewSegmentos);
        SegmentosAdapter entidadeSegmentosAdapter = new SegmentosAdapter(entidadeSegmentos);
        recyclerViewentidadeSegmentos.setAdapter(entidadeSegmentosAdapter);
        recyclerViewentidadeSegmentos.setItemAnimator(null);
        recyclerViewentidadeSegmentos.setHasFixedSize(false);
        /*LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(SegmentoActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerViewentidadeSegmentos.setLayoutManager(horizontalLayoutManagaer);*/

        recyclerViewentidadeSegmentos.setItemViewCacheSize(20);
        recyclerViewentidadeSegmentos.setDrawingCacheEnabled(true);
        recyclerViewentidadeSegmentos.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);


        recyclerViewentidadeSegmentos.setItemAnimator(null);
        recyclerViewentidadeSegmentos.setHasFixedSize(false);


        ViewCompat.setNestedScrollingEnabled(recyclerViewentidadeSegmentos, false);

        recyclerViewentidadeSegmentos.setNestedScrollingEnabled(false);


        //////////////////

        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
        recyclerViewentidadeSegmentos.setLayoutAnimation(animation);
        ///////////////

        entidadeSegmentosAdapter.notifyDataSetChanged();

    }
}
