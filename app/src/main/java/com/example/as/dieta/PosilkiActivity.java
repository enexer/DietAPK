package com.example.as.dieta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.as.dieta.realm.SelectedProductsDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class PosilkiActivity extends AppCompatActivity {

    private ListView lv;
    private SelectedProductsDao selectedProductsDao;
    private int day_pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posilki);

        Intent intent = getIntent();
        final String day_name = intent.getStringExtra("day");

        lv = (ListView) findViewById(R.id.listViewMeal);
        fillListView();
        fillTextView();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HashMap<String, String> map = (HashMap<String, String>) lv.getItemAtPosition(position);
                String meal_name = map.get("meal");
                String meal_pos = map.get("meal_pos");
              //  Toast.makeText(PosilkiActivity.this, "CLIKCED AT: " + meal_name + "," + day_name + ",d.p " + day_pos + ", m.p" + meal_pos, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), FoodListActivity.class);
                intent.putExtra("day", day_name);
                String day_p = Integer.toString(day_pos);
                intent.putExtra("day_pos", day_p);
                intent.putExtra("meal", meal_name);
                intent.putExtra("meal_pos", meal_pos);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onDestroy() {
        // zamykamy instancję Realma
        // selectedProductsDao.close();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        fillListView();
        fillTextView();
        super.onResume();
    }

    private void fillTextView() {
        selectedProductsDao = new SelectedProductsDao(this);
        Intent intent = getIntent();
        day_pos = intent.getIntExtra("day_pos", 0);

        TextView textViewSum = (TextView) findViewById(R.id.textViewMealSum);
        TextView textViewTarget = (TextView) findViewById(R.id.textViewMealTarget);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarMeal);

        int s_kcal = selectedProductsDao.countKcal(Integer.toString(day_pos), null);
        int s_protein = selectedProductsDao.countProtein(Integer.toString(day_pos), null);
        int s_carbo = selectedProductsDao.countCarbo(Integer.toString(day_pos), null);
        int s_fat = selectedProductsDao.countFat(Integer.toString(day_pos), null);

        textViewSum.setText("Suma dnia:" + s_kcal + " kcal, W:" + s_carbo + " B:" + s_protein + " T:" + s_fat);

        SharedPreferences sharedPrefRead = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String sCal = sharedPrefRead.getString("calories", "1");
        String sP = sharedPrefRead.getString("protein", "1");
        String sC = sharedPrefRead.getString("carbo", "1");
        String sF = sharedPrefRead.getString("fat", "1");

        int prog = (s_kcal * 100) / Integer.parseInt(sCal);


        System.out.println("kcal: " + sCal + ", W:" + sC + ", B:" + sP + ", T:" + sF);
        textViewTarget.setText("Twoj cel:" + sCal + " kcal, W:" + sC + " B:" + sP + " T:" + sF + "\n(" + prog + " %)");
        progressBar.setProgress((prog));

    }
    private void fillListView() {
        selectedProductsDao = new SelectedProductsDao(this);

        List<String> list = new ArrayList<String>();
        list.add("posilek 1");
        list.add("posilek 2");
        list.add("posilek 3");
        list.add("posilek 4");
        list.add("posilek 5");
        list.add("posilek 6");

        ArrayList<HashMap<String, Object>> dayList;
        dayList = new ArrayList<>();

        Iterator<String> iterator = list.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            String dd = iterator.next();
            System.out.println(dd);
            HashMap<String, Object> dL = new HashMap<>();
            dL.put("meal", dd);
            dL.put("meal_pos", Integer.toString(i));
            dL.put("sum_kcal", selectedProductsDao.countKcal(Integer.toString(day_pos), Integer.toString(i)) + " kcal");
            dL.put("sum_carbo", "W: " + selectedProductsDao.countCarbo(Integer.toString(day_pos), Integer.toString(i)) + " g");
            dL.put("sum_protein", "B: " + selectedProductsDao.countProtein(Integer.toString(day_pos), Integer.toString(i)) + " g");
            dL.put("sum_fat", "T: " + selectedProductsDao.countFat(Integer.toString(day_pos), Integer.toString(i)) + " g");
            dayList.add(dL);
            i++;
        }

        ListAdapter adapter = new SimpleAdapter(PosilkiActivity.this, dayList,
                R.layout.list_meal, new String[]{"meal", "sum_kcal", "sum_carbo", "sum_protein", "sum_fat"},
                new int[]{R.id.textViewListMeal, R.id.textViewListMealKcal, R.id.textViewListMealCarbo, R.id.textViewListMealProtein, R.id.textViewListMealFat});
        lv.setAdapter(adapter);
    }
}
