package com.kylukz.fooddev.Toolbox;


import android.net.Uri;
import android.os.StrictMode;

import com.aide.aiDelivery.BuildConfig;
import com.kylukz.fooddev.DAO.LogDAO;
import com.kylukz.fooddev.JavaBeans.EntidadeUsuario;
import com.kylukz.fooddev.Routes.Rotas;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Recebe um JSON enviada do servidor para a aplicação
 * RETORNO:
 * 0                      - null
 * 1                      - JSON carregado
 *
 * @author Igor Maximo <igormaximo_1989@hotmail.com>
 * @criado 19/02/2019
 * @editado 02/03/2019
 */
public class Json {

    private URL url = null;
    private HttpURLConnection urlConnection = null;
    private InputStream in = null;

    public JSONArray setEnviaPostRetornaJsonObjectServidor(String rota, Uri.Builder jsonPostParamsBuilder) {
        System.out.println(rota);
        JSONArray jsonArray = null;
        try {
            String jsonInput = this.retornaJSON(rota, jsonPostParamsBuilder);
            jsonArray = new JSONArray(jsonInput);
        } catch (Exception e) {
            System.out.println(e);
            LogDAO.setErro("Json", "retornaJsonObjectServidor", e.getMessage(), Integer.parseInt(String.valueOf(e.getStackTrace()[0])), 0, new EntidadeUsuario().getId(), new EntidadeUsuario().getFkVersionamento());
        }
        return jsonArray;
    }

    //////////////////////////////////////////////////////////////
    //                        RECEBE JSON                       //
    //////////////////////////////////////////////////////////////
    private boolean enviaJSON(String arquivoPHP, JSONObject json) {

        Logger LOG = Logger.getLogger(BuildConfig.class.getName());

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            try {
                URL url = new URL(VariaveisGlobais.IP + arquivoPHP);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setConnectTimeout(VariaveisGlobais.HTTP_URL_CONNECTION);
                connection.setReadTimeout(VariaveisGlobais.HTTP_URL_CONNECTION);
                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                out.write(String.valueOf(json));
                out.close();

                //System.out.println("TAG salva! (json):");

                int http_status = connection.getResponseCode();
                if (http_status / VariaveisGlobais.HTTP_CONNECTION_TIME != 2) {
                    LOG.log(Level.SEVERE, "Ocorreu algum erro. Codigo de resposta: {0}", http_status);
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                try {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                } finally {
                    reader.close();
                }
            } catch (Exception e) {
                LOG.log(Level.SEVERE, null, e);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, null, e);
        }

        return false;
    }




    //////////////////////////////////////////////////////////////
    //                        RECEBE JSON                       //
    //////////////////////////////////////////////////////////////

    /**
     * Método que envia um array POST para o servidor e coleta
     * de retorno um array JSON
     *
     *
     * @param stringUrl
     * @param builderAppends
     * @author  Igor Maximo <igormaximo_1989@hotmail.com>
     * @date    10/05/2020
     * @return  JSONArray
     */
    private String retornaJSON(String stringUrl, Uri.Builder builderAppends) {
        try {
            url = new URL(Rotas.ROTA_PADRAO + stringUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            //urlConnection.setReadTimeout(VariaveisGlobais.HTTP_TIME_OUT);
            //urlConnection.setConnectTimeout(VariaveisGlobais.HTTP_URL_CONNECTION);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

//            Uri.Builder builder = builderAppends.appendQueryParameter("tokenX", VariaveisGlobais.TOKEN_X);
            Uri.Builder builder = builderAppends;

            String query = "";
            if (builderAppends != null) {
                query = builder.build().getEncodedQuery();
            }


            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            urlConnection.connect();
            in = new BufferedInputStream(urlConnection.getInputStream());

            return readStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return "";
    }

    /**
     * <>SOBRESCRITA</>
     * Método que coleta de retorno um array JSON
     * do servidor.
     *
     *
     * @param stringUrl
     * @author  Igor Maximo <igormaximo_1989@hotmail.com>
     * @date    10/05/2020
     * @return
     */
    private String retornaJSON(String stringUrl) {
        try {
            url = new URL(stringUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream());

            return readStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return "";
    }


    private String readStream(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(in), 1000);
        for (String line = r.readLine(); line != null; line = r.readLine()) {
            sb.append(line);
        }
        in.close();

        return sb.toString();
    }
}
