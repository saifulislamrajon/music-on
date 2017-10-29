package com.example.saiful.musicon;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class MainworkFragmentPlatform extends AppCompatActivity implements Communicator {
    Toolbar toolbar;
    String artistName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainwork_fragment_platform);
        toolbar = (Toolbar) findViewById(R.id.toolbar_two);
        setSupportActionBar(toolbar);

/*        artistList to mainworkfragmentplatform to allsong
        artistList to mainworkfragmentplatform to singerdetails*/
        artistName = getIntent().getStringExtra("common");
        FragmentManager fragmentManager = getFragmentManager();
        AllSong allSong = (AllSong) fragmentManager.findFragmentById(R.id.fragment2);
        SingerDetails singerDetails = (SingerDetails) fragmentManager.findFragmentById(R.id.fragment);
        allSong.setData(artistName);
        singerDetails.setData(artistName);

    }

    @Override
    public void respond(String name, String path, ArrayList list) {
        FragmentManager fragmentManager = getFragmentManager();
        Player player = (Player) fragmentManager.findFragmentById(R.id.fragment4);
        player.setSongName(name, path, list);

    }

    @Override
    public void respond2(String name) {
        FragmentManager fragmentManager = getFragmentManager();
        AllSong allSong = (AllSong) fragmentManager.findFragmentById(R.id.fragment2);
        allSong.setData(name);
    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,ArtistList.class));
        finish();
    }*/

}
