package com.example.nair9.shoppingapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ScanActivity extends AppCompatActivity {

    //public static TextView tvProName,tvPrice,tvWeight;
    //private Button btnCart;
   // TextView tvResult ;

    //JSONObject json = null;
    //String str = "";
    static List<Product> itemsList;
    ListView lv;
    //HttpResponse response;
    //Context context;
    //ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        /*tvProName =(TextView)findViewById(R.id.tvProName);
        tvPrice =(TextView)findViewById(R.id.tvPrice);
        tvWeight =(TextView)findViewById(R.id.tvWeight);*/
        //btnCart = (Button) findViewById(R.id.btnCart);


        //tvResult = (TextView) findViewById(R.id.tvResult);
        Intent in = getIntent();
        //tvResult.setText(in.getStringExtra("result"));*/
        //new ResultData(context).execute();
        /*String id = tvResult.getText().toString();
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();*/

        ResultData res = new ResultData();
        String pythonUrl ="https://recommendationapi.herokuapp.com/api/get_details/";
        String id = in.getStringExtra("result");
        String newUrl = pythonUrl+id;
        res.execute(newUrl);

    }




    public class ResultData extends AsyncTask<String, Void, String> {

       /* public Context context;
        public ResultData(Context context){this.context = context;}*/

        @Override
        protected String doInBackground(String... urls) {

            //not getting barcode value using json
            /*String result = "";
            String bar_url = "https://shoppingdivya.000webhostapp.com/shopping/barcode.php";

                try {

                    HttpClient client = new DefaultHttpClient();
                    HttpGet request = new HttpGet();
                    request.setURI(new URI(bar_url));
                    HttpResponse response = client.execute(request);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response
                            .getEntity().getContent()));
                    StringBuffer stringBuffer = new StringBuffer("");
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuffer.append(line);
                        break;
                    }
                    bufferedReader.close();
                    result = stringBuffer.toString();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

           /* HttpClient myClient = new DefaultHttpClient();
            HttpPost myConnection = new HttpPost("https://recommendationapi.herokuapp.com/api/get_details/");

            try{
                response = myClient.execute(myConnection);
                str = EntityUtils.toString(response.getEntity(),"UTF-8");
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }*/

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try{
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream is = urlConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                int data = isr.read();

                while (data != -1){
                    char current = (char) data;
                    result += current;
                    data = isr.read();
                }

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

            }

            return null;

            }


            @Override
            protected void onPostExecute (String s){

                /*try {

                    JSONObject jsonObject = new JSONObject(result);
                    Log.e("Result", String.valueOf(jsonObject));
                    int success = jsonObject.getInt("success");
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject c = jsonArray.getJSONObject(i);
                        String bar_id = c.getString("bar_id");
                        Log.e("val",bar_id);  }
                    if (success == 1) {
                        ArrayList<String> items = new ArrayList<>();

                    } else {
                        Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/

               /* try{
                    JSONArray jsonArray = new JSONArray(str);
                    
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/

                try{
                    Log.i("JSON",s);

                    System.out.println(s);

                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("Details");
                    String i = jsonArray.getString(1);
                    Log.e("i",i);
                    String i1 = jsonArray.getString(2);

                    String i2 = jsonArray.getString(3);

                    String i3 = jsonArray.getString(4);

                    int i4 = 1;
                    itemsList = new ArrayList<>();
                    lv = (ListView) findViewById(R.id.lv);


                    itemsList.add(new Product(i1,i2,i3,i4));



                    CustomAdapter ca = new CustomAdapter(ScanActivity.this,R.layout.cart_item,itemsList);
                    lv.setAdapter((ListAdapter) ca);
                    ca.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }



}
