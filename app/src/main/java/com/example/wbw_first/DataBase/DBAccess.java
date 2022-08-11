package com.example.wbw_first.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wbw_first.Entities.EUser;

public class DBAccess extends SQLiteOpenHelper {

    // Declaración e inicialización de constantes
    private static final String NAME_DB = "WPWHARD";
    private static final int VERSION_DB = 1;

    // Definición de tablas
    private static final String TABLE_USER = "CREATE TABLE user (iduser INTEGER, nameuser TEXT NOT NULL, lastname TEXT NOT NULL, PRIMARY KEY(iduser AUTOINCREMENT))";
    private static final String TABLE_AREA = "CREATE TABLE area (idarea INTEGER, namearea TEXT NOT NULL UNIQUE, price REAL NOT NULL, dateregister TEXT NOT NULL, PRIMARY KEY(idarea AUTOINCREMENT))";
    private static final String TABLE_ACTIVITY = "CREATE TABLE activity (idactivity INTEGER, iduser INTEGER NOT NULL, idarea INTEGER NOT NULL, " +
                                                " numberbox INTEGER NOT NULL, dateregister TEXT NOT NULL, "+
                                                " PRIMARY KEY(idactivity AUTOINCREMENT), " +
                                                " FOREIGN KEY(iduser) REFERENCES user (iduser), " +
                                                " FOREIGN KEY(idarea) REFERENCES area (idarea)" +
                                                ")";

    // Constructor DataBase
    public DBAccess(@Nullable Context context) {
        super(context, NAME_DB, null, VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_USER);
        sqLiteDatabase.execSQL(TABLE_AREA);
        sqLiteDatabase.execSQL(TABLE_ACTIVITY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Eliminar la tabla
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS user");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS area");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS activity");

        // Volver a crear
        onCreate(sqLiteDatabase);
    }

}
