package es.iesnervion.fjruiz.mov_examen_1eva.controller;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by fjruiz on 27/10/16.
 */

public class JugadorViewHolder {
    TextView nombre;
    TextView info;
    ImageView img;

    public JugadorViewHolder(View row, int nombreId,int infoId,int imgId) {
        this.nombre = (TextView) row.findViewById(nombreId);
        this.img = (ImageView) row.findViewById(imgId);
        this.info=(TextView) row.findViewById(infoId);
    }

    public TextView getNombre() {
        return this.nombre;
    }

    public TextView getInfo() {
        return this.info;
    }

    public ImageView getImg() {
        return this.img;
    }
}
