package com.example.erronka03;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import java.util.List;

public class ProduktuaAdapter extends ArrayAdapter<Produktua> {
    public ProduktuaAdapter(Context context, List<Produktua> produktuak) {
        super(context, 0, produktuak);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_produktua, parent, false);
        }

        Produktua produktua = getItem(position);

        ImageView imgView = convertView.findViewById(R.id.imgView);
        TextView text1 = convertView.findViewById(R.id.text1);
        TextView text2 = convertView.findViewById(R.id.text2);

        text1.setText(produktua.getIzena());
        text2.setText("Prezioa: " + produktua.getPrezioa() + "€");
        int imageId = getContext().getResources().getIdentifier(produktua.getIrudia().replace(".jpg", ""), "drawable", getContext().getPackageName());
        imgView.setImageResource(imageId);

        return convertView;
    }
}



