package com.example.userinterface;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class BackgroundActivity extends AsyncTask<String,Void,String> {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("StaticFieldLeak")
    private Context context;

    BackgroundActivity(Context ctx){
        this.context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {

        preferences = context.getSharedPreferences("MYPREFS", Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString("flag","0");
        editor.apply();

        String urlRegistration = "http://159.203.20.150/register.php";
        String urlLogin  = "http://159.203.20.150/login.php";
        String task = params[0];

        if(task.equals("register")){
            String regName = params[1];
            String regEmail = params[2];
            String regPassword = params[3];

            try {
                URL url = new URL(urlRegistration);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                String myData = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(regName,"UTF-8")+"&"
                        +URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(regEmail,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(regPassword,"UTF-8");
                bufferedWriter.write(myData);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();

                editor.putString("flag","register");
                editor.commit();
                return "Successfully Registered " + regName;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if(task.equals("login")){
            String loginEmail = params[1];
            String loginPassword = params[2];
            try {
                URL url = new URL(urlLogin);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                String myData = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(loginEmail,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(loginPassword,"UTF-8");
                bufferedWriter.write(myData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String dataResponse = "";
                String inputLine = "";
                while((inputLine = bufferedReader.readLine()) != null){
                    dataResponse += inputLine;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                editor.putString("flag","login");
                editor.commit();
                return  dataResponse;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        String flag = preferences.getString("flag","0");

        if(flag.equals("register")) {
            Toast.makeText(context,s,Toast.LENGTH_LONG).show();
        }
        else if(flag.equals("login")){
            String[] serverResponse = s.split("[,]");
                String test = "false", name = "", email = "";
            if (serverResponse.length >1) {
                test = serverResponse[0];
                name = serverResponse[1];
                email = serverResponse[2];
            }
            if(test.equals("true")){
                editor.putString("username",name);
                editor.commit();
                editor.putString("email",email);
                editor.commit();
                Intent intent = new Intent(context, TowerDefenseActivity.class);
                context.startActivity(intent);
            }else{
                display("Login Failed...", "That email and password do not match our records :(.");
            }
        }else{
            display("Login Failed...","Something weird happened :(.");
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
    // Display some message
    public void display(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}