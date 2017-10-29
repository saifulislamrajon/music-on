package com.example.saiful.musicon;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ArtistList extends AppCompatActivity {
    Toolbar toolbar;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ListView listView;
    ContactAdapter contactAdapter;
    MainworkFragmentPlatform mainworkFragmentPlatform;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar_one);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listView);
        contactAdapter = new ContactAdapter(this, R.layout.artist_layout);
        listView.setAdapter(contactAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
//                        startActivity(new Intent(getApplicationContext(),MainWork.class).putExtra("common","james"));
                        startActivity(new Intent(getApplicationContext(), MainworkFragmentPlatform.class).putExtra("common", "james"));
                        break;
                    case 1:
//                        startActivity(new Intent(getApplicationContext(),MainWork.class).putExtra("common","ayub bachchu"));
                        startActivity(new Intent(getApplicationContext(), MainworkFragmentPlatform.class).putExtra("common", "ayub bachchu"));
                        break;
                    case 2:
//                        startActivity(new Intent(getApplicationContext(),MainWork.class).putExtra("common","sumon"));
                        startActivity(new Intent(getApplicationContext(), MainworkFragmentPlatform.class).putExtra("common", "sumon"));
                        break;
                    case 3:
//                        startActivity(new Intent(getApplicationContext(),MainWork.class).putExtra("common","imran"));
                        startActivity(new Intent(getApplicationContext(), MainworkFragmentPlatform.class).putExtra("common", "imran"));
                        break;
                    case 4:
//                        startActivity(new Intent(getApplicationContext(),MainWork.class).putExtra("common","nancy"));
                        startActivity(new Intent(getApplicationContext(), MainworkFragmentPlatform.class).putExtra("common", "nancy"));
                        break;
                    case 5:
//                        startActivity(new Intent(getApplicationContext(),MainWork.class).putExtra("common","habib"));
                        startActivity(new Intent(getApplicationContext(), MainworkFragmentPlatform.class).putExtra("common", "habib"));
                        break;
                }
                /*String data = String.valueOf(adapterView.getItemAtPosition(i).toString());
                Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainWork.class));*/

            }
        });
        getJson();
    }

    public void getJson() {
        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String json_string;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String artistList_url = "http://saifulrajoncom.000webhostapp.com/martistget.php";
            try {
                URL url = new URL(artistList_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while ((json_string = bufferedReader.readLine()) != null) {
                    stringBuilder.append(json_string + "\n");
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
                    int count = 0;
                    String name, type;
                    while (count < jsonArray.length()) {
                        JSONObject jo = jsonArray.getJSONObject(count);
                        name = jo.getString("name");
                        type = jo.getString("type");

                        Contact contact = new Contact(name, type);
                        contactAdapter.add(contact);
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

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edittext_search_bar, menu);
        MenuItem item = menu.findItem(R.id.action_bar);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                contactAdapter.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }*/

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,MainActivity.class));
    }*/

}
