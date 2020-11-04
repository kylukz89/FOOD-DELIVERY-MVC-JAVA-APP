package com.kylukz.fooddev.Toolbox;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Classe responsável por manipular
 * os dados de rua por CEP
 *
 * @author  Igor Maximo <igormaximo_1989@hotmail.com>
 * @date     20/06/2020
 */
abstract class ViaCEP {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String[] getEnderecoPorCEP(String CEP) throws IOException {
        String[] vetorRecortado = null;
        try {
            URL url = new URL("https://viacep.com.br/ws/" + CEP + "/piped/");
            StringBuilder builder = null;
            String built = "";
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()))) {
                String retornoJson;
                builder = new StringBuilder();
                while ((retornoJson = bufferedReader.readLine()) != null) {
                    builder.append(retornoJson);
                }
                built = String.valueOf(builder);
            }
            vetorRecortado = fragmentaPiped(built);
        } catch (MalformedURLException e) {
            System.err.println(e);
            System.err.println(e.getMessage());
        }
        return vetorRecortado;
    }

    private static String[] fragmentaPiped(String pipedBuilt) {
        String recorte = "";
        String[] vetorRecortado = new String[8];

        int j = -1;

        for (int i = 0; i < pipedBuilt.length(); i++) {
            recorte = recorte + pipedBuilt.substring(i, i + 1);
            if (pipedBuilt.substring(i, i + 1).equals("|")) {
                j++;
                vetorRecortado[j] = recorte.
                        replace("cep", "").
                        replace("logradouro", "").
                        replace("complemento", "").
                        replace("bairro", "").
                        replace("localidade", "").
                        replace("uf", "").
                        replace("unidade", "").
                        replace("ibge", "").
                        replace("gia", "").replace("|", "").replace(":", "").replace("-", "");
                recorte = "";
            }
        }
        return vetorRecortado;
    }
}
