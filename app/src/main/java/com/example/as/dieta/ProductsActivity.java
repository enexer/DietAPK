package com.example.as.dieta;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.as.dieta.realm.SelectedProducts;
import com.example.as.dieta.realm.SelectedProductsDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {

    private ListView listView;
    public static List<String> quotes;
    private SelectedProductsDao selectedProductsDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        Intent intent = getIntent();
        final String day_pos = intent.getStringExtra("day_pos");
        final String meal_pos = intent.getStringExtra("meal_pos");
        System.out.println("day " + day_pos + " meal" + meal_pos);
        final boolean directed = intent.getBooleanExtra("directed", false);

        Button bt = (Button) findViewById(R.id.buttonFindProduct);
        final EditText et = (EditText) findViewById(R.id.editTextFindProduct);
        this.listView = (ListView) findViewById(R.id.listView);
        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);

        selectedProductsDao = new SelectedProductsDao(this);

        databaseAccess.open();
        fillListView(databaseAccess.getProducts());
//        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
//        databaseAccess.open();

        //databaseAccess.close();
//        if(databaseAccess.insertMeasurement("123","12-12-2002")){
//            Toast.makeText(ProductsActivity.this, "DODANO!" ,Toast.LENGTH_SHORT).show();
//        }else{
//            Toast.makeText(ProductsActivity.this, "Blad podczas dodawania!" ,Toast.LENGTH_SHORT).show();
//        }

        // quotes = databaseAccess.getQuotes();

        // adp(quotes);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, quotes);
