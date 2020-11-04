package com.kylukz.fooddev.Toolbox;


import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class ManipulaData {

    public long comparaDiasEntreDatas(Date deData, Date ateData) {
        long diff = ateData.getTime() - deData.getTime();

        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public String converteDataFormatoBR(String recebeData) {
        String[] vetor = new String[3];

        int d1, d2, d3;

        // ENTRADA --> 1989-9-10 00:00:00
        d1 = Integer.parseInt(recebeData.substring(0, 4)); //    1989
        d2 = Integer.parseInt(recebeData.substring(5, 7)); //      9
        d3 = Integer.parseInt(recebeData.substring(8, 10)); //    10

        vetor[2] = String.valueOf(d1);
        vetor[1] = String.valueOf(d2);
        vetor[0] = String.valueOf(d3);

        if (d2 <= 9) {
            vetor[1] = "0" + d2;
        }

        if (d3 <= 9) {
            vetor[0] = "0" + d3;
        }
        // SAÍDA --> 10/09/1989

//        System.out.println(vetor[0]+"/"+vetor[1]+"/"+vetor[2]);
        return String.valueOf(vetor[0] + "/" + vetor[1] + "/" + vetor[2]);
    }

    public String converteDataFormatoSQL(String x) {

        String recebeData = x;
        int d1, d2, d3;

        // ENTRADA --> 10/09/1989
        d1 = Integer.parseInt(recebeData.substring(0, 2));
        d2 = Integer.parseInt(recebeData.substring(3, 5));
        d3 = Integer.parseInt(recebeData.substring(6, 10));

        // SAÍDA --> 1989-09-10
        return String.valueOf(d3 + "-" + d2 + "-" + d1);
    }

    public String converteDataFormatoSQLTratado(String x) {

        String recebeData = x;
        int d1, d2, d3;
        String sd1, sd2, sd3;

        // ENTRADA --> 10/09/1989
        d1 = Integer.parseInt(recebeData.substring(0, 2));
        d2 = Integer.parseInt(recebeData.substring(3, 5));
        d3 = Integer.parseInt(recebeData.substring(6, 10));

        // SAÍDA --> 1989-09-10
        if (d1 <= 9) {
            sd1 = "0" + String.valueOf(d1);
        } else {
            sd1 = String.valueOf(d1);
        }

        if (d2 <= 9) {
            sd2 = "0" + String.valueOf(d2);
        } else {
            sd2 = String.valueOf(d2);
        }

        x = String.valueOf(d3 + "-" + sd2 + "-" + sd1);

        return x;
    }

    // 2016-07-08
    public String incrementaMes(int m) {
        GregorianCalendar c = new GregorianCalendar();
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        c.set(GregorianCalendar.MONTH, +m);
        return dt.format(c.getTime());
    }

    public String autoPreencherAno() {

        SimpleDateFormat sd = new SimpleDateFormat("yyyy");
        Date dataAtual = new Date(System.currentTimeMillis());
        String date = sd.format(dataAtual);
        return date;

    }

    public String getDataBR() {
        SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
        Date dataAtual = new Date(System.currentTimeMillis());
        String date = sd.format(dataAtual);
        return date;
    }

    public String getDataBRVencimento() {
        SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
        Date dataAtual = new Date(System.currentTimeMillis());
        dataAtual.compareTo(dataAtual);

        String date = sd.format(dataAtual);
        return date;
    }

    public String getDataDuasCasasDDMMYY() {
        SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yy");
        Date dataAtual = new Date(System.currentTimeMillis());
        dataAtual.compareTo(dataAtual);

        String date = sd.format(dataAtual);
        return date;
    }

    public String getDataHora() {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dataAtual = new Date(System.currentTimeMillis());
        String date = sd.format(dataAtual);
        return date;
    }

    public String getDataSQL() {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        Date dataAtual = new Date(System.currentTimeMillis());
        String date = sd.format(dataAtual);
        return date;
    }

    public String getHora() {
        SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
        Date dataAtual = new Date(System.currentTimeMillis());
        String date = sd.format(dataAtual);
        return date;
    }

    public String getAno() {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy");
        Date dataAtual = new Date(System.currentTimeMillis());
        String date = sd.format(dataAtual);
        return date;
    }

    public String getMes() {
        SimpleDateFormat sd = new SimpleDateFormat("MM");
        Date dataAtual = new Date(System.currentTimeMillis());
        String date = sd.format(dataAtual);
        return date;
    }

    public String getDia() {
        SimpleDateFormat sd = new SimpleDateFormat("dd");
        Date dataAtual = new Date(System.currentTimeMillis());
        String date = sd.format(dataAtual);
        return date;
    }

    public String getTimeStamp() {
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date dataAtual = new Date(System.currentTimeMillis());
        String date = sd.format(dataAtual);
        return date;
    }

    public String getUmAnoAPartirHoje() {
        GregorianCalendar c = new GregorianCalendar();
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        c.set(GregorianCalendar.YEAR, Integer.parseInt(getAno())-1); // 1 ano atrás
        return dt.format(c.getTime());
    }

    public String extraiMesDeDataEspecifica(String data) {
        // 2018-01-17
        // 01
        return data.substring(5,7);
    }

    public long getDiferencaDiasEntreUmaDataAteHoje(String dataMenor) {
        // FORMATO DE DATA PARA ESTE MÉTODO dd/MM/yyyy
        GregorianCalendar ini = new GregorianCalendar();
        GregorianCalendar fim = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat ("dd/MM/yyyy");
        try {
            ini.setTime(sdf.parse(dataMenor));
            fim.setTime(sdf.parse(getDataBRVencimento()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long dt1 = ini.getTimeInMillis();
        long dt2 = fim.getTimeInMillis();
        return (int) (((dt2 - dt1) / 86400000)+1);
    }

    public String getDataHojeDaquiMeses(int incrementoMeses) {
        // 2019-03-08
        // 2019-MM-08
        GregorianCalendar c = new GregorianCalendar();
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        c.set(GregorianCalendar.MONTH, Integer.parseInt(getMes())+incrementoMeses); // 1 ano atrás
        return dt.format(c.getTime());
    }

}
