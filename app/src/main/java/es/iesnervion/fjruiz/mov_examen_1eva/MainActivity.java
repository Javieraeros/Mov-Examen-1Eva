package es.iesnervion.fjruiz.mov_examen_1eva;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Vector;

import es.iesnervion.fjruiz.mov_examen_1eva.controller.JugadorArrayAdapter;
import es.iesnervion.fjruiz.mov_examen_1eva.model.Jugador;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, AdapterView.OnItemClickListener,
                    PopupMenu.OnMenuItemClickListener{

    private Vector<Jugador> arrayjugadores;

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        Jugador jugador=new Jugador("Pene",R.drawable.jugador00,"Alero",90,150);
        Jugador jugador2=new Jugador("Peneeeee",R.drawable.jugador00,"Alero",90,150);
        ((MyApplication) getApplication()).setJugador(jugador);
        ((MyApplication) getApplication()).setJugador(jugador);
        ((MyApplication) getApplication()).setJugador(jugador2);
        ((MyApplication) getApplication()).setJugador(jugador);
        ((MyApplication) getApplication()).setJugador(jugador);
        ((MyApplication) getApplication()).setJugador(jugador);
        ((MyApplication) getApplication()).setJugador(jugador);
        ((MyApplication) getApplication()).setJugador(jugador);
        ((MyApplication) getApplication()).setJugador(jugador);
        ((MyApplication) getApplication()).setJugador(jugador);
        arrayjugadores=((MyApplication) getApplication()).getVectorJugadores();

    }

    /**
     * Aquí hago añado el adapter al list view, para que cuando agreguemos/editemos un jugador
     * nos aparezca en el listview, debido al ciclo de vida de las aplicaciones.
     */
    @Override
    public void onResume(){
        super.onResume();
        JugadorArrayAdapter jaa=new JugadorArrayAdapter(this,R.layout.fila,R.id.texto,arrayjugadores.toArray());
        lv.setAdapter(jaa);
        lv.setOnItemClickListener(this);
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
     * Método qeu controla cuando pulsamos un jugador del list view
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


        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }


    /**
     * Selección de una posibilidad del menú
     * @param item
     * @return
     */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if(item.getItemId()==R.id.borrar){

            ((MyApplication) getApplication()).eliminaJugador(posicionPulsada);

            //Llamo a onResume para que recargue el array de jugadores
            onResume();
        }else{
            Intent editar=new Intent(this,DetallesJugador.class);
            editar.putExtra("Jugador",jugadorSeleccionado);
            startActivityForResult(editar,0);
        }
        return true;
    }


    /**AutoGenerado pro Android Studio
     *
     */

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

}
