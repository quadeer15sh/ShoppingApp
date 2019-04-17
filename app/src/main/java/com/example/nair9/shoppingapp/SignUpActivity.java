package com.example.nair9.shoppingapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.SignInButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SignUpActivity extends AppCompatActivity {

    //ScrollView scScroll;
    private static final String TAG = "RegisterActivity";
    private static final String URL_FOR_REGISTRATION = "https://shoppingdivya.000webhostapp.com/shopping/register.php";
    ProgressDialog progressDialog;
    TextView tvSignUp, tvInfo;
    EditText signupInputName, signupInputEmail, signupInputPassword, etPhone;
    Button btnSignUp, btnLinkLogin;
    RadioGroup genderRadioGroup;
    ImageView ivUser,ivPhone,ivEmail,ivPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        tvSignUp = (TextView) findViewById(R.id.tvSignUp);
        signupInputName = (EditText) findViewById(R.id.signupInputName);
        signupInputEmail = (EditText) findViewById(R.id.signup_input_email);
        signupInputPassword = (EditText) findViewById(R.id.signup_input_password);
        etPhone = (EditText) findViewById(R.id.etPhone);
        genderRadioGroup = (RadioGroup) findViewById(R.id.gender_radio_group);
        btnSignUp = (Button) findViewById(R.id.btn_signup);
        tvInfo = (TextView) findViewById(R.id.tvInfo);
        btnLinkLogin = (Button) findViewById(R.id.btn_link_login);
        ivUser = (ImageView)findViewById(R.id.ivUser);
        ivEmail = (ImageView)findViewById(R.id.ivEmail);
        ivPhone = (ImageView)findViewById(R.id.ivPhone);
        ivPass = (ImageView)findViewById(R.id.ivPass);


        //scScroll = (ScrollView)findViewById(R.id.scScroll);
        btnLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = signupInputName.getText().toString();
                String valemail = signupInputEmail.getText().toString();
                String valpassword = signupInputPassword.getText().toString();
                String valphone = etPhone.getText().toString();
                if ((username.length() == 0) || !username.matches("[a-zA-Z_]+")) {
                    signupInputName.setError("Name is empty/ enter alphabets");
                    signupInputName.requestFocus();
                    return;
                }

                if (!isEmailValid(valemail) || valemail.length() == 0){
                    signupInputEmail.setError("Invalid id");
                    return;
                }

                if ((valpassword.length() <= 6) || valpassword.length()==0){
                    signupInputPassword.setError("Password must have at least 6 char");
                    signupInputPassword.requestFocus();
                    return;
                }

                if ((valphone.length() == 0) || valphone.length() >10){
                    etPhone.setError("Check Phone no");
                    etPhone.requestFocus();
                    return;
                }


                submitForm();
            }
        });
    }
    private void submitForm() {

        int selectedId = genderRadioGroup.getCheckedRadioButtonId();
        String gender;
        if(selectedId == R.id.female_radio_btn)
            gender = "Female";
        else
            gender = "Male";

        registerUser(signupInputName.getText().toString(),
                signupInputEmail.getText().toString(),
                signupInputPassword.getText().toString(),
                gender,
                etPhone.getText().toString());
    }

    private void registerUser(final String name,  final String email, final String password,
                              final String gender, final String phone) {
        // Tag used to cancel the request
        String cancel_req_tag = "register";


        progressDialog.setMessage("Adding you ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_REGISTRATION, new Response.Listener<String>() {

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
                        Toast.makeText(getApplicationContext(), "Hi " + user +", You are successfully Added!", Toast.LENGTH_SHORT).show();

                        // Launch login activity
                        Intent intent = new Intent(
                                SignUpActivity.this,
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
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("gender", gender);
                params.put("phone", phone);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, cancel_req_tag);
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
















