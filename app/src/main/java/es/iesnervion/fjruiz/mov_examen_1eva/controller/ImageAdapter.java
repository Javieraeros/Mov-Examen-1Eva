package es.iesnervion.fjruiz.mov_examen_1eva.controller;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.Vector;

import es.iesnervion.fjruiz.mov_examen_1eva.MyApplication;import android.app.Application;
import es.iesnervion.fjruiz.mov_examen_1eva.model.Jugador;


public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    //Aquí guardaremos TODAS las imagenes
    private Vector<Integer> imagenesOriginal = new Vector<>(25, 0);
    //Aquí solo guardaremos las imagenes que vamos a mostrar.
    private Vector<Integer> imagenesMostrar = new Vector<>(25, 0);
    private Vector<Integer> imagenesEliminar = new Vector<>(25, 0);

    private Vector<Jugador> vectorJugadores=new Vector<>(10,0);
    public ImageAdapter(Context c) {
        mContext = c;
        //Recuperamos todas las imagenes
        for (int i = 0; i < 25; i++) {
            if (i < 10) {
                imagenesOriginal.add(c.getResources().getIdentifier("jugador0" + i, "drawable", c.getPackageName()));
            } else {
                imagenesOriginal.add(c.getResources().getIdentifier("jugador" + i, "drawable", c.getPackageName()));
            }
        }
        //Recuperamos las imagenes que no vamos a mostrar porque ya las tenemos cogidas
        vectorJugadores=((MyApplication) c.getApplicationContext()).getVectorJugadores();
        for(int j=0;j<vectorJugadores.size();j++){
            imagenesEliminar.add(vectorJugadores.elementAt(j).getImagen());
        }

        //Por último, quitamos de las imagenes a mostrar, las que no queremos mostrar
        imagenesMostrar=imagenesOriginal;
        for(int k=0;k<imagenesEliminar.size();k++){
            imagenesMostrar.remove(imagenesEliminar.elementAt(k));
        }
    }

    public int getCount() {
        return imagenesMostrar.size();
    }

    public Integer getItem(int position) {
        return imagenesMostrar.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(imagenesMostrar.get(position));
        return imageView;
    }
}