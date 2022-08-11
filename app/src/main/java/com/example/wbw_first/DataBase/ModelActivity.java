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
                value = db.insert("activity", "idactivity", contentValues);

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
        String query = "SELECT activity.idactivity, activity.iduser, activity.numberbox, activity.dateregister, " +
                        " area.idarea, area.namearea, area.price " +
                        "FROM activity INNER JOIN area ON area.idarea = activity.idarea " +
                        "ORDER BY activity.idactivity DESC";

        Cursor cursor = db.rawQuery(query, null);

        // Obteniendo los datos obtenidos en la consulta anterior
        while (cursor.moveToNext()){
            // Instanciando la entidad
            eActivity = new EActivity();

            // Asignar los datos del cursos a la ENTIDAD
            eActivity.setIdactivity(cursor.getInt(0));
            eActivity.setIduser(cursor.getInt(1));
            eActivity.setNumberbox(cursor.getInt(2));
            eActivity.setDateregister(cursor.getString(3));
            eActivity.setIdarea(cursor.getInt(4));
            eActivity.setNamearea(cursor.getString(5));
            eActivity.setPrice(cursor.getDouble(6));

            // Añadiendo el objeto al array
            dataList.add(eActivity);
        }

        return dataList;
    }

    // listar
    public ArrayList<EActivity> getRowsByIduser(int iduser){
        // Solicitar acceso de tipo lectura
        SQLiteDatabase db = getReadableDatabase();

        // Instancia user
        EActivity eActivity;

        // Instanciado el arraylist
        ArrayList<EActivity> dataList = new ArrayList<>();

        // Desencadenar la consulta y traer los datos
        String query = "SELECT activity.idactivity, activity.iduser, activity.numberbox, activity.dateregister, " +
                        " area.idarea, area.namearea, area.price " +
                        "FROM activity INNER JOIN area ON area.idarea = activity.idarea " +
                        "WHERE activity.iduser = " + iduser +
                        " ORDER BY activity.idactivity ASC";

        Cursor cursor = db.rawQuery(query, null);

        // Obteniendo los datos obtenidos en la consulta anterior
        while (cursor.moveToNext()){
            // Instanciando la entidad
            eActivity = new EActivity();

            // Asignar los datos del cursos a la ENTIDAD
            eActivity.setIdactivity(cursor.getInt(0));
            eActivity.setIduser(cursor.getInt(1));
            eActivity.setNumberbox(cursor.getInt(2));
            eActivity.setDateregister(cursor.getString(3));
            eActivity.setIdarea(cursor.getInt(4));
            eActivity.setNamearea(cursor.getString(5));
            eActivity.setPrice(cursor.getDouble(6));

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
