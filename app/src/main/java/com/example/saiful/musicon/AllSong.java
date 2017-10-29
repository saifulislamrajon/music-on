package com.example.saiful.musicon;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
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


public class AllSong extends Fragment {
    ListView listView3;
    ContactAdapterSong contactAdapterSong;
    String artist_name;
    String path = "";
    JSONObject jsonObject;
    JSONArray jsonArray;
    List list = new ArrayList();
    static MediaPlayer mediaPlayer = null;
    static Uri uri;
    Communicator communicator;
    SeekBar seekBar;
    int size;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.allsong, container, false);
//        https://stackoverflow.com/questions/16212469/send-string-from-an-activity-to-a-fragment-of-another-activity
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        communicator = (Communicator) getActivity();
        listView3 = getActivity().findViewById(R.id.listView3);
        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ContactSong contactSong = (ContactSong) getItem(i);
                path = contactSong.getPath();
                String name = path.replace("http://saifulrajoncom.000webhostapp.com/music_folder/", "");
                size = list.size();
                communicator.respond(name, path, (ArrayList) list);
                /*uri = Uri.parse(path);
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
                mediaPlayer = MediaPlayer.create(getActivity(), uri);
                mediaPlayer.start();*/
            }
        });

        contactAdapterSong = new ContactAdapterSong(getActivity(), R.layout.song_layout);
        listView3.setAdapter(contactAdapterSong);
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
            String songlist_url = "http://saifulrajoncom.000webhostapp.com/mmusicget.php";
            String line;
            try {
                URL url = new URL(songlist_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String postData = URLEncoder.encode("artist_name", "UTF-8") + "=" + URLEncoder.encode(artist_name, "UTF-8");
                bufferedWriter.write(postData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-9"));
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
                        contactAdapterSong.add(contact);
                        list.add(contactSong);
                        count++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getActivity(), "data not found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setData(String s) {
        artist_name = s;
    }

    public Object getItem(int position) {
        return list.get(position);
    }
}
