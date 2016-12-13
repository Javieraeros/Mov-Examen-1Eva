package es.iesnervion.fjruiz.mov_examen_1eva.controller;


import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import es.iesnervion.fjruiz.mov_examen_1eva.model.Jugador;
import es.iesnervion.fjruiz.mov_examen_1eva.model.MiOOS;

public class FicheroController {
    //region Atributos

    private File carpeta;
    private File fichero;
    private Context c;

    //endregion

    //region Constructores

    /**
     * Constructor que toma el fichero por defecto.
     */
    public FicheroController(Context c){
        this.c=c;
        fichero=new File(c.getFilesDir(),"fichero.dat");
    }

    /**
     * Método que devuelve todos los jugadores de un fichero
     * @return
     */
    public Vector<Jugador> recuperaJugadores(){
        FileInputStream leer=null;
        ObjectInputStream in=null;
        Vector<Jugador> devolver=new Vector<>(10,0);
        try{
            leer=new FileInputStream(fichero);
            in=new ObjectInputStream(leer);
            Jugador j;
            j=(Jugador) in.readObject();
            while (j!=null){
                devolver.add(j);
                j=(Jugador) in.readObject();
            }
        }catch (ClassNotFoundException e) {
            //ToDo cambiar
            Toast toast=Toast.makeText(c,e.toString(), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }catch(EOFException e){
            //ToDo cambiar
            Toast toast=Toast.makeText(c,e.toString(), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        } catch (IOException e) {
            //ToDo cambiar
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
            //ToDo Cambiar
            Log.e("Error",e.toString());

        }finally{
            if(escribeBin!=null){
                try {
                    escribeBin.close();
                } catch (IOException e) {
                    //ToDo Cambiar
                    Log.e("Error",e.toString());
                }
            }
            if(ficheroBin!=null){
                try {
                    ficheroBin.close();
                } catch (IOException e) {
                    //ToDo Cambiar
                    Log.e("Error",e.toString());
                }
            }
        }
    }
}
