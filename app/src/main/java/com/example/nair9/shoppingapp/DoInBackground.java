package com.example.nair9.shoppingapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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
import java.util.HashMap;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;
import static com.example.nair9.shoppingapp.R.styleable.AlertDialog;
import static com.example.nair9.shoppingapp.R.styleable.AlertDialog;
import static com.example.nair9.shoppingapp.R.styleable.AlertDialog;
import static com.example.nair9.shoppingapp.R.styleable.AlertDialog;
import static com.example.nair9.shoppingapp.R.styleable.AlertDialog;

/**
 * Created by nair9 on 14-02-2019.
 */

public class DoInBackground extends AsyncTask<String ,Void,String> {

    AlertDialog alertDialog;

    Context context;
    DoInBackground (Context ctx){
        context = ctx;
    }


    @Override
    protected String doInBackground(String... params) {
        String  result = "";
        String method = params[0];
        //String method = params[0];
        String login_url = "https://shoppingdivya.000webhostapp.com/shopping/login.php";

        String register_url = "https://shoppingdivya.000webhostapp.com/shopping/register.php";

        //String barcode_url = "http://192.168.1.107/shopping/barcode.php";

        if (method.equals("Login")) {
            String login_email = params[1];
            String login_pass = params[2];
            try {


                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter
                        (outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("login_email", "UTF-8")+"="+URLEncoder.encode
                        (login_email, "UTF-8")+"&"+ URLEncoder.encode("login_pass", "UTF-8")
                        +"="+URLEncoder.encode(login_pass, "UTF-8");
                bufferedWriter.write(post_data);
                
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader
                        (inputStream, "iso-8859-1"));

                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;




            } catch (MalformedURLException e) {
                result = e.getMessage();
            } catch (IOException e) {
                result =e.getMessage();
            }

        }


        else if (method.equals("Register")){
            String name = params[1];
            String number = params[2];
            String email_id = params[3];
            String password = params[4];
            String gender = params[5];
            try {


                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os,
                        "UTF-8"));
                String post_data = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,
                        "UTF-8")+"&"+URLEncoder.encode("number","UTF-8")+"="+URLEncoder.encode
                        (number, "UTF-8")+"&"+URLEncoder.encode("email_id","UTF-8")+"="+URLEncoder
                        .encode(email_id, "UTF-8")+"&"+URLEncoder.encode("password","UTF-8")
                        +"="+URLEncoder.encode(password, "UTF-8")+"&"+URLEncoder.encode("gender",
                        "UTF-8")+"="+URLEncoder.encode(gender, "UTF-8");
                bw.write(post_data);
                bw.flush();
                bw.close();
                os.close();
                InputStream is = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader
                        (is, "iso-8859-1"));

                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                is.close();
                httpURLConnection.disconnect();
                return result;

            } catch (MalformedURLException e) {
                result = e.getMessage();
            } catch (IOException e) {
                result = e.getMessage();
            }
        }



        return result;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String result) {

            if(result.equals("success")) {
                Toast.makeText(context, "Successful login", Toast.LENGTH_SHORT).show();
                //Intent i = new Intent(this,NavigationActivity.class);
                context.startActivity(new Intent(context,NavigationActivity.class));

            }
            else if (result.equals("Success reg"))
            {
                alertDialog.setMessage(result);
                alertDialog.show();
                context.startActivity(new Intent(context,NavigationActivity.class));
            }
            else
            {
                Toast.makeText(context,result, Toast.LENGTH_SHORT).show();
            }

        //delegate.processFinish(result);



    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
