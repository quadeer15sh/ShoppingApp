package com.example.nair9.shoppingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CustReviewActivity extends AppCompatActivity {

    EditText etCustMsg;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_review);

        etCustMsg = (EditText)findViewById(R.id.etCustMsg);
        btnSubmit = (Button)findViewById(R.id.btnSubmit);


    }

    public void Enter(View view){

        String feedback = etCustMsg.getText().toString();

        DateFormat df = new SimpleDateFormat("yyyy.MM.dd  'at' HH:mm:ss ");
        String datetime = df.format(Calendar.getInstance().getTime());
        Log.e("datetime",datetime);


        Intent i = getIntent();
        String user_id = i.getStringExtra("id");
        String method = "Enter";
        if (feedback.length() != 0 )
        { Background background = new Background(this);
        background.execute(method,user_id,feedback,datetime);}


        if (feedback.length() == 0)
        {
            etCustMsg.setError("Enter Message");
            etCustMsg.requestFocus();
            return;
        }
        Toast.makeText(CustReviewActivity.this, "Thanks for ur Feedback", Toast
                .LENGTH_SHORT).show();

    }
}
