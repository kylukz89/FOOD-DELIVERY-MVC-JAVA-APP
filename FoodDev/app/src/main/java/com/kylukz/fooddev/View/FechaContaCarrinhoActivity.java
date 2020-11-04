package com.kylukz.fooddev.View;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aide.aiDelivery.R;

public class FechaContaCarrinhoActivity extends Fragment {

    public static final FechaContaCarrinhoActivity newInstance() {
        return new FechaContaCarrinhoActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.popup_pagar_cartao, container, false);
    }
}
