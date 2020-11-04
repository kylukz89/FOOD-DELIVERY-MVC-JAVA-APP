package com.kylukz.fooddev.Toolbox;


import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Base64;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.aide.aiDelivery.R;
import com.kylukz.fooddev.SQLite.SQLiteGeraTabelaGerenciamento;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.NetworkInterface;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

import static android.content.Context.VIBRATOR_SERVICE;


/**
 * Métodos auxiliares genéricos para qualquer parte da aplicação
 *
 * @author Igor Maximo <igormaximo_1989@hotmail.com>
 */
public final class Ferramentas extends ViaCEP {

    /*
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void geraNotificacaoPushPadrao(Context ctx, String titulo, String msg, int idNotification) {
            try {
                Vibrator v = (Vibrator) ctx.getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(700);
            } catch (Exception e) {
                System.err.println(e);
            }

            ////////////////////// VIBRAR ///////////////////////////
            Notification.Builder mBuilder = null;
            mBuilder = new Notification.Builder(ctx)
                    .setSmallIcon(R.drawable.ic_notificacao_padrao)
                    .setContentTitle(titulo).setStyle(new Notification.BigTextStyle())
                    .setContentText(msg);
            Intent resultIntent = new Intent(ctx, MainActivity.class);
            android.app.TaskStackBuilder stackBuilder = android.app.TaskStackBuilder.create(ctx);
            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager =
                    (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mNotificationManager.notify(idNotification, mBuilder.build());
            }
            //////////////////////////////////////////////////////////
        }
    */
    public static String[] concatenaVetores(String[] vetor1, String[] vetor2) {
        final int length = vetor1.length + vetor2.length;
        String[] vetorConcatenado = new String[length];

        int i = 0;
        int k = 0;
        int t = 0;
        int j = vetor1.length;

        while (i < length) {
            if (i >= vetor1.length) {
                vetorConcatenado[j] = vetor2[k];
                j++;
                k++;
            } else {
                if (i < vetor1.length) {
                    vetorConcatenado[t] = vetor1[t];
                    t++;
                }
            }
            i++;
        }
        return vetorConcatenado;
    }

    public static String extraiPalavraEntreParenteses(String x) {
        boolean parenteseEsquerdo = false;
        boolean parenteseDireito = false;
        int pos1 = 0;
        int pos2 = 0;
        for (int i = 0; i < x.length(); i++) {
            if (x.substring(i, i + 1).equals("(")) {
                parenteseEsquerdo = true;
                pos1 = i;
            }
            if (x.substring(i, i + 1).equals(")")) {
                parenteseDireito = true;
                pos2 = i;
            }

        }
        return x.substring(pos1 + 1, pos2);
    }

    public String copiarTextClipBoard(Context context, String text) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(text);
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
                clipboard.setPrimaryClip(clip);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return text;
    }


    public static void disparaAlertaNotificacaoPadrao(Context ctx, String msg, long milliseconds) {
        // Dispara alerta preto e branco no relógio do dispositivo; igual qualquer outro app.
        Vibrator rr = (Vibrator) ctx.getSystemService(VIBRATOR_SERVICE);
        rr.vibrate(milliseconds);
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
        rr.vibrate(milliseconds);
    }

    public static void restartAPPeApagaCredenciais(Context context) {

        setApagaCredenciaisAcessoSQLite(context);

        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
        ComponentName componentName = intent.getComponent();
        Intent mainIntent = Intent.makeRestartActivityTask(componentName);
        context.startActivity(mainIntent);
        System.exit(0);
    }

    public static void encerraAPP(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
        ComponentName componentName = intent.getComponent();
        Intent mainIntent = Intent.makeRestartActivityTask(componentName);
        context.startActivity(mainIntent);
        System.exit(0);
    }

    public static void restartAPP(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
        ComponentName componentName = intent.getComponent();
        Intent mainIntent = Intent.makeRestartActivityTask(componentName);
        context.startActivity(mainIntent);
        System.exit(0);
    }

    public static String converteImageViewBase64(ImageView imagem) {
        //String imageEncoded = null;
        Bitmap bitmap = null;
        bitmap = ((BitmapDrawable) imagem.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, VariaveisGlobais.qualidadeImagem, baos);
        byte[] byteArrayImage = baos.toByteArray();
        return Base64.encodeToString(byteArrayImage, Base64.NO_WRAP);
    }

    public static int retornaNumeroXTemporal(Spinner comboX, String[] vetor, String tipo, String mdX) {
        int i;
        int foundIndex = 0;
        i = 0;
        while (i < vetor.length) {
            if (comboX.getItemAtPosition(i).toString().equals(mdX)) {
                foundIndex = i;
                break;
            }
            i++;
        }
        return foundIndex;
    }

    public static Bitmap convertBase64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

    /**
     *
     */
