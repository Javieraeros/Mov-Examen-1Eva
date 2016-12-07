package es.iesnervion.fjruiz.mov_examen_1eva;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.Vector;

import es.iesnervion.fjruiz.mov_examen_1eva.controller.SpinnerArrayAdapter;
import es.iesnervion.fjruiz.mov_examen_1eva.model.Jugador;

public class DetallesJugador extends AppCompatActivity
        implements View.OnClickListener,AdapterView.OnItemSelectedListener{

    //Si queremos editar un jugador, lo recogemos en el bundle
    private Bundle datos;
    private Jugador jugadorEditar,jugadorInsertar;
    private String nombreJugador, posicion;
    private int pesoJugador,alturaJugador,imagenJugador;

    //Aquí guardamos si lo que vamos a hacer es editar un jugador, o a añadir uno nuevo
    private boolean editar;

    //Aqui guardamos si todos los campos son correctos
    private boolean imagenSeleccionada=false;
    private boolean alturaSeleccionada=false;
    private boolean pesoSeleccionado=false;

    //Objetos del xml
    private EditText nombre;
    private ImageView imagen;
    private RadioGroup rg;
    private RadioButton rbBase,rbEscolta,rbAlero,rbAla,rbPivot;
    private Spinner spAltura,spPeso;

    //Array compartido
    private Vector<Jugador> vectorJugadores;

    private String[] arrayAltura=new String[101];
    private String[] arrayPeso=new String[206];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_jugador);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        vectorJugadores=((MyApplication) getApplication()).getVectorJugadores();

        //Creamos los arrays de Altura y peso y los arrayadapter
        for(Integer i=150;i<=250;i++){
            arrayAltura[i-150]=i.toString();
        }
        for(Integer i=45;i<=250;i++){
            arrayPeso[i-45]=i.toString();
        }

        SpinnerArrayAdapter saaAltura=new SpinnerArrayAdapter(this,R.layout.filaspinner,R.id.numero,arrayAltura);
        SpinnerArrayAdapter saaPeso=new SpinnerArrayAdapter(this,R.layout.filaspinner,R.id.numero,arrayPeso);

        //Asignamos lso objetos
        nombre=(EditText) findViewById(R.id.txtNombre);

        imagen=(ImageView) findViewById(R.id.SeleccionaImagen);

        rg=(RadioGroup) findViewById(R.id.rg);
        rbBase=(RadioButton) findViewById(R.id.rbBase);
        rbEscolta=(RadioButton) findViewById(R.id.rbEscolta);
        rbAlero=(RadioButton) findViewById(R.id.rbAlero);
        rbAla=(RadioButton) findViewById(R.id.rbAla);
        rbPivot=(RadioButton) findViewById(R.id.rbPivot);

        spAltura=(Spinner) findViewById(R.id.spAltura);
        spAltura.setAdapter(saaAltura);
        spAltura.setOnItemSelectedListener(this);
        spPeso=(Spinner) findViewById(R.id.spPeso);
        spPeso.setAdapter(saaPeso);
        spPeso.setOnItemSelectedListener(this);

        //recogemos extra si hay
        if(getIntent().hasExtra("jugador")){
            datos = getIntent().getExtras();
            /*
            No se porqué pero no es el mismo objeto el que envía que el que recibe
             */
            jugadorEditar =datos.getParcelable("jugador");
            editar=true;
            //Le damos los valores.

            nombre.setText(jugadorEditar.getNombre());
            imagen.setImageResource(jugadorEditar.getImagen());
            imagenSeleccionada=true;

            switch (jugadorEditar.getPosicion()) {
                case "Base":
                    rbBase.setChecked(true);
                    break;
                case "Escolta":
                    rbEscolta.setChecked(true);
                    break;
                case "Alero":
                    rbAlero.setChecked(true);
                    break;
                case "Ala":
                    rbAla.setChecked(true);
                    break;
                case "Pivot":
                    rbPivot.setChecked(true);
                    break;
            }
            //ToDo Poner valores a spinners

        }else{
            editar=false;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //No usar view, porque devuelve la fila pulsada, no el spinner!!
        switch (parent.getId()) {
            case R.id.spPeso:
                pesoSeleccionado=true;
                pesoJugador=position+45;
                break;
            case R.id.spAltura:
                alturaSeleccionada=true;
                alturaJugador=position+150;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        switch (parent.getId()) {
            case R.id.spPeso:
                pesoSeleccionado=false;
                break;
            case R.id.spAltura:
                alturaSeleccionada=false;
                break;
        }
    }

    /**
     * Método que maneja cuando clickemos en la imagen
     * @param v
     */
    public void onClickImagen(View v){
        //ToDo Hacer que elija la imagen lanzando un intent
        //Recogiendo la imagen que seleecione el usuario del intent
        imagenSeleccionada=true;
        /*Intent seleccionaImagen=new Intent(this,SeleccionaImagen.class);
        startActivityForResult(seleccionaImagen,1);*/
    }



    /**
     * Método que maneja el botón flotante
     * @param v
     */
    @Override
    public void onClick(View v) {
        imagenSeleccionada=true;  //toDo borrar
        if(datosCorrectos()){
            nombreJugador=nombre.getText().toString();

            switch (rg.getCheckedRadioButtonId()){
                case R.id.rbAla:
                    posicion="Ala";
                    break;
                case R.id.rbAlero:
                    posicion="Alero";
                    break;
                case R.id.rbBase:
                    posicion="Base";
                    break;
                case R.id.rbEscolta:
                    posicion="Escolta";
                    break;
                case R.id.rbPivot:
                    posicion="Pivot";
                    break;
            }
            jugadorInsertar=new Jugador(nombreJugador,imagenJugador,posicion,alturaJugador,pesoJugador);
            //Si es un jugador el que vamos a editar, lo que hacemos es sustituir el antiguo por el nuevo
            if(editar){
                vectorJugadores.setElementAt(jugadorInsertar,vectorJugadores.indexOf(jugadorEditar));
            }else{
                vectorJugadores.add(jugadorInsertar);
            }
        }
        ((MyApplication) getApplication()).setVectorJugadores(vectorJugadores);
        finish();
    }

    /**
     * Método que devuelve si los datos introducidos son correctos
     * @return
     */
    private boolean datosCorrectos(){
        boolean resultado=false;
        if(nombre.getText().toString()!="" && imagenSeleccionada && alturaSeleccionada && pesoSeleccionado){
            resultado=true;
        }
        return resultado;
    }
}
