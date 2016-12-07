package es.iesnervion.fjruiz.mov_examen_1eva;

import android.app.Application;

import java.util.Vector;

import es.iesnervion.fjruiz.mov_examen_1eva.model.Jugador;

public class MyApplication extends Application {
    Vector<Jugador> arrayJugadores=new Vector<>(10,0);

    public MyApplication(){

    }

    public MyApplication(Vector<Jugador> arrayJugadores) {
        this.arrayJugadores = arrayJugadores;
    }

    public Vector<Jugador> getVectorJugadores() {
        return arrayJugadores;
    }

    public void setVectorJugadores(Vector<Jugador> arrayJugadores) {
        this.arrayJugadores = arrayJugadores;
    }

    public Jugador getJugador(int posicion){
        return arrayJugadores.elementAt(posicion);
    }

    public void setJugador(Jugador jugador){
        if(arrayJugadores.size()<11){
            arrayJugadores.add(jugador);
        }
    }

    /**
     * Método que sobreescribe un jugador en una posición dada
     * @param j
     * @param posicion
     */
    public void sobreEscribeJugador(Jugador j,int posicion){
        arrayJugadores.setElementAt(j,posicion);
    }

    public void eliminaJugador(int posicion){
        arrayJugadores.remove(posicion);
    }
}
