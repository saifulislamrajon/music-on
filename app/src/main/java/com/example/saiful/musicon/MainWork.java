package com.example.saiful.musicon;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainWork extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    ContactAdapterSong contactAdapterSong;
    JSONObject jsonObject;
    JSONArray jsonArray;
    String artist_name;
    List list = new ArrayList();
    static MediaPlayer mp = null;
    static Uri uri;
    String path = "";
    SeekBar seekBar;
    Thread updateSeekBar;
    TextView songName;
    String message = "please you first play music";
    Button btPv, btFv, btPlay, btFF, btNxt;
    int position;
    //it's horizontal recyclerView work
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList<String> mDataset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_work);
        toolbar = (Toolbar) findViewById(R.id.toolbar_two);
        setSupportActionBar(toolbar);

        //it's horizontal recyclerView work
        mDataset = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            mDataset.add("img no :" + i);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                Toast.makeText(getApplicationContext(),id, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
//        adapter = new MainAdapter(mDataset, this);
        adapter=new MainAdapter(this);
        recyclerView.setAdapter(adapter);

        btPv = (Button) findViewById(R.id.btPv);
        btFv = (Button) findViewById(R.id.btFV);
        btPlay = (Button) findViewById(R.id.btPlay);
        btFF = (Button) findViewById(R.id.btFF);
        btNxt = (Button) findViewById(R.id.btNxt);
        songName = (TextView) findViewById(R.id.songName);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        updateSeekBar = new Thread() {
            @Override
            public void run() {
                super.run();
                int totalDuration = mp.getDuration();
                int currentPosition = 0;
                while (currentPosition < totalDuration) {
                    try {
                        sleep(1000);

                        currentPosition = mp.getCurrentPosition();
                        seekBar.setProgress(currentPosition);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        listView = (ListView) findViewById(R.id.listView2);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ContactSong contactSong = (ContactSong) getItem(i);
                path = contactSong.getPath();
                String name = path.replace("http://saifulrajoncom.000webhostapp.com/music_folder/", "");
                uri = Uri.parse(path);
                if (mp != null) {
                    Log.d("mediaPlayer", "why don't work this music player");
                    mp.stop();
                    mp.release();
                }

                mp = MediaPlayer.create(getApplicationContext(), uri);
                songName.setText(name);
                mp.start();
                /*if(mp.isPlaying()){
                    btPlay.setText(">");
                }else{
                    btPlay.setText("||");
                }*/
                seekBar.setMax(mp.getDuration());
                //updateSeekBar.start();


                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        /*mp.seekTo(i);
                        seekBar.setProgress(i);*/
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        mp.seekTo(seekBar.getProgress());
                    }
                });
            }
        });


        contactAdapterSong = new ContactAdapterSong(this, R.layout.song_layout);
        listView.setAdapter(contactAdapterSong);

        artist_name = getIntent().getStringExtra("common");

        getJson();

    }

    public void getJson() {
        new BackgroundTask().execute();
    }


    class BackgroundTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {

            String line;
            String songlist_url = "http://saifulrajoncom.000webhostapp.com/mmusicget.php";
            try {
                URL url = new URL(songlist_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("artist_name", "UTF-8") + "=" + URLEncoder.encode(artist_name, "UTF-8");
//                String post_data = URLEncoder.encode("artist_name", "UTF-8") + "=" + URLEncoder.encode("james", "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "/n");
                }
                inputStream.close();
                bufferedReader.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
//            super.onPostExecute(aVoid);
            if (result != null) {
                try {
                    jsonObject = new JSONObject(result);
                    jsonArray = jsonObject.getJSONArray("server_response");
                    String song_name, artist_name, album_name, path;
                    int count = 0;
                    while (count < jsonArray.length()) {
                        JSONObject jo = jsonArray.getJSONObject(count);
                        song_name = jo.getString("song_name");
                        artist_name = jo.getString("artist_name");
                        album_name = jo.getString("album_name");
                        path = jo.getString("path");
                        Contact contact = new Contact(song_name, artist_name, album_name, path);
                        ContactSong contactSong = new ContactSong(path);
                        list.add(contactSong);
                        contactAdapterSong.add(contact);
                        count++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), "data not found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void control(View view) {
        switch (view.getId()) {
            case R.id.btPlay:
                if (mp == null) {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                } else {
                    if (mp.isPlaying()) {
                        btPlay.setText(">");
                        mp.pause();
                    } else {
                        btPlay.setText("||");
                        mp.start();
                    }
                }

                break;
            case R.id.btFF:
                if (mp == null) {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                } else {
                    mp.seekTo(mp.getCurrentPosition() + 5000);
                }

                break;
            case R.id.btFV:
                if (mp == null) {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                } else {
                    mp.seekTo(mp.getCurrentPosition() - 5000);
                }


                break;
            case R.id.btNxt:
                if (mp == null) {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                } else {
                    mp.stop();
                    mp.release();
                    position = (position + 1) % list.size();
                    ContactSong contactSong = (ContactSong) getItem(position);
                    path = contactSong.getPath();
                    String name = path.replace("http://saifulrajoncom.000webhostapp.com/music_folder/", "");
                    uri = Uri.parse(path);
                    mp = MediaPlayer.create(getApplicationContext(), uri);
                    songName.setText(name);
                    mp.start();
                    seekBar.setMax(mp.getDuration());
                }
                break;
            case R.id.btPv:
                if (mp == null) {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                } else {
                    mp.stop();
                    mp.release();
                    position = (position - 1 < 0) ? list.size() - 1 : position - 1;
                    ContactSong contactSong = (ContactSong) getItem(position);
                    path = contactSong.getPath();
                    String name = path.replace("http://saifulrajoncom.000webhostapp.com/music_folder/", "");
                    uri = Uri.parse(path);
                    mp = MediaPlayer.create(getApplicationContext(), uri);
                    songName.setText(name);
                    mp.start();
                    seekBar.setMax(mp.getDuration());
                }

                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edittext_search_bar, menu);
        MenuItem menuItem = menu.findItem(R.id.action_bar);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public Object getItem(int position) {
        return list.get(position);
    }

}
