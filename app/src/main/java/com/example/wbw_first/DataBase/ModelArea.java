package com.example.wbw_first.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.wbw_first.Entities.EArea;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ModelArea extends DBAccess{

    public ModelArea(@Nullable Context context) {
        super(context);
    }

    // Register
    public long registerArea(EArea eArea){
        // id
        long idarea = 0;
        try{
            // Objeto que permite la escritura de datos
            SQLiteDatabase db = getWritableDatabase();

            // Validando acceso
            if(db != null){
                // Objeto para almacenar los valores a enviar
                ContentValues contentValues = new ContentValues();

                // Datos pasados por clave => valor
                contentValues.put("namearea", eArea.getNamearea());
                contentValues.put("price", eArea.getPrice());
                contentValues.put("dateregister", LocalDateTime.now().toString()); //

                // Devolver el resultado
                idarea = db.insert("area", "idarea", contentValues);

                // Cerrar conexión
                db.close();
            }

        } catch (SQLException exception){
            throw new RuntimeException(exception.toString());
        } finally {
            // Valor devuelto
            return idarea;
        }
    }

    // listar
    public ArrayList<EArea> getRows(){
        // Solicitar acceso de tipo lectura
        SQLiteDatabase db = getReadableDatabase();

        // Instancia user
        EArea eArea;

        // Instanciado el arraylist
        ArrayList<EArea> dataList = new ArrayList<>();

        // Desencadenar la consulta y traer los datos
        Cursor cursor = db.rawQuery("SELECT * FROM area ORDER BY idarea DESC", null);

        // Obteniendo los datos obtenidos en la consulta anterior
        while (cursor.moveToNext()){
            // Instanciando la entidad
            eArea = new EArea();

            // Asignar los datos del cursos a la ENTIDAD
            eArea.setIdarea(cursor.getInt(0));
            eArea.setNamearea(cursor.getString(1));
            eArea.setPrice(cursor.getDouble(2));
            eArea.setDateregister(cursor.getString(3));

            // Añadiendo el objeto al array
            dataList.add(eArea);
        }

        return dataList;
    }

    // Update
    public long updateArea(EArea eArea){
        // Objeto que permite la esritura de datos
        SQLiteDatabase db = getWritableDatabase();
        long value = -1;
        try{
            // Parametros (consición a evaluar)
            String[] parameters = {String.valueOf(eArea.getIdarea())};

            // Contenido de los valores a actualizar
            ContentValues values = new ContentValues();
            values.put("namearea", eArea.getNamearea());
            values.put("price", eArea.getPrice());

            value = db.update("area", values, "idarea=?", parameters);

            // Cerrar la conexión
            db.close();

        } catch (SQLException exception){
            throw new RuntimeException(exception.toString());
        } finally {
            return value;
        }
    }

    // Delete
    public long deleteArea(int idarea){
        // Obteniendo acceso de escritura
        SQLiteDatabase db = getWritableDatabase();

        // Definir array con los parametros de entrada
        String[] parameters = {String.valueOf(idarea)};

        // Ejecutar proceso
        int value = db.delete("area", "idarea=?", parameters);

        // Cerrar la conexión
        db.close();

        return value;
    }

    // Datos de prueba
    public EArea searchAreaById(int idarea){

        EArea eArea = new EArea();

        // Objeto que nos brinda acceso a la bd
        SQLiteDatabase db = getReadableDatabase();

        // Campos usados para la busqueda
        String[] paramConsult = {String.valueOf(idarea)};

        // Campos a obtener (segun el orden indicado)
        String[] getFields = {"idarea", "namearea", "price", "dateregister"};

        // Control de excepciones
        try{
            // Ejecutar y obtener los datos
            Cursor cursor = db.query("area", getFields, "idarea=?", paramConsult, null, null, null);

            // Se encontró registro coincidente
            if(cursor.moveToFirst()){
                // Enviando los datos a las cajas de textos
                eArea.setIdarea(cursor.getInt(0));
                eArea.setNamearea(cursor.getString(1));
                eArea.setPrice(cursor.getDouble(2));
                eArea.setDateregister(cursor.getString(3));
            }

            // Cerrar conexión
            cursor.close();
        } catch(Exception error){
            error.printStackTrace();
        } finally {
            return eArea;
        }
    }

}
