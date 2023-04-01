package com.example.contadordecalorias;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;

public class DBAdapter {
    //Variáveis-----------------------------------------------------
    private static final String databaseName = "Stramdiet.db";
    private static final int databaseVersion = 1;

    //Variáveis da DB-----------------------------------------------
    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    //Classe DBAdapter----------------------------------------------
    public DBAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    //Classe DatabaseHelper---------------------------------------------
    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, databaseName, null, databaseVersion);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                //Criar as tabelas
                db.execSQL("CREATE TABLE IF NOT EXISTS food(food_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " food_name VARCHAR, " +
                        "food_manufactor_name VARCHAR)");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS food");
            onCreate(db);

            String TAG = "Tag";
            Log.w(TAG, "Atualizando a database da versão " + oldVersion + " para " + newVersion +
                    " , no qual vai destruir todos os dados antigos");
        }
    }

    //Abrir a Database-------------------------------------------------------
    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //Fechar a Database-------------------------------------------------------
    public void close() {
        DBHelper.close();
    }

}
