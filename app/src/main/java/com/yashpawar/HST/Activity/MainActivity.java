package com.yashpawar.HST.Activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.yashpawar.HST.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText Company_name, Cylinder_Number, Cylinder_type, isi_number, party_name;
    Button btn_submit, btn_clear;
    TextView date_of_test, last_hydro_test;
    String next_hydro_test;

    Calendar myCalendar1 = Calendar.getInstance();
    Calendar myCalendar2 = Calendar.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

        btn_clear = findViewById(R.id.clear);
        btn_submit = findViewById(R.id.submit);
        date_of_test = findViewById(R.id.date_of_test);
        last_hydro_test = findViewById(R.id.last_hydro_test);
        Company_name = findViewById(R.id.company_name);
        Cylinder_Number = findViewById(R.id.cylinder_number);
        Cylinder_type = findViewById(R.id.cylinder_type);
        isi_number = findViewById(R.id.isi_number);
        party_name = findViewById(R.id.party_name);


        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                date_of_test.setText(sdf.format(myCalendar1.getTime()));
            }
        };

        date_of_test.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                closeKeyBoard();
                new DatePickerDialog(MainActivity.this, date1,
                        myCalendar1.get(Calendar.YEAR),
                        myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        last_hydro_test.setOnClickListener(new View.OnClickListener() {

            DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    myCalendar2.set(Calendar.YEAR, year);
                    myCalendar2.set(Calendar.MONTH, monthOfYear);
                    myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    String myFormat = "dd/MM/yyyy"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    last_hydro_test.setText(sdf.format(myCalendar2.getTime()));
                }
            };
            @Override
            public void onClick(View v) {
                closeKeyBoard();
                new DatePickerDialog(MainActivity.this, date2,
                        myCalendar2.get(Calendar.YEAR),
                        myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                if (Company_name.getText().toString().trim().equalsIgnoreCase("")) {
                    Company_name.setError("Please Enter Company Name");
                } else if (Cylinder_Number.getText().toString().trim().equalsIgnoreCase("")) {
                    Cylinder_Number.setError("Please enter Cylinder Number");
                } else if (Cylinder_type.getText().toString().trim().equalsIgnoreCase("")) {
                    Cylinder_type.setError("Please enter Cylinder Type");
                } else if (date_of_test.getText().toString().trim().equalsIgnoreCase("")) {
                    date_of_test.setError("Please enter Date of Test");
                } /*else if (last_hydro_test.getText().toString().trim().equalsIgnoreCase("")) {
                    last_hydro_test.setError("Please enter Last Hydro Test");
                }*/
                else {
                    String compnay_name = Company_name.getText().toString();
                    String c_number = Cylinder_Number.getText().toString();
                    String c_type = Cylinder_type.getText().toString();
                    String date_of_tastee = date_of_test.getText().toString();
                    String last_hydro_tastee = last_hydro_test.getText().toString();
                    String isi_numberr = isi_number.getText().toString();
                    String party_namee = party_name.getText().toString();

                    String myFormat = "dd/MM/yyyy"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    Calendar NHT = Calendar.getInstance();
                    NHT.add(myCalendar1.YEAR, 5);
                    next_hydro_test = sdf.format(NHT.getTime());

                    //Date nexthydrotest = NHT.getTime();
                    //next_hydro_test = myCalendar1.get(Calendar.DATE)+"/"+(myCalendar1.get(Calendar.MONTH) + 1)+"/"+NHT.get(Calendar.YEAR);
                    //next_hydro_test = sdf.format(beforedate);

                    //Toast.makeText(getApplicationContext(),next_hydro_test, Toast.LENGTH_LONG).show();

                    Intent myIntent = new Intent(v.getContext(), CheckActivity.class);
                    myIntent.putExtra("compnay_name",compnay_name);
                    myIntent.putExtra("c_number",c_number);
                    myIntent.putExtra("c_type",c_type);
                    myIntent.putExtra("date_of_taste",date_of_tastee);
                    myIntent.putExtra("last_hydro_taste",last_hydro_tastee);
                    myIntent.putExtra("next_hydro_test",next_hydro_test);
                    myIntent.putExtra("isi_number",isi_numberr);
                    myIntent.putExtra("party_name",party_namee);
                    startActivity(myIntent);


                   /* Company_name.setText("");
                    Cylinder_Number.setText("");
                    Cylinder_type.setText("");
                    date_of_test.setText(null);
                    last_hydro_test.setText("");*/
                }
            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                party_name.setText("");
                Company_name.setText("");
                Cylinder_Number.setText("");
                Cylinder_type.setText("");
                date_of_test.setText("");
                last_hydro_test.setText("");
                isi_number.setText("");
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.newact:
            Intent intent = new Intent(MainActivity.this, DisplayDataActivity.class);
            startActivity(intent);
            return true;

        /*case R.id.logout:
            SharedPreferences sp=getSharedPreferences("login",MODE_PRIVATE);
            SharedPreferences.Editor e=sp.edit();
            e.clear();
            e.commit();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();   //finish current activity*/
    }
        return(super.onOptionsItemSelected(item));
    }
    private void closeKeyBoard(){
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
