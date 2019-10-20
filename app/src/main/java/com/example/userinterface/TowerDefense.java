package com.example.userinterface;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class TowerDefense extends AppCompatActivity {
    //User user;
    int money = 200;
    int waves = 1;

    public void play(){
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        AlertDialog.Builder wrongInfo = new AlertDialog.Builder(TowerDefense.this);
        wrongInfo.setCancelable(true);
        wrongInfo.setTitle("THIS IS THE TOWER GAME");
        wrongInfo.setMessage("PLAY ON");
        wrongInfo.show();


    }
}