//        this.listView.setAdapter(adapter);
        if (directed == true) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {

                    HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                    final String v_id = map.get("id");
                    final String v_name = map.get("name");
                    String v_kcal = map.get("kcal");
                    String v_carbo = map.get("carbo");
                    String v_protein = map.get("protein");
                    String v_fat = map.get("fat");
                    Toast.makeText(ProductsActivity.this, "CLIKCED AT: " + v_id, Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(getApplicationContext(),Special.class);
//                intent.putExtra("lol",value);
//                intent.putExtra("lol2",value2);
//                startActivity(intent);
                    final EditText taskEditText = new EditText(ProductsActivity.this);
                    final TextView taskTextView1 = new TextView(ProductsActivity.this);
                    final TextView taskEditTextKcal = new TextView(ProductsActivity.this);
                    final TextView taskEditTextCarbo = new TextView(ProductsActivity.this);
                    final TextView taskEditTextProtein = new TextView(ProductsActivity.this);
                    final TextView taskEditTextFat = new TextView(ProductsActivity.this);

                    AlertDialog alertDialog = new AlertDialog.Builder(ProductsActivity.this).create();
                    alertDialog.setTitle("Dodaj Produkt:");


//                alertDialog.setView(taskEditTextKcal, 10, 10, 10, 10);
//               // taskEditTextKcal.setTextColor(Color.GREEN);
//
////                alertDialog.setView(taskEditTextProtein);
////                alertDialog.setView(taskEditTextFat);
//                taskEditTextCarbo.setText(v_carbo);
//                taskEditTextCarbo.setBackgroundColor(Color.BLUE);
//                taskEditTextCarbo.setWidth(35);
//                alertDialog.setView(taskEditTextCarbo, 10, 100, 10, 0);


                    final Context context = getApplicationContext();
                    //RELATIVE LAYOUT
                    RelativeLayout relativeLayout = new RelativeLayout(context);
                    //FIRST LINEAR LAYOUT
                    LinearLayout layout = new LinearLayout(context);
                    layout.setOrientation(LinearLayout.HORIZONTAL);

                    taskEditTextKcal.setText(v_kcal);
                    taskEditTextKcal.setBackgroundColor(context.getResources().getColor(R.color.red_500));
                    taskEditTextKcal.setWidth(100);
                    //taskEditTextKcal.setPadding(0, 0, 5, 0);

                    taskEditTextCarbo.setText(v_carbo);
                    taskEditTextCarbo.setBackgroundColor(context.getResources().getColor(R.color.yellow_500));
                    taskEditTextCarbo.setWidth(100);
                    //taskEditTextKcal.setPadding(0, 0, 5, 0);

                    taskEditTextProtein.setText(v_protein);
                    taskEditTextProtein.setBackgroundColor(context.getResources().getColor(R.color.orange_500));
                    taskEditTextProtein.setWidth(100);
                    //taskEditTextKcal.setPadding(0, 0, 5, 0);

                    taskEditTextFat.setText(v_fat);
                    taskEditTextFat.setBackgroundColor(context.getResources().getColor(R.color.blue_500));
                    taskEditTextFat.setWidth(100);
                    // taskEditTextKcal.setPadding(0, 0, 5, 0);

                    layout.addView(taskEditTextKcal);
                    layout.addView(taskEditTextCarbo);
                    layout.addView(taskEditTextProtein);
                    layout.addView(taskEditTextFat);


                    //SECOND LINEAR LAYOUT
                    LinearLayout layout2 = new LinearLayout(context);
                    layout2.setOrientation(LinearLayout.VERTICAL);

                    taskTextView1.setText("Waga produktu (g):");
                    layout2.addView(taskTextView1);
                    taskEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    layout2.addView(taskEditText);
                    layout2.setPadding(0, 100, 0, 0);


                    //ADD LAYOUTS TO PARENT
                    relativeLayout.addView(layout);
                    relativeLayout.addView(layout2);
                    relativeLayout.setPadding(60, 0, 0, 0);
                    //  relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
                    alertDialog.setView(relativeLayout, 10, 5, 0, 10);


                    alertDialog.setMessage(v_name + "\nKCAL  |  B  |  T  |  W");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    String id, name = null;
                                    String weightEt = String.valueOf(taskEditText.getText());
                                    double weight = Double.parseDouble(weightEt);
                                    double kcal = 0, carbo = 0, protein = 0, fat = 0;

                                    // Pobranie z bazy SQlite produktu o danym id
                                    Cursor mCursor = databaseAccess.getProduct(v_id);
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
                                    kcal= convert(kcal,weight);
                                    carbo= convert(carbo,weight);
                                    protein= convert(protein,weight);
                                    fat= convert(fat,weight);

                                    ////////////////////////////////////////////////////
                                    System.out.println(weight + " " + day_pos + " " + meal_pos);

                                    /////////////REALM////////////////////////
                                    // Zapis do bazy Realm (waga, dzien, posilek, id produktu)
                                    SelectedProducts selectedProducts = new SelectedProducts();
                                    if (weightEt.length() > 0) {
                                        selectedProducts.setWeight(Integer.parseInt(weightEt));
                                        selectedProducts.setDayId(day_pos);
                                        selectedProducts.setMealId(meal_pos);
                                        selectedProducts.setProductId(v_id);
                                        selectedProducts.setKcal(kcal);
                                        selectedProducts.setCarbo(carbo);
                                        selectedProducts.setProtein(protein);
                                        selectedProducts.setFat(fat);
                                    }
                                    selectedProductsDao.insertNote(selectedProducts);
                                    selectedProductsDao.close();
                                    ////////////////////////////////
                                    Intent intent = new Intent(getApplicationContext(), FoodListActivity.class);
                                    //FLAG_ACTIVITY_NEW_TASK
                                    //wyczyszczenie back stack
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("day", "directed");
                                    intent.putExtra("day_pos", day_pos);
                                    intent.putExtra("meal", "directed");
                                    intent.putExtra("meal_pos", meal_pos);
                                    startActivity(intent);

                                    dialog.dismiss();
                                }
                            });


                    alertDialog.show();
                }

            });
        }

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adp(databaseAccess.findQuotes(et.getText().toString()));
                fillListView(databaseAccess.findProducts(et.getText().toString()));
                // listView.refreshDrawableState();
            }
        });
    }

    @Override
    protected void onDestroy() {
        // zamykamy instancję Realma
        selectedProductsDao.close();
        super.onDestroy();
    }

    //    public void adp(List<String> quotes){
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, quotes);
//        this.listView.setAdapter(adapter);
//    }
    private double convert(double val, double weight) {
        return (val * weight) / 100;
    }

    public void fillListView(Cursor mCursor) {
//        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
//        databaseAccess.open();
        //mCursor = databaseAccess.getProducts();
        ArrayList<HashMap<String, Object>> productsList;
        productsList = new ArrayList<>();
        mCursor.moveToFirst();

        while (!mCursor.isAfterLast()) {
            HashMap<String, Object> contact2 = new HashMap<>();
            String id = mCursor.getString(mCursor.getColumnIndex("_id"));
            String name = mCursor.getString(mCursor.getColumnIndex("c0name"));
            String kcal = mCursor.getString(mCursor.getColumnIndex("kcal"));
            String carbo = mCursor.getString(mCursor.getColumnIndex("W"));
            String protein = mCursor.getString(mCursor.getColumnIndex("B"));
            String fat = mCursor.getString(mCursor.getColumnIndex("T"));


            contact2.put("id", id);
            contact2.put("name", name);
            contact2.put("kcal", kcal);
            contact2.put("carbo", carbo);
            contact2.put("protein", protein);
            contact2.put("fat", fat);
            productsList.add(contact2);
            mCursor.moveToNext();
        }

        ListAdapter adapter = new SimpleAdapter(ProductsActivity.this, productsList,
                R.layout.list_item, new String[]{"name", "kcal", "carbo", "protein", "fat"},
                new int[]{R.id.textViewPListName, R.id.textViewPListK, R.id.textViewPListC, R.id.textViewPListP, R.id.textViewPListF});
        listView.setAdapter(adapter);
    }

}
