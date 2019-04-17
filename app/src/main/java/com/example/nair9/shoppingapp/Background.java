package com.example.nair9.shoppingapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by nair9 on 14-04-2019.
 */
public class Background extends AsyncTask<String,Void,String> {

    Context context;

    Background(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        String result = "";
        String method = params[0];
        String brandUrl = "https://shoppingdivya.000webhostapp.com/shopping/revBrand.php";
        String custUrl = "https://shoppingdivya.000webhostapp.com/shopping/custReview.php";

        if (method.equals("Submit")) {
            String time = params[1];
            String reviewBrand = params[2];
            String brand = params[3];
            String user_id = params[4];
            try {
                URL url = new URL(brandUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os,
                        "UTF-8"));
                String post_data = URLEncoder.encode("time", "UTF-8") + "=" + URLEncoder.encode
                        (time, "UTF-8") + "&" + URLEncoder.encode("reviewBrand", "UTF-8")
                        + "=" + URLEncoder.encode(reviewBrand, "UTF-8") + "&" + URLEncoder.encode("brand", "UTF-8") + "=" + URLEncoder
                        .encode(brand, "UTF-8") + "&" + URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8");
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
                //return result;
                Log.e("result", result);
            } catch (MalformedURLException e) {
                result = e.getMessage();
            } catch (IOException e) {
                result = e.getMessage();
            }
        }

        else if (method.equals("Enter")) {
            String user_id = params[1];
            String feedback = params[2];
            String datetime = params[3];
            try {
                URL url = new URL(custUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os,
                        "UTF-8"));
                String post_data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8") + "&" +
                        URLEncoder.encode("feedback", "UTF-8") + "=" + URLEncoder.encode(feedback, "UTF-8")+ "&" +
                        URLEncoder.encode("datetime", "UTF-8") + "=" + URLEncoder.encode(datetime, "UTF-8");
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
                //return result;
                Log.e("result", result);

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

    }

    @Override
    protected void onPostExecute(String result) {
        if (result.equals("Success")){
            Toast.makeText(context, "Data Inserted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
