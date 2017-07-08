package com.example.as.dieta;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductsActivity extends AppCompatActivity {

    private ListView listView;
    private SelectedProductsDao selectedProductsDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        Intent intent = getIntent();
        final String day_pos = intent.getStringExtra("day_pos");
        final String meal_pos = intent.getStringExtra("meal_pos");
        final String day = intent.getStringExtra("day");
        final String meal = intent.getStringExtra("meal");
        System.out.println("day " + day_pos + " meal" + meal_pos);
        final boolean directed = intent.getBooleanExtra("directed", false);

        Button bt = (Button) findViewById(R.id.buttonFindProduct);
        Button btAdd = (Button) findViewById(R.id.buttonAddProduct);
        final EditText et = (EditText) findViewById(R.id.editTextFindProduct);
        this.listView = (ListView) findViewById(R.id.listView);
        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);

        selectedProductsDao = new SelectedProductsDao(this);

        databaseAccess.open();
        fillListView(databaseAccess.getProducts());

        // W przypadku dodawania produktu do posilku
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
                   // Toast.makeText(ProductsActivity.this, "CLIKCED AT: " + v_id, Toast.LENGTH_LONG).show();

                    final EditText taskEditText = new EditText(ProductsActivity.this);
                    final TextView taskTextView1 = new TextView(ProductsActivity.this);
                    final TextView taskEditTextKcal = new TextView(ProductsActivity.this);
                    final TextView taskEditTextCarbo = new TextView(ProductsActivity.this);
                    final TextView taskEditTextProtein = new TextView(ProductsActivity.this);
                    final TextView taskEditTextFat = new TextView(ProductsActivity.this);

                    AlertDialog alertDialog = new AlertDialog.Builder(ProductsActivity.this).create();
                    alertDialog.setTitle("Dodaj Produkt:");


                    final Context context = getApplicationContext();
                    //RELATIVE LAYOUT
                    RelativeLayout relativeLayout = new RelativeLayout(context);
                    //FIRST LINEAR LAYOUT
                    LinearLayout layout = new LinearLayout(context);
                    layout.setOrientation(LinearLayout.HORIZONTAL);

                    taskEditTextKcal.setText(v_kcal);
                    taskEditTextKcal.setBackgroundColor(context.getResources().getColor(R.color.red_500));
                    taskEditTextKcal.setWidth(100);


                    taskEditTextCarbo.setText(v_carbo);
                    taskEditTextCarbo.setBackgroundColor(context.getResources().getColor(R.color.yellow_500));
                    taskEditTextCarbo.setWidth(100);


                    taskEditTextProtein.setText(v_protein);
                    taskEditTextProtein.setBackgroundColor(context.getResources().getColor(R.color.orange_500));
                    taskEditTextProtein.setWidth(100);


                    taskEditTextFat.setText(v_fat);
                    taskEditTextFat.setBackgroundColor(context.getResources().getColor(R.color.blue_500));
                    taskEditTextFat.setWidth(100);


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


                    //DODANIE LINEAR LAYOUT 1,2 DO PARENT RELATIVE LAYOUT
                    relativeLayout.addView(layout);
                    relativeLayout.addView(layout2);
                    relativeLayout.setPadding(20, 0, 0, 0);
                    //  relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
                    alertDialog.setView(relativeLayout, 10, 5, 0, 10);

                    // Okno dialogowe z dodawaniem produktu do posilku
                    alertDialog.setMessage(v_name + "\nKCAL  |  W  |  B  |  T");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
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
                                        kcal = convert(kcal, weight);
                                        carbo = convert(carbo, weight);
                                        protein = convert(protein, weight);
                                        fat = convert(fat, weight);

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
                                        intent.putExtra("day", day);
                                        intent.putExtra("day_pos", day_pos);
                                        intent.putExtra("meal", meal);
                                        intent.putExtra("meal_pos", meal_pos);
                                        startActivity(intent);

                                        dialog.dismiss();
                                    }catch (Exception e){
                                        Toast.makeText(ProductsActivity.this, "WPISZ POPRAWNE WARTOSCI!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                    alertDialog.show();
                }

            });
        } else {
            btAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final EditText taskEditTextName = new EditText(ProductsActivity.this);
                    final EditText taskEditTextKcal = new EditText(ProductsActivity.this);
                    final EditText taskEditTextProtein = new EditText(ProductsActivity.this);
                    final EditText taskEditTextCarbo = new EditText(ProductsActivity.this);
                    final EditText taskEditTextFat = new EditText(ProductsActivity.this);

                    final TextView taskTextViewName = new TextView(ProductsActivity.this);
                    final TextView taskTextViewKcal = new TextView(ProductsActivity.this);
                    final TextView taskTextViewProtein = new TextView(ProductsActivity.this);
                    final TextView taskTextViewCarbo = new TextView(ProductsActivity.this);
                    final TextView taskTextViewFat = new TextView(ProductsActivity.this);


                    AlertDialog alertDialog = new AlertDialog.Builder(ProductsActivity.this).create();
                    alertDialog.setTitle("Dodaj Produkt:");

                    final Context context = getApplicationContext();
                    //RELATIVE LAYOUT
                    RelativeLayout relativeLayout = new RelativeLayout(context);
                    //SECOND LINEAR LAYOUT
                    LinearLayout layout2 = new LinearLayout(context);
                    layout2.setOrientation(LinearLayout.VERTICAL);

                    taskTextViewName.setText("Nazwa produktu:");
                    layout2.addView(taskTextViewName);
                    taskEditTextName.setInputType(InputType.TYPE_CLASS_TEXT);
                    layout2.addView(taskEditTextName);

                    taskTextViewKcal.setText("Kalorie (kcal):");
                    layout2.addView(taskTextViewKcal);
                    taskEditTextKcal.setInputType(InputType.TYPE_CLASS_NUMBER);
                    layout2.addView(taskEditTextKcal);

                    taskTextViewCarbo.setText("Węglowodany:");
                    layout2.addView(taskTextViewCarbo);
                    taskEditTextCarbo.setInputType(InputType.TYPE_CLASS_NUMBER);
                    layout2.addView(taskEditTextCarbo);

                    taskTextViewProtein.setText("Białko:");
                    layout2.addView(taskTextViewProtein);
                    taskEditTextProtein.setInputType(InputType.TYPE_CLASS_NUMBER);
                    layout2.addView(taskEditTextProtein);

                    taskTextViewFat.setText("Tłuszcz:");
                    layout2.addView(taskTextViewFat);
                    taskEditTextFat.setInputType(InputType.TYPE_CLASS_NUMBER);
                    layout2.addView(taskEditTextFat);


                    layout2.setPadding(0, 0, 0, 0);

                    //DODANIE LINEAR LAYOUT 1,2 DO PARENT RELATIVE LAYOUT
                    relativeLayout.addView(layout2);
                    relativeLayout.setPadding(60, 0, 0, 0);
                    alertDialog.setView(relativeLayout, 10, 5, 0, 10);
                    // Okno dialogowe z dodawaniem produktu do posilku
                    alertDialog.setMessage("Makroskladniki dla 100 g produktu.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    String pName = String.valueOf(taskEditTextName.getText());
                                    String pKcal = String.valueOf(taskEditTextKcal.getText());
                                    String pCarbo = String.valueOf(taskEditTextCarbo.getText());
                                    String pProtein = String.valueOf(taskEditTextProtein.getText());
                                    String pFat = String.valueOf(taskEditTextFat.getText());

                                    try {
                                        if (databaseAccess.addProduct(pName, Double.parseDouble(pKcal), Double.parseDouble(pCarbo), Double.parseDouble(pProtein), Double.parseDouble(pFat))) {
                                            Toast.makeText(ProductsActivity.this, "DODANO PRODUKT DO BAZY!", Toast.LENGTH_LONG).show();
                                            fillListView(databaseAccess.getProducts());
                                            dialog.dismiss();
                                        } else {
                                            Toast.makeText(ProductsActivity.this, "NIE DODANO PRODUKTU!", Toast.LENGTH_LONG).show();
                                            dialog.dismiss();
                                        }
                                    } catch (Exception e) {
                                        Toast.makeText(ProductsActivity.this, "WPISZ POPRAWNE WARTOSCI!", Toast.LENGTH_LONG).show();
                                    }


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

    private double convert(double val, double weight) {
        return (val * weight) / 100;
    }

    public void fillListView(Cursor mCursor) {

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
