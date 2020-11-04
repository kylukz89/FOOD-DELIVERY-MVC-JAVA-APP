package com.kylukz.fooddev.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kylukz.fooddev.Toolbox.VariaveisGlobais;

/**
 * DDL SQLite para gerar tabela gerenciamento no banco embarcado a fim de salvar as credenciais
 * RETORNO:
 * 0                      - Tabela existente
 * 1                      - Tabela criada com sucesso
 *
 * @author Igor Maximo <igormaximo_1989@hotmail.com>
 * @criado 19/02/2019
 * @editado 02/03/2019
 */
public final class SQLiteGeraTabelaGerenciamento extends SQLiteOpenHelper {

    private static final String ID = "_id";
    private static final String tabelaGerenciamento = "gerenciamento";

    public SQLiteGeraTabelaGerenciamento(Context ctx) {
        super(ctx, VariaveisGlobais.NOME_BANCO, null, VariaveisGlobais.VERSAO_DB);
    }

    public final String sqlCriaTabelaGerenciamento = "CREATE TABLE " + tabelaGerenciamento + " ("
            + ID + " integer, "
            + " pin" + " text,"
            + " senha" + " text,"
            + " tipo" + " text"
            + ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(sqlCriaTabelaGerenciamento);
            System.err.println("Tabela Gerenciamento criada com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao criar tabela Gerenciamento !");
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + tabelaGerenciamento);
            try {
                onCreate(db);
                System.err.println("Tabela Dropada! " + tabelaGerenciamento);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    /**
     * Executado após a criação da tabela
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 26/07/2020
     */
    private void setCadastraQuebraIncrementoPrimeiraRegistroTabela() throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("_id", 1);
            values.put("pin", "-");
            values.put("senha", "-");
            values.put("tipo", "-");

            db.insert(tabelaGerenciamento, null, values);
        } catch (Exception e) {
            System.err.println("Erro cadastra1QuebraIncremento SQLite!");
            e.printStackTrace();
        }

    }

    public int getLinhasBD(String selectQuery) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor myCursor = db.rawQuery(selectQuery, null);
        return myCursor.getCount();
    }

    /**
     * Retornas as credenciais salvas
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 26/07/2020
     */
    public String[] getSelectUltimoLogin() {
        String[] vetor = new String[4];
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.rawQuery("select _id, pin, senha, tipo from gerenciamento", null);
            if (c.moveToFirst()) {
                do {
                    vetor[0] = c.getString(0);
                    vetor[1] = c.getString(1);
                    vetor[2] = c.getString(2);
                    vetor[3] = c.getString(3);
                } while (c.moveToNext());
            }
            c.close();
            db.close();
        } catch (Exception e) {
            System.err.println("Erro selectUltimoLogin SQLite! ==>> " + e);
            e.printStackTrace();
        }
        return vetor;
    }

    /**
     * Atualiza as credenciais salvas de login
     * para autenticações automáticas nas próximas vezes
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 26/07/2020
     */
    public void setAtualizaLogin(String login, String senha, String tipoAuth) {
        try {
            setCadastraQuebraIncrementoPrimeiraRegistroTabela();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("update gerenciamento " + "set pin = '" + login + "', senha = '" + senha + "', tipo = '" + tipoAuth + "'");
            db.close();
        } catch (Exception e) {
            System.err.println("====================================================>" + e);
            System.err.println("Erro atualizaLogin SQLite! ==>> " + e);
            e.printStackTrace();
        }
    }


}
