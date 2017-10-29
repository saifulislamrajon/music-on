package com.example.saiful.musicon;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import de.hdodenhof.circleimageview.CircleImageView;


public class SingerDetails extends Fragment {
    CircleImageView profile_image;
    TextView singer_name, singer_type, followers;
    Button follow;
    String artist;
//    String name = "saiful";
    String name;
    JSONObject jsonObject;
    JSONArray jsonArray;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.singerdetails, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        profile_image = getActivity().findViewById(R.id.profile_image);
        singer_name = getActivity().findViewById(R.id.singer_name);
        singer_type = getActivity().findViewById(R.id.singer_type);
        followers = getActivity().findViewById(R.id.followers);
        follow = getActivity().findViewById(R.id.follow);

//        SharedPreferences
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("fileName", Context.MODE_PRIVATE);
        name=sharedPreferences.getString("name","defaultValue");

        viewSetting();
    }

    public void viewSetting() {
        if (artist.equals("james")) {
            profile_image.setImageResource(R.drawable.james);
            singer_name.setText("james");
            singer_type.setText("Singer");
            getViewResult();
            followers.setText("00");
            getSearchResult();
            follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    follow.setText("followed");
                    follow.setTextColor(Color.parseColor("#FFFFFF"));
                    follow.setEnabled(false);
                    /*Toast.makeText(getActivity(), artistName, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(),name,Toast.LENGTH_SHORT).show();*/
                    String type = "follow";
                    BackgroundTask backgroundTask = new BackgroundTask(getActivity());
                    backgroundTask.execute(type, name, artist);

                }
            });
        } else if (artist.equals("ayub bachchu")) {
            profile_image.setImageResource(R.drawable.ayub_bachu);
            singer_name.setText("Ayub Bachchu");
            singer_type.setText("Singer");
            getViewResult();
            followers.setText("00");
            getSearchResult();
            follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    follow.setText("followed");
                    follow.setTextColor(Color.parseColor("#FFFFFF"));
                    follow.setEnabled(false);
                    String type = "follow";
                    BackgroundTask backgroundTask = new BackgroundTask(getActivity());
                    backgroundTask.execute(type, name, artist);
                }
            });
        } else if (artist.equals("sumon")) {
            profile_image.setImageResource(R.drawable.sumon);
            singer_name.setText("Sumon");
            singer_type.setText("Singer");
            getViewResult();
            followers.setText("00");
            getSearchResult();
            follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    follow.setText("followed");
                    follow.setTextColor(Color.parseColor("#FFFFFF"));
                    follow.setEnabled(false);
                    String type = "follow";
                    BackgroundTask backgroundTask = new BackgroundTask(getActivity());
                    backgroundTask.execute(type, name, artist);
                }
            });
        } else if (artist.equals("imran")) {
            profile_image.setImageResource(R.drawable.imran);
            singer_name.setText("Imran");
            singer_type.setText("Singer");
            getViewResult();
            followers.setText("00");
            getSearchResult();
            follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    follow.setText("followed");
                    follow.setTextColor(Color.parseColor("#FFFFFF"));
                    follow.setEnabled(false);
                    String type = "follow";
                    BackgroundTask backgroundTask = new BackgroundTask(getActivity());
                    backgroundTask.execute(type, name, artist);
                }
            });
        } else if (artist.equals("nancy")) {
            profile_image.setImageResource(R.drawable.nancy);
            singer_name.setText("Nancy");
            singer_type.setText("Singer");
            getViewResult();
            followers.setText("00");
            getSearchResult();
            follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    follow.setText("followed");
                    follow.setTextColor(Color.parseColor("#FFFFFF"));
                    follow.setEnabled(false);
                    String type = "follow";
                    BackgroundTask backgroundTask = new BackgroundTask(getActivity());
                    backgroundTask.execute(type, name, artist);
                }
            });
        } else if (artist.equals("habib")) {
            profile_image.setImageResource(R.drawable.habib);
            singer_name.setText("Habib");
            singer_type.setText("Singer");
            getViewResult();
            followers.setText("00");
            getSearchResult();
            follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    follow.setText("followed");
                    follow.setTextColor(Color.parseColor("#FFFFFF"));
                    follow.setEnabled(false);
                    String type = "follow";
                    BackgroundTask backgroundTask = new BackgroundTask(getActivity());
                    backgroundTask.execute(type, name, artist);
                }
            });
        }
    }

    public void getViewResult() {
        Log.d("Call test", "getViewResult()");
        String af = "allFollower";
        new FollowBackgroundTask().execute(af);
    }

    public void getSearchResult() {
        Log.d("Call test", "getSearchResult()");
        String fs = "followSearch";
        new FollowBackgroundTask().execute(fs);
    }

    class FollowBackgroundTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String followSearch_url = "http://saifulrajoncom.000webhostapp.com/mfollowsearch.php";
            String allFollower_url = "http://saifulrajoncom.000webhostapp.com/mallfollower.php";
            String check = params[0];
            if (check.equals("allFollower")) {
                try {
                    URL url = new URL(allFollower_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("artist", "UTF-8") + "=" + URLEncoder.encode(artist, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line + "/n");
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return stringBuilder.toString().trim();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (check.equals("followSearch")) {
                try {
                    URL url = new URL(followSearch_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" + URLEncoder.encode("artist", "UTF-8") + "=" + URLEncoder.encode(artist, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String line = "";
                    String response = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        response += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return response;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
            if (result.equals("followed")) {
                follow.setText("followed");
                follow.setTextColor(Color.parseColor("#FFFFFF"));
                follow.setEnabled(false);

            } else if (result.equals("unfollowed")) {
//                Toast.makeText(getActivity(), "unfollowed", Toast.LENGTH_SHORT).show();
                Log.d("follow", "this result unfollowed");
            } else {
                if (result != null) {
                    try {
                        jsonObject = new JSONObject(result);
                        jsonArray = jsonObject.getJSONArray("server_response");
                        String n = Integer.toString(jsonArray.length());
                        followers.setText(n);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity(), "this result is null", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void setData(String s) {
        artist = s;
    }

    /*public void setName(String n) {
        name = n;
    }*/
}
