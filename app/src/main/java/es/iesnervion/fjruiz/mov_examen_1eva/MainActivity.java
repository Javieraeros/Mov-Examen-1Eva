package es.iesnervion.fjruiz.mov_examen_1eva;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Vector;

import es.iesnervion.fjruiz.mov_examen_1eva.controller.FicheroController;
import es.iesnervion.fjruiz.mov_examen_1eva.controller.JugadorArrayAdapter;
import es.iesnervion.fjruiz.mov_examen_1eva.model.Jugador;

//ToDo Cuando pinche sobre un jugador muestre editar, cuando deje pulsado, menú contextual
//https://developer.android.com/guide/topics/ui/menus.html?hl=es-419#CAB
public class MainActivity extends AppCompatActivity
        implements View.OnClickListener,
                    AdapterView.OnItemClickListener,
                    AdapterView.OnItemLongClickListener,
                    ActionMode.Callback{

    private FicheroController miFichero;
    private Vector<Jugador> arrayjugadores;
    private FloatingActionButton fab;
    private ListView lv;

    //Aquí guardamos el item que hemos pulsado para borrar/editar
    private int posicionPulsada;

    //Aquí guardamos el jugador que vamos a editar
    private Jugador jugadorSeleccionado;
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
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        //recuperamos la posición y el jugador pulsado
        posicionPulsada=position;
        jugadorSeleccionado=arrayjugadores.elementAt(position);

        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
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
        Intent editar=new Intent(this,DetallesJugador.class);
        editar.putExtra("jugador",jugadorSeleccionado);
        //Añado la posición que hemos pulsado aquí,porque al utilizar vector.indexOf() no me detecta
        //bien la posición
        //No funciona porque tiene que ser parcelable... :(
        //editar.putExtra("posicion",posicionPulsada);
        startActivity(editar);
    }

    /**
     * Selección de una posibilidad del menú
     * @param item
     * @return
     */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if(item.getItemId()==R.id.borrar){

            arrayjugadores.remove(posicionPulsada);

            miFichero.escribeJugadores(arrayjugadores);
            //Llamo a onResume para que recargue el array de jugadores
            onResume();
        }else{
            Intent editar=new Intent(this,DetallesJugador.class);
            editar.putExtra("jugador",jugadorSeleccionado);
            //Añado la posición que hemos pulsado aquí,porque al utilizar vector.indexOf() no me detecta
            //bien la posición
            //No funciona porque tiene que ser parcelable... :(
            //editar.putExtra("posicion",posicionPulsada);
            startActivity(editar);
        }
        return true;
    }

    //ToDo documentar y acabar
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }



    //region AutoGenerado por Android Studio

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //endregion
}
