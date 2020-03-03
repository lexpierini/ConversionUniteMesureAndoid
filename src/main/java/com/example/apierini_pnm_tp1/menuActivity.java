package com.example.apierini_pnm_tp1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class menuActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button button1 = findViewById(R.id.buttonViewTemperature);
        Button button2 = findViewById(R.id.buttonViewDistance);
        Button button3 = findViewById(R.id.buttonViewMasse);
        Button button4 = findViewById(R.id.buttonQuitter);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
    }

    public void onClick (View view) {
        Class nomClasse = null;

        switch (view.getId()) {
            case R.id.buttonViewTemperature:
                nomClasse = TemperatureActivity.class;
                break;
            case R.id.buttonViewDistance:
                nomClasse = DistanceActivity.class;
                break;
            case R.id.buttonViewMasse:
                nomClasse = MasseActivity.class;
                break;
            case R.id.buttonQuitter:
                finish();
                break;
            default:
                nomClasse = null;
        }
        if(nomClasse != null) {
            startActivity(new Intent(this, nomClasse));
        }
    }
}
