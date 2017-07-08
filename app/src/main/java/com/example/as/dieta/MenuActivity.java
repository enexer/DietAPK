package com.example.as.dieta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void openMeasure(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        //intent.putExtra("kategoria", "quest");
        startActivity(intent);
    }

    public void openPlan(View view) {
        Intent intent = new Intent(getApplicationContext(), DniActivity.class);
        startActivity(intent);
    }

    public void openChart(View view) {
        Intent intent = new Intent(getApplicationContext(), ProductsActivity.class);
        startActivity(intent);
    }
}
