package com.example.nair9.shoppingapp;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class BarcodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView scannerView;
    public static final int PERMISSION_REQUEST_CAMERA =1;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        if (!haveCameraPermission()){
            requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
        }
    }


    private boolean haveCameraPermission() {
        if (Build.VERSION.SDK_INT < 23){
            return true;

        }
        return checkSelfPermission(Manifest.permission.CAMERA) == PackageManager
                .PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (permissions.length == 0 || grantResults.length == 0){
            return;
        }

        switch (requestCode)
        {
            case PERMISSION_REQUEST_CAMERA:
            {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    startCamera();
                }
                else
                {
                    finish();
                }
            }
            break;
        }
    }

    private void startCamera() {
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    public void stopCamera(){
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {

        Log.v("tag",result.getText());
        Log.v("tag",result.getBarcodeFormat().toString());
        //ScanActivity.tvResult.setText(result.getText());
        onBackPressed();

        Intent i1 = new Intent(this,ScanActivity.class);
        i1.putExtra("result", String.valueOf(result));
        startActivity(i1);


    }
}
