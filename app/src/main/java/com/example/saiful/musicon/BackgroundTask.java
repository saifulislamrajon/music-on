package com.example.saiful.musicon;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

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


public class BackgroundTask extends AsyncTask<String, Void, String> {
    Context context;
    AlertDialog alertDialog;
    ProgressDialog progressDialog;

    BackgroundTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
//        super.onPreExecute();
        alertDialog = new AlertDialog.Builder(context).create();
//        alertDialog.setTitle("Registration Status");

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("wait for your response");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String login_url = "http://saifulrajoncom.000webhostapp.com/mlogin.php";
        String registration_url = "http://saifulrajoncom.000webhostapp.com/mregistration.php";
        String follow_url = "http://saifulrajoncom.000webhostapp.com/mfollow.php";
        String type = params[0];
        if (type.equals("registration")) {
            String name = params[1];
            String email = params[2];
            String password = params[3];
            String sex = params[4];
            try {
                URL url = new URL(registration_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&" + URLEncoder.encode("sex", "UTF-8") + "=" + URLEncoder.encode(sex, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                outputStream.close();
                bufferedWriter.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                httpURLConnection.disconnect();
                bufferedReader.close();
                inputStream.close();
//                return "registration success";
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type.equals("login")) {
            String name = params[1];
            String password = params[2];
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }
                httpURLConnection.disconnect();
                inputStream.close();
                bufferedReader.close();
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type.equals("follow")) {
            String name = params[1];
            String artist = params[2];
            try {
                URL url = new URL(follow_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data=URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+URLEncoder.encode("artist","UTF-8")+"="+URLEncoder.encode(artist,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response="";
                String line="";
                while ((line=bufferedReader.readLine())!=null){
                    response+=line;
                }
                httpURLConnection.disconnect();
                inputStream.close();
                bufferedReader.close();
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
//        super.onPostExecute(aVoid);
        progressDialog.cancel();
        /*if (result.equals("registration success")) {
            alertDialog.setMessage(result);
            alertDialog.show();
            Toast.makeText(context, "your registration complete", Toast.LENGTH_SHORT).show();
        } else {
            alertDialog.setMessage(result);
            alertDialog.show();
        }*/
        if (result.equals("data insertion success")) {
            /*alertDialog.setTitle("Registration Status");
            alertDialog.setMessage(result);
            alertDialog.show();*/
            Toast.makeText(context, "Registration Success", Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context, ArtistList.class));
        } else if (result.equals("data insertion error")) {
            alertDialog.setTitle("Registration Status");
            alertDialog.setMessage(result);
            alertDialog.show();
        } else if (result.equals("Login Success")) {
            /*alertDialog.setTitle("Login Status");
            alertDialog.setMessage(result);
            alertDialog.show();*/
            Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context, ArtistList.class));
        } else if (result.equals("Login Error")) {
            alertDialog.setTitle("Login Status");
            alertDialog.setMessage(result);
            alertDialog.show();
        }else if(result.equals("follow success")){
            Toast.makeText(context,"follow success",Toast.LENGTH_SHORT).show();
        }else if(result.equals("follow error")){
            Toast.makeText(context,"follow error",Toast.LENGTH_SHORT).show();
        }
    }
}
