package com.example.wbw_first.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.wbw_first.Entities.EActivity;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ModelActivity extends DBAccess{

    public ModelActivity(@Nullable Context context) {
        super(context);
    }

    // Register
    public long registerActivity(EActivity eActivity){
        // id
        long value = 0;
        try{
            // Objeto que permite la escritura de datos
            SQLiteDatabase db = getWritableDatabase();

            // Validando acceso
            if(db != null){
                // Objeto para almacenar los valores a enviar
                ContentValues contentValues = new ContentValues();

                // Datos pasados por clave => valor
                contentValues.put("iduser", eActivity.getIduser());
                contentValues.put("idarea", eActivity.getIdarea());
                contentValues.put("numberbox", eActivity.getNumberbox());
                contentValues.put("dateregister", LocalDateTime.now().toString());

                // Devolver el resultado
                value = db.insert("area", "idarea", contentValues);

                // Cerrar conexión
                db.close();
            }

        } catch (SQLException exception){
            throw new RuntimeException(exception.toString());
        } finally {
            // Valor devuelto
            return value;
        }
    }

    // listar
    public ArrayList<EActivity> getRows(){
        // Solicitar acceso de tipo lectura
        SQLiteDatabase db = getReadableDatabase();

        // Instancia user
        EActivity eActivity;

        // Instanciado el arraylist
        ArrayList<EActivity> dataList = new ArrayList<>();

        // Desencadenar la consulta y traer los datos
        Cursor cursor = db.rawQuery("SELECT * FROM activity ORDER BY idactivity DESC", null);

        // Obteniendo los datos obtenidos en la consulta anterior
        while (cursor.moveToNext()){
            // Instanciando la entidad
            eActivity = new EActivity();

            // Asignar los datos del cursos a la ENTIDAD
            eActivity.setIdactivity(cursor.getInt(0));
            eActivity.setIduser(cursor.getInt(1));
            eActivity.setIdarea(cursor.getInt(2));
            eActivity.setNumberbox(cursor.getInt(3));
            eActivity.setDateregister(cursor.getString(4));

            // Añadiendo el objeto al array
            dataList.add(eActivity);
        }

        return dataList;
    }

    // Update
    public long updateActivity(EActivity eActivity){
        // Objeto que permite la esritura de datos
        SQLiteDatabase db = getWritableDatabase();
        long value = -1;

        try{
            // Parametros (consición a evaluar)
            String[] parameters = {String.valueOf(eActivity.getIdactivity())};

            // Contenido de los valores a actualizar
            ContentValues values = new ContentValues();
            values.put("iduser", eActivity.getIduser());
            values.put("idarea", eActivity.getIdarea());
            values.put("numberbox", eActivity.getNumberbox());

            value = db.update("activity", values, "idactivity=?", parameters);

            // Cerrar la conexión
            db.close();

        } catch (SQLException exception){
            throw new RuntimeException(exception.toString());
        } finally {
            return value;
        }
    }

    // Delete
    public long deleteActivity(int idactivity){
        // Obteniendo acceso de escritura
        SQLiteDatabase db = getWritableDatabase();
        int value = -1;

        try{
            // Definir array con los parametros de entrada
            String[] parameters = {String.valueOf(idactivity)};

            // Ejecutar proceso
            value = db.delete("activity", "idactivity=?", parameters);

            // Cerrar la conexión
            db.close();

        } catch (SQLException exception){
            throw new RuntimeException(exception.toString());
        } finally {
            return value;
        }
    }
}
