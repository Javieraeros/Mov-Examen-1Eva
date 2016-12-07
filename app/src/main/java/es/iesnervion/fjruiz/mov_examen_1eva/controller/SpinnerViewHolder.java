package es.iesnervion.fjruiz.mov_examen_1eva.controller;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * CLo usaremos para ambos spinner
 */

public class SpinnerViewHolder {
    TextView numero;

    public SpinnerViewHolder(View row, int numeroId) {
        this.numero = (TextView) row.findViewById(numeroId);
    }

    public TextView getNumero() {
        return this.numero;
    }

}
