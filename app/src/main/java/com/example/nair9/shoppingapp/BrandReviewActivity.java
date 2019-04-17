package com.example.nair9.shoppingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class BrandReviewActivity extends AppCompatActivity {

    Spinner spProducts;
    EditText etMessage;
    Button btnSubmit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_review);

        spProducts = (Spinner)findViewById(R.id.spProducts);
        etMessage = (EditText)findViewById(R.id.etMessage);
        //btnSubmit = (Button)findViewById(R.id.btnSubmit);

        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");


        final ArrayList<String >product = new ArrayList<>();
        product.add("Brand: 1");
        product.add("Brand: 2");
        product.add("Brand: 3");
        product.add("Brand: 4");
        product.add("Brand: 5");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout
                .simple_list_item_1,product);
        spProducts.setAdapter(adapter);





    }

    public void Submit(View view){

        String reviewBrand =etMessage.getText().toString();
        Date d =  Calendar.getInstance().getTime();

        String date = d.toString();
        Log.e("date",date);

        DateFormat df = new SimpleDateFormat("HH:mm a");
        String time = df.format(Calendar.getInstance().getTime());
        Log.e("time",time);

        Intent intent = getIntent();
        String user_id = intent.getStringExtra("id");
        Log.e("user_id",user_id);

        String brand = (String) spProducts.getSelectedItem();
        Log.e("pro",brand);

        String method = "Submit";
        if (reviewBrand.length() != 0)
        {Background background = new Background(this);
        background.execute(method,time,reviewBrand,brand,user_id);}



        if (reviewBrand.length() == 0)
        {
            etMessage.setError("Please enter your Review");
            etMessage.requestFocus();

            return;
        }

        Toast.makeText(BrandReviewActivity.this, "Thanks for ur feedback", Toast
                .LENGTH_SHORT)
                .show();


    }


}
