package com.example.as.dieta;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.as.dieta.realm.SelectedProductsDao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DniActivity extends AppCompatActivity {

    private ListView lv;
    private SelectedProductsDao selectedProductsDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dni);

        selectedProductsDao = new SelectedProductsDao(this);

        lv = (ListView) findViewById(R.id.listViewDay);
        fillListView();

        TextView textView = (TextView)findViewById(R.id.textViewDay);

        SharedPreferences sharedPrefRead = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String sCal = sharedPrefRead.getString("calories", "");
        String sP = sharedPrefRead.getString("protein", "");
        String sC = sharedPrefRead.getString("carbo", "");
        String sF = sharedPrefRead.getString("fat", "");
        System.out.println("kcal: "+sCal+", W:"+sC+", B:"+sP+", T:"+sF);
        textView.setText("Twoje zapotrzebowanie:\nKcal: "+sCal+" g\nWeglowodany:"+sC+" g\nBialko:"+sP+" g\nTluszcz:"+sF+" g");

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HashMap<String, String> map = (HashMap<String, String>) lv.getItemAtPosition(position);
                String day_name = map.get("day");
                Toast.makeText(DniActivity.this, "CLIKCED AT: " + position, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), PosilkiActivity.class);
                intent.putExtra("day", day_name);
                intent.putExtra("day_pos", position);
                startActivity(intent);

//                ProgressBar p_Bar = (ProgressBar)view.findViewById(R.id.progressBarDay);
//                p_Bar.setProgress(50);

            }
        });



    }
    @Override
    protected void onDestroy() {
        // zamykamy instancję Realma //
       // selectedProductsDao.close();
        super.onDestroy();
    }

//    @Override
//    protected void onPostResume() {
//        //fillListView();
//        super.onPostResume();
//    }

    public void fillListView() {

        List<String> list = new ArrayList<String>();
        list.add("Poniedzialek");
        list.add("Wtorek");
        list.add("Sroda");
        list.add("Czwartek");
        list.add("Piatek");
        list.add("Sobota");
        list.add("Niedziela");

        ArrayList<HashMap<String, Object>> dayList;
        dayList = new ArrayList<>();

        Iterator<String> iterator = list.iterator();
        int i=0;
        while (iterator.hasNext()) {
            String dd = iterator.next();
            System.out.println(dd);
            HashMap<String, Object> dL = new HashMap<>();
            dL.put("day", dd);
            dL.put("sum_kcal", selectedProductsDao.countKcal(Integer.toString(i), null)+" kcal");
            dayList.add(dL);
            i++;
        }

        ListAdapter adapter = new SimpleAdapter(DniActivity.this, dayList,
                R.layout.list_day, new String[]{"day", "sum_kcal"},
                new int[]{R.id.textViewListDay, R.id.textViewListDayKcal});
        lv.setAdapter(adapter);


    }
}
