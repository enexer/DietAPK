package com.example.as.dieta;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView textViewSeek;
    private EditText editTextAge, editTextWeight, editTextHeight;
    private TextView textViewResult;
    private RadioButton radioButton;
    private Spinner spinner;
    private Button button, buttonHelpActivity;
    private RadioGroup radioGroup;
    public long repeatTime = 0;





    private void initializeVariables() {

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textViewSeek = (TextView) findViewById(R.id.textViewSeek);
        textViewResult = (TextView) findViewById(R.id.textViewResult);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextHeight = (EditText) findViewById(R.id.editTextHeight);
        editTextWeight = (EditText) findViewById(R.id.editTextWeight);
       // radioButton = (RadioButton) findViewById(R.id.radioButton);
        spinner = (Spinner) findViewById(R.id.spinner);
        button = (Button) findViewById(R.id.button);
        buttonHelpActivity = (Button) findViewById(R.id.buttonHelpActivity);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);

    }

//    public Dialog createDialog() {
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
//        dialogBuilder.setTitle("Dialog title");
//        dialogBuilder.setMessage("Dialog content text...");
//        return dialogBuilder.create();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeVariables();
        seekBar.setProgress(60);
        textViewSeek.setText("1."+String.valueOf(seekBar.getProgress()));
        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);


        SharedPreferences sharedPrefRead = PreferenceManager.getDefaultSharedPreferences(getBaseContext());//MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        String sAge = sharedPrefRead.getString("age", "");
        String sWeight = sharedPrefRead.getString("weight", "");
        String sHeight = sharedPrefRead.getString("height", "");
        String sCal = sharedPrefRead.getString("calories", "");
        String sP = sharedPrefRead.getString("protein", "");
        String sC = sharedPrefRead.getString("carbo", "");
        String sF = sharedPrefRead.getString("fat", "");
        String sDate = sharedPrefRead.getString("date", "");

        editTextAge.setText(sAge);
        editTextWeight.setText(sWeight);
        editTextHeight.setText(sHeight);
        textViewResult.setText("Data: "+sDate+"\nKalorie: "+sCal+" kcal\nBialko: "+sP+" g\nWelgowodany: "+sC+" g\nTluszcz: "+sF+" g\n");

        buttonHelpActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ////////////////////ALERT///////////////////
                //final EditText taskEditText = new EditText(MainActivity.this);
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Aktywność");
                //alertDialog.setView(taskEditText);
                alertDialog.setMessage("Pomoc:\n"+
                        "1,0 – siedzący tryb życia, brak ćwiczeń\n"+"1,2 – praca siedząca, aktywność sportowa na minimalnym poziomie (spacer)\n"
                        +"1,4 – praca siedząca + trening  1 – 2 razy w tygodniu\n"+"1,6 – praca nie fizyczna + trening  (umiarkowana aktywność)\n"
                        +"1,8 – praca fizyczna + trening  5 razy w tygodniu\n"+"2,0 – ciężka praca fizyczna + codzienny trening\n");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                ////////////////////ALERT///////////////////
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String aAge = editTextAge.getText().toString();
                String aWeight = editTextWeight.getText().toString();
                String aHeight = editTextHeight.getText().toString();
                String aActivity= textViewSeek.getText().toString();
                String aTarget = spinner.getSelectedItem().toString();
                String aGender = ((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                String bb = String.valueOf(seekBar.getProgress());
                String bbb = textViewSeek.getText().toString();

                double age = Double.parseDouble(aAge);
                double weight = Double.parseDouble(aWeight);
                double height = Double.parseDouble(aHeight);
                double activity = Double.parseDouble(aActivity);




                String number, nGender;
                double bmr = 0;

                double target = 0;
                int num = (int) spinner.getSelectedItemId();
                switch (num) {
                    case 0:
                        target=-0.10;
                        break;
                    case 1:
                        target =0;
                        break;
                    case 2:
                        target = 0.10;
                        break;
                    default:
                        target = 100;
                        break;
                }

//                Wyliczenie podstawowej przemiany materii organizmu
//                Wzór na wyliczenie BMR (podstawowej przemiany materii organizmu):
//                Dla mężczyzn:    66 + (13,7 x waga w kg) + (5 x wzrost w cm) – (6,8 x wiek)
//                Dla kobiet:          655 + (9,6 x waga w kg) + (1,7 x wzrost w cm) – (4,7 x wiek)


                if(aGender.charAt(0)=='M'){
                    bmr = 66+(weight*13.7)+(5*height)-(6.8*age);
                    System.out.println("BMR: "+bmr);
                }else if (aGender.charAt(0)=='K'){
                    bmr = 655+(weight*9.6)+(1.7*height)-(4.7*age);
                    System.out.println("BMR: "+bmr);
                }

//                Określenie dziennej aktywności fizycznej (Wybraną wartość mnożymy przez BMR):

                double bmr2 = bmr*activity;

//                Wyliczenie dziennego spożycia na wzrost/redukcje/utrzymanie masy

                double bmrTarget=bmr2*target;
                System.out.println("bmrTARGET:"+bmrTarget);
                double bmr3= bmr2+bmrTarget;
                String calories = String.valueOf(Math.round(bmr3));
                System.out.println("bmr3:"+bmr3);

//                Podział makroskładników
//                Białko – 30% z 3492kcal = 1047,6 : 4 = 261,9g dziennie
//                Węglowodany – 50% z 3492kcal = 1746 : 4 = 436,5g dziennie
//                Tłuszcz – 20% z 3492kcal = 698,4 : 9 = 77,6g dziennie

                int protein = (int) Math.round((bmr3*0.3)/4);
                int carbo = (int) Math.round((bmr3*0.5)/4);
                int fat = (int) Math.round((bmr3*0.2)/9);



                //System.out.println("wynik spinner: "+number+", wynik radio:"+number2+", wynik pasek:"+bbb+", XD:"+(double)5/2);
                    System.out.println(aAge+", "+aHeight+", "+aWeight+", "+aActivity+", "+aTarget+", "+aGender+"\nBMRACT: "+bmr2);
               // System.out.println(spinner.getSelectedItemId()+" ,<-id, pos-> "+ spinner.getSelectedItemPosition());
                textViewResult.setText("Kalorie: "+calories+" Kcal\nBialko: "+protein+"g\nWeglowodany: "+carbo+"g\nTluszcz: "+fat+"g");

                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String currDateString = dateFormat.format(new Date());
                System.out.println(currDateString);



                databaseAccess.open();

                if (databaseAccess.insertMeasurement(calories,currDateString)) {
                    System.out.println("Zapisano: "+calories+", data:"+currDateString);
                } else {
                    System.out.println("blad w zapisie");
                }


                //SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("calories", calories);
                editor.putString("carbo", Integer.toString(carbo));
                editor.putString("protein", Integer.toString(protein));
                editor.putString("fat", Integer.toString(fat));
                editor.putString("date", currDateString);
                editor.putString("age", aAge);
                editor.putString("weight", aWeight);
                editor.putString("height", aHeight);
                editor.commit();


//                SharedPreferences sharedPref2 = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
//                String txt = sharedPref.getString("calories", "pp");
//                System.out.println("KALORIEEEE"+txt);

            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            long prog=0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                // Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
                prog=progresValue;
                if(prog==100){
                    textViewSeek.setText("2.0");
                }else{
                    textViewSeek.setText("1."+prog + "");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(prog==100){
                    textViewSeek.setText("2.0");
                }else{
                    textViewSeek.setText("1."+prog + "");
                }
                repeatTime = prog*1000;
                System.out.println(repeatTime);
                // Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
            }
        });

        final List<String> codeList = new ArrayList<String>(Arrays.asList("Redukcja", "Utrzymanie", "Masa"));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, codeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);
        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if(position>1){
                    System.out.println(">1");
                }
                System.out.println(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }////////// ON CREATE//////////////////////////////////////////////////////////////////////////



    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButtonMale:
                if (checked)
                    // Pirates are the best
                System.out.println("tak");
                    break;
            case R.id.radioButtonFemale:
                if (checked)
                    // Ninjas rule
                    System.out.println("nie");
                    break;
        }
    }
//    public void onCheckboxClicked(View view) {
//        // Is the view now checked?
//        boolean checked = ((CheckBox) view).isChecked();
//
//        // Check which checkbox was clicked
//        switch(view.getId()) {
//            case R.id.checkbox_meat:
//                if (checked)
//                    System.out.println("MIESKO");
//                // Put some meat on the sandwich
//            else
//                // Remove the meat
//                break;
//            case R.id.checkbox_cheese:
//                if (checked)
//                    System.out.println("SER");
//                // Cheese me
//            else
//                // I'm lactose intolerant
//                break;
//            // TODO: Veggie sandwich
//        }
//    }
}
