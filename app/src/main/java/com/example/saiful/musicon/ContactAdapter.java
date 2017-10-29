package com.example.saiful.musicon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ContactAdapter extends ArrayAdapter {
    List list = new ArrayList();
    int[] images = {R.drawable.james, R.drawable.ayub_bachu, R.drawable.sumon, R.drawable.imran, R.drawable.nancy, R.drawable.habib};

    public ContactAdapter(Context context, int resource) {
        super(context, resource);
    }


    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.artist_layout, parent, false);
            TextView name = row.findViewById(R.id.name);
            TextView type = row.findViewById(R.id.type);
            ImageView img = row.findViewById(R.id.img);
            Contact contact = (Contact) this.getItem(position);
            name.setText(contact.getName());
            type.setText(contact.getType());
            img.setImageResource(images[position]);


        }
        return row;
    }
}
