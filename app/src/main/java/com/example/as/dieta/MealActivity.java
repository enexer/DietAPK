package com.example.as.dieta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MealActivity extends AppCompatActivity {

    public static int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        Intent intent = getIntent();
        id = intent.getIntExtra("button_id", 0);

        System.out.println("IIIIINN" + id);

    }
    public void openFoodList(View view) {
        Intent intent = new Intent(getApplicationContext(), FoodListActivity.class);
//        System.out.println("kkkk"+id);
//        System.out.println("mmmm"+view.getId());
        intent.putExtra("buttonDay_id", id);
        intent.putExtra("buttonMeal_id", view.getId());
        startActivity(intent);
    }
}
