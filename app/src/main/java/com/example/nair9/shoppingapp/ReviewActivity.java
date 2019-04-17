package com.example.nair9.shoppingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ReviewActivity extends AppCompatActivity {

    Button btnBrand, btnCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        btnBrand = (Button)findViewById(R.id.btnBrand);
        btnCustomer = (Button)findViewById(R.id.btnCustomer);
        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");

        btnBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ReviewActivity.this,BrandReviewActivity.class);
                i.putExtra("id",id);
                startActivity(i);
            }
        });

        btnCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(ReviewActivity.this,CustReviewActivity.class);
                i2.putExtra("id",id);
                startActivity(i2);
            }
        });
    }
}
