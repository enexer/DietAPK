package com.example.as.dieta;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PlanActivity extends AppCompatActivity {

//    DatabaseAccess mydb = new DatabaseAccess(this);

    private TextView textViewCal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();


        Cursor cursor = databaseAccess.getLastMeasurements();
        String result = "";
        if (cursor != null && cursor.moveToFirst()) {
            result = cursor.getString(cursor.getColumnIndex("calories"));
            cursor.close();
        }


        double cal= Double.parseDouble(result);
        double protein = Math.round(cal*0.3);
        double carbo = Math.round(cal*0.5);
        double fat = Math.round(cal*0.2);

        textViewCal = (TextView) findViewById(R.id.textViewCal);
        textViewCal.setText("Kalorie: "+result+"\nBialkOoo: "+protein+"\nWegle: "+carbo+"\nTluszcz: "+fat);
    }
    public void openFood(View view) {
        Intent intent = new Intent(getApplicationContext(), MealActivity.class);
        System.out.println("BBBBBB"+view.getId());
        intent.putExtra("button_id", view.getId());
        startActivity(intent);
    }


}
