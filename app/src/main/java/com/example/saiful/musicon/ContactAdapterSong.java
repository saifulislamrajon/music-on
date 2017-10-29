package com.example.saiful.musicon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ContactAdapterSong extends ArrayAdapter {
    List list = new ArrayList();

    public ContactAdapterSong(Context context, int resource) {
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
            row=inflater.inflate(R.layout.song_layout, parent, false);
            TextView song_name = row.findViewById(R.id.song_name);
            TextView artist_name = row.findViewById(R.id.artist_name);
            TextView album_name = row.findViewById(R.id.album_name);
            Contact contact = (Contact) this.getItem(position);
            song_name.setText(contact.getSong_name());
            artist_name.setText(contact.getArtist_name());
            album_name.setText(contact.getAlbum_name());


        }
        return row;
    }
}
