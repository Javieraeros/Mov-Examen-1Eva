package es.iesnervion.fjruiz.mov_examen_1eva;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Vector;

import es.iesnervion.fjruiz.mov_examen_1eva.controller.FicheroController;
import es.iesnervion.fjruiz.mov_examen_1eva.controller.JugadorArrayAdapter;
import es.iesnervion.fjruiz.mov_examen_1eva.model.Jugador;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener,
                    AdapterView.OnItemClickListener,
                    AdapterView.OnItemLongClickListener,
                    ActionMode.Callback{

    private FicheroController miFichero;
    private Vector<Jugador> arrayjugadores;
    private FloatingActionButton fab;
    private ListView lv;
    private ActionMode mActionMode;

    //Aquí guardamos el item que hemos pulsado para borrar/editar
    private int posicionPulsada;

    //Aquí guardamos el jugador que vamos a editar
    private Jugador jugadorSeleccionado;


    private ActionMode.Callback mActionModeCallback=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv=(ListView) findViewById(R.id.list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        miFichero=new FicheroController(this);
        arrayjugadores=miFichero.recuperaJugadores();

        lv.setOnItemClickListener(this);


    }

    /**
     * Aquí hago añado el adapter al list view, para que cuando agreguemos/editemos un jugador
     * nos aparezca en el listview, debido al ciclo de vida de las aplicaciones.
     */
    @Override
    public void onResume(){
        super.onResume();
        arrayjugadores=miFichero.recuperaJugadores();
        JugadorArrayAdapter jaa=new JugadorArrayAdapter(this,R.layout.fila,R.id.texto,arrayjugadores.toArray());
        lv.setAdapter(jaa);
        //lv.setOnItemClickListener(this);
        lv.setOnItemLongClickListener(this);
        //Aunque lo tenemos controlado para que no se puedan insertar más de 10 jugadores
        //Es mejor no darle la opción al usuario de que pueda insertarlo
        if(arrayjugadores.size()>=10){
            fab.setEnabled(false);
            fab.setVisibility(View.INVISIBLE);
        }else{
            fab.setEnabled(true);
            fab.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Click de botón flotante
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent crear=new Intent(this,DetallesJugador.class);
        startActivity(crear);
    }

    /**
     * Método qeu controla cuando dejamos pulsado un jugador del list view
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    //ToDo cambiar oclor de seleccionado
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        //recuperamos la posición y el jugador pulsado
        posicionPulsada=position;
        jugadorSeleccionado=arrayjugadores.elementAt(position);
        if (mActionMode != null) {
            return false;
        }

        // Start the CAB using the ActionMode.Callback defined above
        mActionMode = this.startSupportActionMode(mActionModeCallback);
        view.setSelected(true);
        return true;
    }

    /**
     * Método al que llamamaos cuando pulsamos un jugador de listview
     * @param parent
     * @param view
     * @param position
     * @param id
     */

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //recuperamos la posición y el jugador pulsado
        posicionPulsada=position;
        jugadorSeleccionado=arrayjugadores.elementAt(position);
        Intent editar=new Intent(this,DetallesJugador.class);
        editar.putExtra("jugador",jugadorSeleccionado);
        //Añado la posición que hemos pulsado aquí,porque al utilizar vector.indexOf() no me detecta
        //bien la posición
        //No funciona porque tiene que ser parcelable... :(
        //editar.putExtra("posicion",posicionPulsada);
        startActivity(editar);
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        //Puesto que solo tenemos un botón, no es necesario que distingamos
        //por botón.
        arrayjugadores.remove(posicionPulsada);
        miFichero.escribeJugadores(arrayjugadores);
        //Llamo a onResume para que recargue el array de jugadores
        onResume();

        mode.finish();
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mActionMode=null;
    }
}