//    public void setToastCustomizado(Context context, LayoutInflater inflater, int layoutToast, int layoutToastRoot) {
////        LayoutInflater inflater = getLayoutInflater();
////        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout_root));
//        View layout = inflater.inflate(layoutToast, (ViewGroup) findViewById(layoutToastRoot));
//
//        ImageView image = (ImageView) layout.findViewById(R.id.image);
//        image.setImageResource(R.drawable.android);
//        TextView text = (TextView) layout.findViewById(R.id.text);
//        text.setText("Hello! This is a custom toast!");
//
//        Toast toast = new Toast(context);
//        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//        toast.setDuration(Toast.LENGTH_LONG);
//        toast.setView(layout);
//        toast.show();
//    }

    public static String[] contadorTemporal(int pontoInicial, int length) {
        int i = 0;
        String[] contagem = new String[length];
        while (i < length) {
            if (pontoInicial < 10) {
                contagem[i] = String.valueOf("0" + pontoInicial);
            } else {
                contagem[i] = String.valueOf(pontoInicial);
            }
            pontoInicial++;
            i++;
        }

        return contagem;
    }

    public static int retornaInteiroRandom(int limite) {
        int last = 0;
        Random r = new Random();
        int random = 0;
        random = r.nextInt(limite);
        last = random;
        if (random != last) {
            return random;
        } else {
            return r.nextInt(limite);
        }
    }

    public static String retornaCorHexadecimalAleatoria() {
        //(r,g,b)
        return String.format("%02x%02x%02x", retornaInteiroRandom(255), retornaInteiroRandom(255), retornaInteiroRandom(255));
    }

    /**
     * Exibe calendário ao usuário para escolher uma data
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * data 18/04/2019
     */
    public static void setMascaraDataPicker(Calendar calendar, Activity activity, int layout) {
        EditText data = (EditText) activity.findViewById(layout);
        String myFormat = VariaveisGlobais.MASCARA_DATA_HORA; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));
        data.setText(sdf.format(calendar.getTime()));
    }

    public static String setMascaraCPF(String CPF) {
        return CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." + CPF.substring(6, 9) + "-" + CPF.substring(9, CPF.length());
    }

    public static String mascaraCNPJ(String CNPJ) {
        System.err.println("============> " + CNPJ);
        return CNPJ.substring(0, 2) + "." + CNPJ.substring(2, 5) + "." + CNPJ.substring(5, 8) + "/" + CNPJ.substring(8, 12) + "-" + CNPJ.substring(12, CNPJ.length());
    }


    /**
     * Trunca o sqlite
     *
     * @author      Igor Maximo <igormaximo_1989@hotmail.com>
     * @date        26/07/2020
     */
    private static void setApagaCredenciaisAcessoSQLite(Context ctx) {
        SQLiteGeraTabelaGerenciamento sqliger = new SQLiteGeraTabelaGerenciamento(ctx);
        try {
            sqliger.setAtualizaLogin("-", "-", "-");
        } catch (Exception e) {
            System.err.println("apagaCredenciaisAcessoSQLite() " + e);
        }

    }

    public static String retornaBandeiraCartao(String cartaoNumero) {
        /*
                  Visa       | 4                                           | 13,16            | 3                    |
                | Mastercard | 5                                           | 16               | 3                    |
                | Diners     | 301,305,36,38                               | 14,16            | 3                    |
                | Elo        | 636368,438935,504175,451416,509048,509067,  |                  | 3(?)
                |            | 509049,509069,509050,509074,509068,509040,
                |            | 509045,509051,509046,509066,509047,509042,
                |            | 509052,509043,509064,509040                 |                  |
                |            | 36297, 5067,4576,4011                       | 16               | 3
                | Amex       | 34,37                                       | 15               | 4                    |
                | Discover   | 6011,622,64,65                              | 16               | 4                    |
                | Aura       | 50                                          | 16               | 3                    |
                | jcb        | 35                                          | 16               | 3                    |
                | Hipercard  | 38,60                                       | 13,16,19         | 3
        */
        String cartao = cartaoNumero.replace(".", "");

        if (cartao.substring(0, 1).equals("4")) {
            return "Visa";
        } else {
            if (cartao.substring(0, 1).equals("5")) {
                return "Master";
            } else {
                if (cartao.substring(0, 3).equals("301") || cartao.substring(0, 3).equals("305") || cartao.substring(0, 2).equals("36") || cartao.substring(0, 2).equals("38")) {
                    return "Diners";
                } else {
                    if (cartao.substring(0, 6).equals("636368") ||
                            cartao.substring(0, 6).equals("438935") ||
                            cartao.substring(0, 6).equals("504175") ||
                            cartao.substring(0, 6).equals("451416") ||
                            cartao.substring(0, 6).equals("509048") ||
                            cartao.substring(0, 6).equals("509067") ||
                            cartao.substring(0, 6).equals("509049") ||
                            cartao.substring(0, 6).equals("509069") ||
                            cartao.substring(0, 6).equals("509050") ||
                            cartao.substring(0, 6).equals("509074") ||
                            cartao.substring(0, 6).equals("509068") ||
                            cartao.substring(0, 6).equals("509045") ||
                            cartao.substring(0, 6).equals("509051") ||
                            cartao.substring(0, 6).equals("509046") ||
                            cartao.substring(0, 6).equals("509066") ||
                            cartao.substring(0, 6).equals("509047") ||
                            cartao.substring(0, 6).equals("509042") ||
                            cartao.substring(0, 6).equals("509052") ||
                            cartao.substring(0, 6).equals("509043") ||
                            cartao.substring(0, 6).equals("509064") ||
                            cartao.substring(0, 6).equals("509040") ||
                            cartao.substring(0, 5).equals("36297") ||
                            cartao.substring(0, 4).equals("5067") ||
                            cartao.substring(0, 4).equals("4576") ||
                            cartao.substring(0, 4).equals("4011")) {
                        return "Elo";
                    } else {
                        if (cartao.substring(0, 2).equals("34") || cartao.substring(0, 2).equals("37")) {
                            return "Amex";
                        } else {
                            if (cartao.substring(0, 4).equals("6011") || cartao.substring(0, 3).equals("622") || cartao.substring(0, 2).equals("64") || cartao.substring(0, 2).equals("65")) {
                                return "Discover";
                            } else {
                                if (cartao.substring(0, 2).equals("35")) {
                                    return "Jcb";
                                } else {
                                    if (cartao.substring(0, 2).equals("38") || cartao.substring(0, 2).equals("60")) {
                                        return "Hipercard";
                                    } else {
                                        if (cartao.substring(0, 15).equals("000000000000000")) {
                                            return "Visa"; // Ambiente de testes
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public static boolean verificaTodosCamposPreenchidos(boolean[] campos, Context ctx) {
        boolean status = false;
        for (int i = 0; i < campos.length; i++) {
            if (campos[i] == false) {
                //   System.err.println("CAMPOS["+i+"] ======================================================> "+campos[i]);
                // Toast.makeText(ctx, "Campo "+i+" é obrigatório!", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                if (i == campos.length - 1) {
                    //       System.err.println("CAMPOS["+i+"] ======================================================> "+ Arrays.toString(campos));
                    status = true;
                    return status;
                }
            }
        }
        //    System.err.println("CAMPOS ======================================================> "+Arrays.toString(campos));
        return status;
    }


  /*  public static void geraAlerta(Context ctx, String titulo, String msg, int idNotification) {
        /////// VIBRAR
        Vibrator rr = (Vibrator) ctx.getSystemService(VIBRATOR_SERVICE);
        long milliseconds = 1500;
        rr.vibrate(milliseconds);

        ////////////////////// VIBRAR ///////////////////////////
        Notification.Builder mBuilder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mBuilder = new Notification.Builder(ctx)
                    .setSmallIcon(R.drawable.ic_notificacao_padrao)
                    .setContentTitle(titulo).setStyle(new Notification.BigTextStyle())
                    .setContentText(msg);
        }
        Intent resultIntent = new Intent(ctx, SplashActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(ctx);
        stackBuilder.addParentStack(SplashActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mNotificationManager.notify(idNotification, mBuilder.build());
        }
        //////////////////////////////////////////////////////////
    }*/

    public static String setArredondaValorMoedaReal(String valor) {
        BigDecimal v = new BigDecimal(valor);
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        return (nf.format(v)).replace("R$", "").replace("$", "");    }

    // Retorna Marca e Modelo do smartphone
    public static String getMarcaModeloDispositivo(Context ctx) {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return toCapitalize(model);
        }
        return "Marca: " + toCapitalize(manufacturer) + " / Modelo: " + model;
    }

    public static String toCapitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }

        return phrase.toString();
    }

    public static String getNumeroCelular(Context ctx) {
        TelephonyManager tMgr = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(ctx, "android.permission.READ_PHONE_NUMBERS")
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return null;
        }
        return tMgr.getLine1Number();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////// GET MAC DO CELULAR ///////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(Integer.toHexString(b & 0xFF) + ":");
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < macBytes.length; i++) {
                    sb.append(String.format("%02X%s", macBytes[i], (i < macBytes.length - 1) ? "-" : ""));
                }
                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            //handle exception
        }
        return getMacAddress();
    }

    public static String loadFileAsString(String filePath) throws IOException {
        StringBuffer data = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            data.append(readData);
        }
        reader.close();
        return data.toString();
    }

    public static String getMacAddress() {
        try {
            return loadFileAsString("/sys/class/net/eth0/address")
                    .toUpperCase().substring(0, 17);
        } catch (IOException e) {
            e.printStackTrace();
            return "02:00:00:00:00:00";
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Retorna o endereço MAC do roteador onde o celular está conectado
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 18/06/2019
     */
    public static String retornaMACAccessPoint(Context ctx) {
        WifiManager wifiManager = (WifiManager) ctx.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo.getBSSID();
    }

    /**
     * Retorna o SSID do roteador onde o celular está conectado
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 18/06/2019
     */
    public static String retornaSSIDAccessPoint(Context ctx) {
        WifiManager wifiManager = (WifiManager) ctx.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo.getSSID().replace("\"", "");
    }

    /**
     * Retorna o endereço IP privado do roteador onde o celular está conectado
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 18/06/2019
     */
    public static String retornaIPPrivadoAccessPoint(Context ctx) {
        WifiManager wifiMgr = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        String ipAddress = Formatter.formatIpAddress(ip);
        return String.valueOf(ipAddress);
    }

    /**
     * Retorna o endereço IP público do roteador onde o celular está conectado
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 18/06/2019
     */
    public static String retornaIPPublicoAccessPoint(Context ctx) throws Exception {
        //final NetworkInfo info = NetworkUtils.getNetworkInfo(context);

        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo info = cm.getActiveNetworkInfo();

        RunnableFuture<String> futureRun = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                if ((info != null && info.isAvailable()) && (info.isConnected())) {
                    StringBuilder response = new StringBuilder();

                    try {
                        HttpURLConnection urlConnection = (HttpURLConnection) (
                                new URL("http://checkip.amazonaws.com/").openConnection());
                        urlConnection.setRequestProperty("User-Agent", "Android-device");
                        //urlConnection.setRequestProperty("Connection", "close");
                        urlConnection.setReadTimeout(15000);
                        urlConnection.setConnectTimeout(15000);
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setRequestProperty("Content-type", "application/json");
                        urlConnection.connect();

                        int responseCode = urlConnection.getResponseCode();

                        if (responseCode == HttpURLConnection.HTTP_OK) {

                            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                            String line;
                            while ((line = reader.readLine()) != null) {
                                response.append(line);
                            }

                        }
                        urlConnection.disconnect();
                        return response.toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    //Log.w(TAG, "No network available INTERNET OFF!");
                    return null;
                }
                return null;
            }
        });

        new Thread(futureRun).start();

        try {
            return futureRun.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }
/*
    public static void geraAlerta(Context ctx, String alertaTitulo, String alertaMassivo) {
        /////// VIBRAR
        Vibrator rr = (Vibrator) ctx.getSystemService(VIBRATOR_SERVICE);
        long milliseconds = 1500;
        rr.vibrate(milliseconds);

        ////////////////////// VIBRAR ///////////////////////////
        Notification.Builder mBuilder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mBuilder = new Notification.Builder(ctx)
                    .setSmallIcon(R.drawable.ic_notificacao_padrao)
                    .setContentTitle(alertaTitulo).setStyle(new Notification.BigTextStyle())
                    .setContentText(alertaMassivo);
        }
        Intent resultIntent = new Intent(ctx, Splash.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(ctx);
        stackBuilder.addParentStack(Splash.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mNotificationManager.notify((int) Math.random(), mBuilder.build());
        }
        //////////////////////////////////////////////////////////
    }
*/


    /**
     * Seta sombreamento em texto de textViews
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 27/03/2020
     */
    public static void setSombraTextView(TextView textView, float dx, float dy, float largura, int cor) {
        try {
            textView.setShadowLayer(largura, dx, dy, cor);
        } catch (Exception e) {
            System.out.println(e);
        }
    }



    /**
     * Seta sombreamento em texto de textViews
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 27/03/2020
     */
    public static void setSombraEditText(EditText editText, float dx, float dy, float largura, int cor) {
        try {
            editText.setShadowLayer(largura, dx, dy, cor);
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    /**
     * Seta sombreamento em texto de textViews
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 27/03/2020
     */
    public void setSombraTextView(TextView textView, float espessura, int cor) {
        try {
            textView.setShadowLayer(espessura, 0, 0, cor);
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    class ShadowImage extends Drawable {

        Bitmap bm;

        @Override
        public void draw(Canvas canvas) {

            Paint forShadow = new Paint();
            //trying for rectangle
            Rect rect = new Rect(0, 0, bm.getWidth(), bm.getHeight());

            forShadow.setAntiAlias(true);
            forShadow.setShadowLayer(5.5f, 4.0f, 4.0f, Color.BLACK);

            canvas.drawRect(rect, forShadow);
            canvas.drawBitmap(bm, 0.0f, 0.0f, null);

        }

        @Override
        public void setAlpha(int alpha) {

        }

        @Override
        public void setColorFilter(@Nullable ColorFilter colorFilter) {

        }

        @SuppressLint("WrongConstant")
        @Override
        public int getOpacity() {
            return 0;
        }

        public ShadowImage(Bitmap bitmap) {
            super();
            this.bm = bitmap;
        }
    }
    /**
     * Seta sombreamento em texto de textViews
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 27/03/2020
     */
    public void setSombraImageView(ImageView imageView, float espessura, int cor) {
        try {
            Paint forShadow = new Paint();
            forShadow.setShadowLayer(10.0f, 0.0f, 2.0f, 0xFF000000);
            Canvas canvas = new Canvas();

//            canvas.drawBitmap(imageView, 0.0f, 0.0f, forShadow);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Seta animação pulsamento
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 31/05/2020
     */
    public static void setHeartPulseAnimation(ImageView imageView) {
        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(imageView, PropertyValuesHolder.ofFloat("scaleX", 1.2f), PropertyValuesHolder.ofFloat("scaleY", 1.2f));
        scaleDown.setDuration(310);
        scaleDown.setRepeatCount(ObjectAnimator.INFINITE);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);
        scaleDown.start();
    }

    /**
     * Seta animação pulsamento
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 31/05/2020
     */
    public static void setHeartPulseAnimation(ImageView imageView, float scale) {
        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(imageView, PropertyValuesHolder.ofFloat("scaleX", scale), PropertyValuesHolder.ofFloat("scaleY", scale));
        scaleDown.setDuration(310);
        scaleDown.setRepeatCount(ObjectAnimator.INFINITE);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);
        scaleDown.start();
    }

    /**
     * Seta animação pulsamento
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 31/05/2020
     */
    public static void setHeartPulseAnimation(TextView textView, float scale) {
        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(textView, PropertyValuesHolder.ofFloat("scaleX", scale), PropertyValuesHolder.ofFloat("scaleY", scale));
        scaleDown.setDuration(310);
        scaleDown.setRepeatCount(ObjectAnimator.INFINITE);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);
        scaleDown.start();
    }

    /**
     * Seta animação pulsamento
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 31/05/2020
     */
    public static void setHeartPulseAnimation(TextView textView) {
        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(textView, PropertyValuesHolder.ofFloat("scaleX", 1.2f), PropertyValuesHolder.ofFloat("scaleY", 1.2f));
        scaleDown.setDuration(310);
        scaleDown.setRepeatCount(ObjectAnimator.INFINITE);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);
        scaleDown.start();
    }

    /**
     * Seta animação pulsamento
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 31/05/2020
     */
    public static void setFastHeartPulseAnimation(ImageView textView, Context context) {
        Animation pulse = AnimationUtils.loadAnimation(context, R.anim.pulse);
        textView.startAnimation(pulse);
    }

    /**
     * Seta animação pulsamento
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 31/05/2020
     */
    public static void setFastHeartPulseAnimation(TextView textView, Context context) {
        Animation pulse = AnimationUtils.loadAnimation(context, R.anim.pulse);
        textView.startAnimation(pulse);
    }

    /**
     * Seta animação pulsamento
     *=
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 31/05/2020
     */
    public static void setRemoveAnimation(TextView textView)  {
        textView.clearAnimation();
        textView.startAnimation(null);
    }


    /**
     * Seta animação pulsamento
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 31/05/2020
     */
    public static void setRemoveAnimation(ImageView imageView) {
        imageView.clearAnimation();
    }


//    public Drawable resizeImage(int imageResource, Context context) {// R.drawable.large_image
//        // Get device dimensions
//        Display display = getWindowManager().getDefaultDisplay();
//        double deviceWidth = display.getWidth();
//
//        BitmapDrawable bd = (BitmapDrawable) this.getResources().getDrawable(
//                imageResource);
//        double imageHeight = bd.getBitmap().getHeight();
//        double imageWidth = bd.getBitmap().getWidth();
//
//        double ratio = deviceWidth / imageWidth;
//        int newImageHeight = (int) (imageHeight * ratio);
//
//        Bitmap bMap = BitmapFactory.decodeResource(getResources(), imageResource);
//        Drawable drawable = new BitmapDrawable(this.getResources(),
//                getResizedBitmap(bMap, newImageHeight, (int) deviceWidth));
//
//        return drawable;
//    }

    /************************ Resize Bitmap *********************************/
    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {

        int width = bm.getWidth();
        int height = bm.getHeight();

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // create a matrix for the manipulation
        Matrix matrix = new Matrix();

        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);

        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
                matrix, false);

        return resizedBitmap;
    }
//    private static class ShadowImageView extends androidx.appcompat.widget.AppCompatImageView {
//
//        public ShadowImageView(Context context, AttributeSet attrs, int defStyle) {
//            super(context, attrs, defStyle);
//        }
//
//        public ShadowImageView(Context context, AttributeSet attrs) {
//            super(context, attrs);
//        }
//
//        public ShadowImageView(Context context) {
//            super(context);
//        }
//
//        private Paint createShadow() {
//            Paint mShadow = new Paint();
//
//            float radius = 10.0f;
//            float xOffset = 3.0f;
//            float yOffset = 3.0f;
//
//            // color=black
//            int color = 0xFF000000;
//            mShadow.setShadowLayer(radius, xOffset, yOffset, color);
//
//
//            return mShadow;
//        }
//
//        @Override
//        protected void onDraw(Canvas canvas) {
//            Paint mShadow = createShadow();
//            Drawable d = getDrawable();
//            if (d != null){
//                setLayerType(LAYER_TYPE_SOFTWARE, mShadow);
//                Bitmap bitmap = ((BitmapDrawable) getDrawable()).getBitmap();
//                canvas.drawBitmap(bitmap, 0.0f, 0.0f, mShadow);
//            } else {
//                super.onDraw(canvas);
//            }
//
//        };

//    }
    /**
     * Seta animaçõ fadedown na recylerview
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 27/03/2020
     */
    public static void runLayoutAnimationRecyclerView(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    /**
     * Detecta se contém um caracter específico
     */
    public static boolean detectaCaracterEspecifico(String palavra, String caracter) {
        for (int i = 0; i < palavra.length(); i++) {
            if (palavra.substring(i).equals(caracter)) {
                return true;
            }
        }
        return false;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    /*

    Timer timer = null;
        long TEMPO = (5000); // chama o método a cada 10 segundos (10000 = 10 seg)
        if (timer == null) {
            timer = new Timer();
            TimerTask tarefa = new TimerTask() {
                public void run() {
                    try {

                        Ferramentas.geraAlerta(ctx, VariaveisGlobais.ALERTA_TITULO, "Há faturas vencidas!");

                    } catch (Exception e) {
                        System.out.println(Arrays.toString(e.getStackTrace()));
                    }
                }
            };
            timer.scheduleAtFixedRate(tarefa, TEMPO, TEMPO);


     */

}
