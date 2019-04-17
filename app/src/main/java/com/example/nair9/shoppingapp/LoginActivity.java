package com.example.nair9.shoppingapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final String URL_FOR_LOGIN = "https://shoppingdivya.000webhostapp.com/shopping/login.php";
    ProgressDialog progressDialog;
    EditText login_input_email, login_input_password;
    Button btn_login;
    ImageView ivEmaiId,ivLock;

    Context context;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        login_input_email =(EditText)findViewById(R.id.login_input_email);
        login_input_password =(EditText)findViewById(R.id.login_input_password);
        btn_login =(Button) findViewById(R.id.btn_login);
        //btn_link_signup =(Button) findViewById(R.id.btn_link_signup);
        ivEmaiId = (ImageView)findViewById(R.id.ivEmailId);
        ivLock = (ImageView)findViewById(R.id.ivLock);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = login_input_email.getText().toString();
                String pass = login_input_password.getText().toString();

                if ((pass.length() <= 6) || pass.length()==0){
                    login_input_password.setError("Password must have at least 6 char");
                    login_input_password.requestFocus();
                    return;
                }
                if (!isEmailValid(email) || email.length() == 0){
                    login_input_email.setError("Invalid id");
                    return;
                }


                loginUser(login_input_email.getText().toString(),
                        login_input_password.getText().toString());

            }
        });


    }

    private void loginUser( final String email, final String password) {
        // Tag used to cancel the request
        String cancel_req_tag = "login";
        progressDialog.setMessage("Logging you in...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();
                try {

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        int userid = jObj.getJSONObject("user").getInt("id");
                        Log.e("id", String.valueOf(userid));
                        String user = jObj.getJSONObject("user").getString("name");

                        // Launch User activity
                        Intent intent = new Intent(
                                LoginActivity.this,
                                NavigationActivity.class);
                        intent.putExtra("username", user);
                        intent.putExtra("userid",String.valueOf(userid));
                        startActivity(intent);
                        finish();
                    } else {

                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }

        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,cancel_req_tag);
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }
    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    private boolean isEmailValid(String email_id){
        return Patterns.EMAIL_ADDRESS.matcher(email_id).matches();
    }


}
