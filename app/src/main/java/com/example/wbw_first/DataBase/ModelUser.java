package com.example.wbw_first.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.wbw_first.Entities.EUser;

import java.util.ArrayList;

public class ModelUser extends DBAccess {

    public ModelUser(@Nullable Context context) {
        super(context);
    }

    // Register
    public long registerUser(EUser eUser){
        // Objeto que permite la escritura de datos
        SQLiteDatabase db = getWritableDatabase();

        // id
        long iduser = 0;

        // Validando acceso
        if(db != null){
            // Objeto para almacenar los valores a enviar
            ContentValues contentValues = new ContentValues();

            // Datos pasados por clave => valor
            contentValues.put("nameuser", eUser.getNameuser());
            contentValues.put("lastname", eUser.getLastname());

            // Devolver el resultado
            iduser = db.insert("user", "iduser", contentValues);

            // Cerrar conexión
            db.close();
        }

        // Valor devuelto
        return iduser;
    }

    // listar
    public ArrayList<EUser> getRows(){
        // Solicitar acceso de tipo lectura
        SQLiteDatabase db = getReadableDatabase();

        // Instancia user
        EUser eUser;

        // Instanciado el arraylist
        ArrayList<EUser> dataList = new ArrayList<>();

        // Desencadenar la consulta y traer los datos
        Cursor cursor = db.rawQuery("SELECT * FROM user ORDER BY iduser DESC", null);

        // Obteniendo los datos obtenidos en la consulta anterior
        while (cursor.moveToNext()){
            // Instanciando la entidad
            eUser = new EUser();

            // Asignar los datos del cursos a la ENTIDAD
            eUser.setIduser(cursor.getInt(0));
            eUser.setNameuser(cursor.getString(1));
            eUser.setLastname(cursor.getString(2));

            // Añadiendo el objeto al array
            dataList.add(eUser);
        }

        return dataList;
    }

    // Update
    public long updateUser(EUser eUser){
        // Objeto que permite la esritura de datos
        SQLiteDatabase db = getWritableDatabase();
        int value = -1;

        try{
            // Parametros (condición a evaluar)
            String[] parameters = {String.valueOf(eUser.getIduser())};

            // Contenido de los valores a actualizar
            ContentValues values = new ContentValues();
            values.put("nameuser", eUser.getNameuser());
            values.put("lastname", eUser.getLastname());

            value = db.update("user", values, "iduser=?", parameters);

            // Cerrar la conexión
            db.close();

        } catch (SQLException exception){
            throw new RuntimeException(exception.toString());
        } finally {
            return value;
        }
    }

    // Delete
    public long deleteUser(int iduser){
        // Obteniendo acceso de escritura
        SQLiteDatabase db = getWritableDatabase();

        // Definir array con los parametros de entrada
        String[] parameters = {String.valueOf(iduser)};

        // Ejecutar proceso
        int iduserDelete = db.delete("user", "iduser=?", parameters);

        // Cerrar la conexión
        db.close();

        return iduserDelete;
    }

    // Datos de prueba
    public EUser searchUserById(int iduser){

        EUser eUser = new EUser();

        // Objeto que nos brinda acceso a la bd
        SQLiteDatabase db = getReadableDatabase();

        // Campos usados para la busqueda
        String[] paramConsult = {String.valueOf(iduser)};

        // Campos a obtener (segun el orden indicado)
        String[] getFields = {"iduser", "nameuser", "lastname"};

        // Control de excepciones
        try{
            // Ejecutar y obtener los datos
            Cursor cursor = db.query("user", getFields, "iduser=?", paramConsult, null, null, null);

            // Se encontró registro coincidente
            if(cursor.moveToFirst()){
                // Enviando los datos a las cajas de textos
                eUser.setIduser(cursor.getInt(0));
                eUser.setNameuser(cursor.getString(1));
                eUser.setLastname(cursor.getString(2));
            }

            // Cerrar conexión
            cursor.close();
        } catch(Exception error){
            error.printStackTrace();
        } finally {
            return eUser;
        }
    }

}
