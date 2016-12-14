package es.iesnervion.fjruiz.mov_examen_1eva.controller;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import es.iesnervion.fjruiz.mov_examen_1eva.R;
import es.iesnervion.fjruiz.mov_examen_1eva.model.Jugador;

//ToDo Documentar e intentar usar singleton
public class FicheroController {
    //region Atributos

    private File fichero;
    private SharedPreferences ficheroOpciones;
    private static Context c;

    //endregion

    //region Constructores

    /**
     * Constructor que toma el fichero por defecto.
     */
    public FicheroController(Context c){
        this.c=c;
        fichero=new File(c.getFilesDir(),"fichero.dat");
        ficheroOpciones=c.getSharedPreferences(c.getResources().getString(R.string.opciones),
                Context.MODE_PRIVATE);
    }

    public FicheroController(){
        fichero=new File(c.getFilesDir(),"fichero.dat");
        ficheroOpciones=c.getSharedPreferences(c.getResources().getString(R.string.opciones),
                Context.MODE_PRIVATE);
    }

    //endregion

    //region Métodos
    /**
     * Método que devuelve todos los jugadores de un fichero
     * @return
     */
    public Vector<Jugador> recuperaJugadores(){
        FileInputStream leer=null;
        ObjectInputStream in=null;
        Vector<Jugador> devolver=new Vector<>(10,0);
        int cantidadJugadores;
        cantidadJugadores=ficheroOpciones.getInt(c.getResources().getString(R.string.cantidadJugadores),0);
        try{
            leer=new FileInputStream(fichero);
            in=new ObjectInputStream(leer);
            Jugador j;
            for(int i=0;i<cantidadJugadores;i++){
                j=(Jugador) in.readObject();
                devolver.add(j);
            }
        }catch (ClassNotFoundException e) {
            Log.e("Error Clase",e.toString());
        }catch(EOFException e){
            Log.e("Fin de fichero",e.toString());
        } catch (IOException e) {
            Log.e("Error",e.toString());
        }if(in!=null){
            try {
                in.close();
            } catch (IOException e) {
                Log.e("Error",e.toString());
            }
        }
        if(leer!=null){
            try {
                leer.close();
            } catch (IOException e) {
                Log.e("Error",e.toString());
            }
        }
        return devolver;
    }
    /*
	 * Interfaz
	 * Cabecera:public void escribeJugadores(Vector<Jugador> jugadores)
	 * Proceso:Escribe todos los jugadores de un vector en el fichero, sobreescribiendolo
	 * Precondiciones:Jugador parcelable
	 * Entrada:1 vector de jugadores
	 * Salida:Guarda en el fichero el Vector de Jugadores
	 * Entrada/Salida:Nada
	 * Postcondiciones:El fichero con lso jugadores guardados
	 */

    public void escribeJugadores(Vector<Jugador> jugadores){
        FileOutputStream ficheroBin=null;
        ObjectOutputStream escribeBin=null;
        try{
            ficheroBin=new FileOutputStream(fichero,false);
            escribeBin=new ObjectOutputStream(ficheroBin);
            for(int i=0;i<jugadores.size();i++){
                escribeBin.writeObject(jugadores.elementAt(i));
            }
            escribeBin.close();
            ficheroBin.close();
        }catch(IOException e){
            Log.e("Error",e.toString());

        }finally{
            if(escribeBin!=null){
                try {
                    escribeBin.close();
                } catch (IOException e) {
                    Log.e("Error",e.toString());
                }
            }
            if(ficheroBin!=null){
                try {
                    ficheroBin.close();
                } catch (IOException e) {
                    Log.e("Error",e.toString());
                }
            }
            SharedPreferences.Editor edit=ficheroOpciones.edit();

            edit.putInt(c.getResources().getString(R.string.cantidadJugadores),jugadores.size());
            edit.apply();
        }
    }

    public int recuperaId(){
        /*FileInputStream leer=null;
        DataInputStream in=null;
        int id=0;
        try{
            leer=new FileInputStream(ficheroId);
            in=new DataInputStream(leer);
            id=in.readInt();
        }catch(EOFException e){
            /*Toast toast=Toast.makeText(c,e.toString(), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        } catch (IOException e) {
            Toast toast=Toast.makeText(c,e.toString(), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }if(in!=null){
            try {
                in.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        if(leer!=null){
            try {
                leer.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }*/
        int id;
        id=ficheroOpciones.getInt(c.getResources().getString(R.string.id),0);

        return id;
    }

    public void escribeId(int id){
        /*FileOutputStream ficheroBin=null;
        DataOutputStream escribeBin=null;
        try{
            ficheroBin=new FileOutputStream(ficheroId,false);
            escribeBin=new DataOutputStream(ficheroBin);
            //No usar write!!!
            escribeBin.writeInt(id);
            escribeBin.close();
            ficheroBin.close();
        }catch(IOException e){
            Log.e("Error",e.toString());

        }finally{
            if(escribeBin!=null){
                try {
                    escribeBin.close();
                } catch (IOException e) {
                    Log.e("Error",e.toString());
                }
            }
            if(ficheroBin!=null){
                try {
                    ficheroBin.close();
                } catch (IOException e) {
                    Log.e("Error",e.toString());
                }
            }
        }*/

        //Version 2.0
        SharedPreferences.Editor edit=ficheroOpciones.edit();

        edit.putInt(c.getResources().getString(R.string.id),id);
        edit.apply();
    }

    //endregion
}
