package com.kylukz.fooddev.Adapters;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.aide.aiDelivery.R;
import com.kylukz.fooddev.JavaBeans.EntidadeEmpresa;
import com.kylukz.fooddev.Toolbox.Ferramentas;
import com.kylukz.fooddev.Toolbox.VariaveisGlobais;
import com.kylukz.fooddev.View.EmpresaActivity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;


public class EmpresaAdapter extends RecyclerView.Adapter<EmpresaAdapter.PontoViewHolder> {


    private List<EntidadeEmpresa> pontoList;
    public static Context ctx;
    private int pos;


    public EmpresaAdapter(List<EntidadeEmpresa> planoList) {
        this.pontoList = planoList;
    }

    @Override
    public EmpresaAdapter.PontoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_ponto, parent, false);
        return new PontoViewHolder(itemView);
    }

    public static Drawable decodeDrawable(Context context, String base64) {
        Drawable ret = null;//from w ww.  ja  va  2  s  .  c  o  m
        if (!base64.equals("")) {
            ByteArrayInputStream bais = new ByteArrayInputStream(
                    Base64.decode(base64.getBytes(), Base64.DEFAULT));
            ret = Drawable.createFromResourceStream(context.getResources(),
                    null, bais, null, null);
            try {
                bais.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return ret;
    }


    public Bitmap fastblur(Bitmap sentBitmap, float scale, int radius) {

        int width = Math.round(sentBitmap.getWidth() * scale);
        int height = Math.round(sentBitmap.getHeight() * scale);
        sentBitmap = Bitmap.createScaledBitmap(sentBitmap, width, height, false);

        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.setPixels(pix, 0, w, 0, 0, w, h);

        return (bitmap);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(PontoViewHolder holder, final int position) {
        try {
            holder.imageViewPonto.setImageBitmap((pontoList.get(position).getImagem()));
        } catch (Exception e) {

        }
//        Picasso.with(pontoList.get(position).getCtx()).load(String.valueOf(decodeDrawable(pontoList.get(position).getCtx(), pontoList.get(position).getImagem())).into(holder.imageViewPonto);
        // Sombreamento
        Ferramentas.setSombraTextView(holder.textViewNomePonto, 0, 0, VariaveisGlobais.ESPESSURA_BRILHO_OU_SOMBRA_TEXTOS, Color.BLACK);

        holder.textViewNomePonto.setText(Html.fromHtml("<span><b>" + pontoList.get(position).getNome() + "</b></span>"));

//        DisplayMetrics displayMetrics =  opcoesPontoList.get(position).getCtx().getResources().getDisplayMetrics();

        // CLICAR NA RECYCLERVIEW
        holder.imageViewPonto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = position;
                System.out.println("===> " + EmpresaActivity.FK_EMPRESA_SELECIONADA + " ==== " + pontoList.get(position).getId());

                // Set empresa escolhida
                EmpresaActivity.ENTIDADE_EMPRESA = pontoList.get(position); // Seta empresa escolhida
                EmpresaActivity.FK_EMPRESA_SELECIONADA = pontoList.get(position).getId();
                new AsyncTaskGetEmpresa().execute();
            }
        });

    }

    @Override
    public int getItemCount() {
        return pontoList.size();
    }


    class PontoViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewPonto;
        private TextView textViewNomePonto;


        public PontoViewHolder(View view) {
            super(view);
            imageViewPonto = (ImageView) view.findViewById(R.id.imageViewPonto);
            textViewNomePonto = (TextView) view.findViewById(R.id.textViewNomePonto);

        }
    }


    @SuppressLint("StaticFieldLeak")
    private class AsyncTaskGetEmpresa extends AsyncTask<String, Integer, Void> {
        private ProgressDialog mProgress = null;

        @Override
        protected void onProgressUpdate(Integer... progress) {
            mProgress.setProgress(progress[0]);
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            this.mProgress = ProgressDialog.show(pontoList.get(0).getCtx(), null, "Processando...", true);
            this.mProgress.setIndeterminate(false);
            this.mProgress.setCancelable(false);
        }

        @Override
        protected void onPostExecute(Void result) {
            Intent intent = new Intent(pontoList.get(pos).getCtx(), EmpresaActivity.class);
            pontoList.get(pos).getCtx().startActivity(intent);
            mProgress.dismiss();
            super.onPostExecute(result);
        }
    }
}
