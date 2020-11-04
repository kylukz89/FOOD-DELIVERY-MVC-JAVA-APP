package com.kylukz.fooddev.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kylukz.fooddev.Toolbox.VariaveisGlobais;


/**
 * DDL SQLite para gerar cadeia de tabelas no banco embarcado
 * RETORNO:
 * 0                      - Tabela existente
 * 1                      - Tabela criada com sucesso
 *
 * @author Igor Maximo <igormaximo_1989@hotmail.com>
 * @criado 19/02/2019
 * @editado 02/03/2019
 */

public class GeraTabelasSQLite extends SQLiteOpenHelper {

    private static final String ID = "_id";

    private static final String tabelaGerenciamento = "gerenciamento";

    public final String sqlCriaTabelaGerenciamento = "CREATE TABLE " + tabelaGerenciamento + " ("
            + ID + " integer, "
            + " pin" + " text,"
            + " senha" + " text,"
            + " tipo" + " text"
            + ")";


    private static GeraTabelasSQLite instance;

    public static synchronized GeraTabelasSQLite getHelper(Context context) {
        if (instance == null)
            instance = new GeraTabelasSQLite(context);
        return instance;
    }

    public GeraTabelasSQLite(Context context) {
        super(context, VariaveisGlobais.NOME_BANCO, null, VariaveisGlobais.VERSAO_DB);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(sqlCriaTabelaGerenciamento);
            System.err.println("Tabela Gerenciamento criada com sucesso! SQLiteGeraTabelaGerenciamento " + sqlCriaTabelaGerenciamento);
        } catch (Exception e) {
            System.err.println("Erro ao criar tabelas  - SQLiteGeraTabela !");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}