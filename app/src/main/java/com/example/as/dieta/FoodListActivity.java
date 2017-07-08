package com.example.as.dieta;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.as.dieta.realm.SelectedProductsDao;
import com.example.as.dieta.realm.SelectedProductsRealm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class FoodListActivity extends AppCompatActivity {

    private Button button;
    private TextView textViewDM,textViewSum, textViewSum2, textViewTarget;
    private ProgressBar progressBar;
    private ListView listView;
    private SelectedProductsDao selectedProductsDao;
    private DatabaseAccess databaseAccess;
    private String day_pos;
    private String meal_pos;
    private String day;
    private String meal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        selectedProductsDao = new SelectedProductsDao(this);

        databaseAccess = DatabaseAccess.getInstance(this);

        textViewDM = (TextView) findViewById(R.id.textViewFoodListDM);
        button = (Button) findViewById(R.id.buttonAddProduct);
        this.listView = (ListView) findViewById(R.id.listViewSelected);
        Intent intent = getIntent();
        day    = intent.getStringExtra("day");
        meal   = intent.getStringExtra("meal");
        day_pos = intent.getStringExtra("day_pos");
        meal_pos = intent.getStringExtra("meal_pos");
        textViewDM.setText(day + "/" + meal);
        fillTextView();

        // zapisanie tekstu z strings.xml do listy
        final ArrayList<String> strings = new ArrayList<>();
        strings.add(getString(R.string.txt1));
        strings.add(getString(R.string.txt2));
        strings.add(getString(R.string.txt3));
        strings.add(getString(R.string.txt4));
        strings.add(getString(R.string.txt5));
        strings.add(getString(R.string.txt6));
        strings.add(getString(R.string.txt7));


        // Dodanie produktow, wyswietlenie porady
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ////////////////////ALERT///////////////////
                AlertDialog alertDialog = new AlertDialog.Builder(FoodListActivity.this).create();
                alertDialog.setTitle("Porada");

                // wylosowanie elementu listy
                Random r = new Random();
                String str = strings.get(r.nextInt(strings.size()));

                alertDialog.setMessage(str);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent(getApplicationContext(), ProductsActivity.class);
                                intent.putExtra("day_pos", day_pos);
                                intent.putExtra("meal_pos", meal_pos);
                                intent.putExtra("day", day);
                                intent.putExtra("meal", meal);
                                intent.putExtra("directed", true);
                                startActivity(intent);

                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                ////////////////////ALERT///////////////////
            }
        });


        // Usuwanie produktow
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> map  = (HashMap<String, String>) listView.getItemAtPosition(position);
                final int               v_id = Integer.parseInt(map.get("id"));
                selectedProductsDao.deleteSelectedById(v_id);
                fillListView();
                fillTextView();
                return true;
            }
        });

        fillListView();
    }

    @Override
    protected void onDestroy() {
        // zamykamy instancję Realma
        selectedProductsDao.close();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        selectedProductsDao.close();

    }
    public void fillTextView(){
        textViewSum = (TextView)findViewById(R.id.textViewFLSum);
        textViewSum2 = (TextView)findViewById(R.id.textViewFLSum2);
        textViewTarget = (TextView)findViewById(R.id.textViewFLTarget);
        progressBar = (ProgressBar)findViewById(R.id.progressBarFL);

        int s_kcal=selectedProductsDao.countKcal(day_pos,null);
        int s_protein=selectedProductsDao.countProtein(day_pos,null);
        int s_carbo=selectedProductsDao.countCarbo(day_pos,null);
        int s_fat=selectedProductsDao.countFat(day_pos,null);

        int sm_kcal=selectedProductsDao.countKcal(day_pos,meal_pos);
        int sm_protein=selectedProductsDao.countProtein(day_pos,meal_pos);
        int sm_carbo=selectedProductsDao.countCarbo(day_pos,meal_pos);
        int sm_fat=selectedProductsDao.countFat(day_pos,meal_pos);

        textViewSum.setText("Suma dnia:"+s_kcal+" kcal, W:"+s_carbo+" B:"+s_protein+" T:"+s_fat);
        textViewSum2.setText("Suma posilku:"+sm_kcal+" kcal, W:"+sm_carbo+" B:"+sm_protein+" T:"+sm_fat);

        SharedPreferences sharedPrefRead = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String sCal = sharedPrefRead.getString("calories", "1");
        String sP = sharedPrefRead.getString("protein", "1");
        String sC = sharedPrefRead.getString("carbo", "1");
        String sF = sharedPrefRead.getString("fat", "1");

        int prog=(sm_kcal*100)/Integer.parseInt(sCal);


        System.out.println("kcal: "+sCal+", W:"+sC+", B:"+sP+", T:"+sF);
        textViewTarget.setText("Twoj cel:"+sCal+" kcal, W:"+sC+" B:"+sP+" T:"+sF+"\n("+prog+" %)");

        progressBar.setProgress((prog));
    }

    public void fillListView() {
        System.out.println("DAY KCAL ++++++++  " + selectedProductsDao.countWeight(day_pos, null));
        System.out.println("WEIGHT ++++++++  " + selectedProductsDao.countWeight(day_pos, meal_pos));
        System.out.println("\nKCAL ++++++++  " + selectedProductsDao.countKcal(day_pos, meal_pos));
        System.out.println("\nPROTEIN ++++++++  " + selectedProductsDao.countProtein(day_pos, meal_pos));
        System.out.println("\nCARBO ++++++++  " + selectedProductsDao.countCarbo(day_pos, meal_pos));
        System.out.println("\nFAT ++++++++  " + selectedProductsDao.countFat(day_pos, meal_pos));
//        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
//        databaseAccess.open();
        //mCursor = databaseAccess.getProducts();
        ArrayList<HashMap<String, Object>> productsList;
        productsList = new ArrayList<>();

        List<SelectedProductsRealm> products = selectedProductsDao.getRawProductsLike(day_pos, meal_pos);


        double sum_kcal = 0, sum_carbo = 0, sum_protein = 0, sum_fat = 0;

        databaseAccess.open();
        for (SelectedProductsRealm product : products) {
            HashMap<String, Object> hashMapProduct = new HashMap<>();

            String id, name = null;
            double weight = product.getWeight();
            double kcal = 0, carbo = 0, protein = 0, fat = 0;

            // Pobranie z bazy SQlite produktu o danym id
            Cursor mCursor = databaseAccess.getProduct(product.getProductId());
            mCursor.moveToFirst();
            while (!mCursor.isAfterLast()) {
                //id = mCursor.getString(mCursor.getColumnIndex("_id"));
                name = mCursor.getString(mCursor.getColumnIndex("c0name"));
                kcal = Double.parseDouble(mCursor.getString(mCursor.getColumnIndex("kcal")));
                carbo = Double.parseDouble(mCursor.getString(mCursor.getColumnIndex("W")));
                protein = Double.parseDouble(mCursor.getString(mCursor.getColumnIndex("B")));
                fat = Double.parseDouble(mCursor.getString(mCursor.getColumnIndex("T")));
                mCursor.moveToNext();
            }
            // Obliczenie wartosci dla danej wagi
            kcal=convert(kcal,weight);
            carbo=convert(carbo,weight);
            protein=convert(protein,weight);
            fat=convert(fat,weight);

            sum_kcal += kcal;
            sum_carbo += carbo;
            sum_protein += protein;
            sum_fat += fat;

            hashMapProduct.put("id", String.valueOf(product.getId()));
            hashMapProduct.put("name", name);
            hashMapProduct.put("weight", product.getWeight());
            hashMapProduct.put("kcal", (int) kcal);
            hashMapProduct.put("carbo", (int) carbo);
            hashMapProduct.put("protein", (int) protein);
            hashMapProduct.put("fat", (int) fat);
            productsList.add(hashMapProduct);
        }
        databaseAccess.close();


        ListAdapter adapter = new SimpleAdapter(FoodListActivity.this, productsList,
                R.layout.list_item_selected, new String[]{"name", "weight", "kcal", "carbo", "protein", "fat"},
                new int[]{R.id.textViewSListName, R.id.textViewSListWeight, R.id.textViewSListK, R.id.textViewSListC, R.id.textViewSListP, R.id.textViewSListF});
        listView.setAdapter(adapter);

        System.out.println("CKAL " + sum_kcal+" CAR"+sum_carbo+" PR"+sum_protein+" FAT"+sum_fat+"");
    }

    private double convert(double val, double weight) {
        return (val * weight) / 100;
    }

}
