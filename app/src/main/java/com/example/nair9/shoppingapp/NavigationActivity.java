package com.example.nair9.shoppingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

import static android.support.design.R.styleable.NavigationView;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    View headerView;
    Button btnBarcode, btnRecommendation,btnReview;
    SharedPreferences sp1;
    TextView tvUser,tvEmail,tvUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
//        setContentView(R.layout.nav_header_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        Intent i = getIntent();
        final String id = i.getStringExtra("userid");
        //String user = i.getStringExtra("username");
        //Log.e("user",user);
        Log.e("id",id);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        headerView = navigationView.getHeaderView(0);

        tvUsername = (TextView)findViewById(R.id.tvUsername);

        tvEmail = (TextView) headerView.findViewById(R.id.tvEmail);
        tvUser = (TextView)headerView.findViewById(R.id.tvUser);
        System.out.println(tvEmail.toString()+" "+tvUser.toString());
        Bundle bundle = getIntent().getExtras();
        String user =bundle.getString("username");
        Log.e("username",user);
        tvUser.setText("User "+id);
        tvEmail.setText("Name "+user);
        //tvUser.setText(user);


        btnBarcode =(Button)findViewById(R.id.btnBarcode);
        btnRecommendation =(Button)findViewById(R.id.btnRecommendation);
        btnReview = (Button)findViewById(R.id.btnReview);






        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(NavigationActivity.this,ReviewActivity.class);
                i2.putExtra("id",id);
                startActivity(i2);
            }
        });

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_log) {
            // Handle the camera action
        } else if (id == R.id.nav_erecipt) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_signout) {

            Intent intent = new Intent(NavigationActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
