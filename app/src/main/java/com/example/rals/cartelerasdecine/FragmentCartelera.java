package com.example.rals.cartelerasdecine;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rals1_000 on 27/11/2014.
 */
public class FragmentCartelera extends Fragment{

    private ListView listaPeliculas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cartelera_layout, container, false);

        listaPeliculas = (ListView)view.findViewById(R.id.lista_cartelera);
        listaPeliculas.setAdapter(new Miadapter(MainActivity.cartelera));

        return view;
    }

    class Miadapter extends ArrayAdapter<Pelicula>{

        public Miadapter(ArrayList<Pelicula> objects) {
            super(getActivity(), R.layout.lista_adapter, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;

            if (convertView == null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.lista_adapter, null);

                holder = new ViewHolder();
                holder.lblTitulo = (TextView)convertView.findViewById(R.id.lbl_titulo_pelicula);
                holder.lblSinopsis = (TextView)convertView.findViewById(R.id.lbl_sinopsis);
                holder.imgCartel = (ImageView)convertView.findViewById(R.id.img_pelicula);

                convertView.setTag(holder);
            }
            else{
                holder = (ViewHolder)convertView.getTag();
            }

            holder.lblTitulo.setText(MainActivity.cartelera.get(position).getTitulo());
            holder.lblSinopsis.setText(MainActivity.cartelera.get(position).getSinopsis());
            holder.imgCartel.setImageBitmap(MainActivity.cartelera.get(position).getCartel());
            return convertView;
        }
    }

    static class ViewHolder{
        TextView lblTitulo, lblSinopsis;
        ImageView imgCartel;
    }

}
