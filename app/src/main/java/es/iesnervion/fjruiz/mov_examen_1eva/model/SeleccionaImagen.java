package es.iesnervion.fjruiz.mov_examen_1eva.model;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.Vector;

import es.iesnervion.fjruiz.mov_examen_1eva.MyApplication;
import es.iesnervion.fjruiz.mov_examen_1eva.R;
import es.iesnervion.fjruiz.mov_examen_1eva.controller.ImageAdapter;

public class SeleccionaImagen extends AppCompatActivity implements GridView.OnItemClickListener {

    private int idSeleccionado;
    private ImageAdapter adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecciona_imagen);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        adaptador=new ImageAdapter(this);
        gridview.setAdapter(adaptador);

        gridview.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent returnIntent = new Intent();
        //Utilizo esto porque view.getId() no me funciona
        idSeleccionado=adaptador.getItem(position);
        returnIntent.putExtra("imagen",idSeleccionado);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
}
