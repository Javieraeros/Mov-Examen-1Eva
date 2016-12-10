package es.iesnervion.fjruiz.mov_examen_1eva.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import es.iesnervion.fjruiz.mov_examen_1eva.R;
import es.iesnervion.fjruiz.mov_examen_1eva.model.Jugador;


public class SpinnerArrayAdapter extends ArrayAdapter {

    public SpinnerArrayAdapter(Context context, int resource, int textViewResourceID, Object[] objects) {
        super(context, resource, textViewResourceID, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        SpinnerViewHolder holder;
        //Aqui guardaremos o bien el peso, o bien la altura en formato string, nada de int
        String cadena = (String) getItem(position);

        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.filaspinner, parent, false);
            holder = new SpinnerViewHolder(v, R.id.numero);
            v.setTag(holder);
        } else {
            holder = (SpinnerViewHolder) v.getTag();
        }
        holder.getNumero().setText(cadena);
        return v;
    }



}
