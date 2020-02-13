package com.yashpawar.HST.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yashpawar.HST.R;
import com.yashpawar.HST.helper.SqliteHelper;

public class ResultActivity extends AppCompatActivity {
    double p = 0, w1 = 0, t = 0, w2 = 0, c1 = 0, c2 = 0, c3 = 0, k = 0;

    TextView et_p, et_w1, et_t, et_w2, et_c1, et_c2, et_c3, et_k, result;
    Button buttonEqual, buttonDel;
    TextView c1_cube, c2_cube, c3_cube;
    String compnay_name, c_number, c_type, orignal_tw, current_tw, loss_of_weight, date_of_taste, last_hydro_taste, water_capacity, next_hydro_test, isi_number, party_name;

    String blank = "  -  ";
    SQLiteDatabase sqLiteDatabase;
    Boolean EditTextEmptyHold;
    String SQLiteDataBaseQueryHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        SQLiteDataBaseBuild();
        SQLiteTableBuild();

        Intent i = getIntent();
        compnay_name = i.getStringExtra("compnay_name");
        c_number = i.getStringExtra("c_number");
        c_type = i.getStringExtra("c_type");
        orignal_tw = i.getStringExtra("orignal_tw");
        current_tw = i.getStringExtra("current_tw");
        loss_of_weight = i.getStringExtra("loss_of_weight");
        date_of_taste = i.getStringExtra("date_of_taste");
        last_hydro_taste = i.getStringExtra("last_hydro_taste");
        water_capacity = i.getStringExtra("water_capacity");
        next_hydro_test = i.getStringExtra("next_hydro_test");
        isi_number = i.getStringExtra("isi_number");
        party_name = i.getStringExtra("party_name");

        //Toast.makeText(getApplicationContext(), isi_number, Toast.LENGTH_LONG).show();

        buttonEqual = findViewById(R.id.buttoneql);
        buttonDel = findViewById(R.id.buttonDel);

        result = findViewById(R.id.result);
        et_p = findViewById(R.id.p);
        et_w1 = findViewById(R.id.w1);
        et_t = findViewById(R.id.t);
        et_w2 = findViewById(R.id.w2);
        et_c1 = findViewById(R.id.c1);
        et_c2 = findViewById(R.id.c2);
        et_c3 = findViewById(R.id.c3);
        et_k = findViewById(R.id.k);
        c1_cube = findViewById(R.id.c1_cube);
        c2_cube = findViewById(R.id.c2_cube);
        c3_cube = findViewById(R.id.c3_cube);
        c1_cube.setText(Html.fromHtml("C<sup><small>3</small></sup>"));
        c2_cube.setText(Html.fromHtml("C<sup><small>3</small></sup>"));
        c3_cube.setText(Html.fromHtml("C<sup><small>3</small></sup>"));

        //et_k.setText(getIntent().getStringExtra("mytext"));

        buttonEqual.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (et_p.getText().toString().trim().equalsIgnoreCase("")) {
                    et_p.setError("Please Enter Gauge of pressure");
                    EditTextEmptyHold = false;
                } else if (et_w1.getText().toString().trim().equalsIgnoreCase("")) {
                    et_w1.setError("Please Enter Mass of water");
                    EditTextEmptyHold = false;
                } else if (et_t.getText().toString().trim().equalsIgnoreCase("")) {
                    et_t.setError("Please Enter Temperature of water");
                    EditTextEmptyHold = false;
                } else if (et_w2.getText().toString().trim().equalsIgnoreCase("")) {
                    et_w2.setError("Please Enter Mass of water in the pipeline and pump");
                    EditTextEmptyHold = false;
                } else if (et_c1.getText().toString().trim().equalsIgnoreCase("")) {
                    et_c1.setError("Please Enter Initial water level in tube before applying pressure in the tube");
                    EditTextEmptyHold = false;
                } else if (et_c2.getText().toString().trim().equalsIgnoreCase("")) {
                    et_c2.setError("Please Enter Initial water level in tube after applying pressure in the tube");
                    EditTextEmptyHold = false;
                } else if (et_c3.getText().toString().trim().equalsIgnoreCase("")) {
                    et_c3.setError("Please Enter water level in tube after depressurizing the cylinder");
                    EditTextEmptyHold = false;
                } else if (et_k.getText().toString().trim().equalsIgnoreCase("")) {
                    et_k.setError("Please Enter k");
                    EditTextEmptyHold = false;
                } else {
                    EditTextEmptyHold = true;
                    p = Float.parseFloat(et_p.getText().toString());
                    w1 = Float.parseFloat(et_w1.getText().toString());
                    t = Float.parseFloat(et_t.getText().toString());
                    w2 = Float.parseFloat(et_w2.getText().toString());
                    c1 = Float.parseFloat(et_c1.getText().toString());
                    c2 = Float.parseFloat(et_c2.getText().toString());
                    c3 = Float.parseFloat(et_c3.getText().toString());
                    k = Float.parseFloat(et_k.getText().toString());

                    double a = c2 - c1;
                    double m2 = a / 1000;
                    double b = c2 - c3;
                    double m1 = w1 + w2;
                    double m = m1 + m2;
                    final double pe = a - b;
                    double c_1 = m * p;
                    double c_2 = k - 0.68 * p / 100000;
                    double c = c_1 * c_2;
                    final double tee = a - c;
                    final double te = (int) (Math.round(tee * 100)) / 100.0;
                    double res = pe * 100 / te;
                    final double finalresult = (int) (Math.round(res * 100)) / 100.0;

                    result.setText(finalresult + " %");


                    if (finalresult < 10 && finalresult > 0.00) {

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(ResultActivity.this);
                        builder1.setIcon(android.R.drawable.ic_dialog_alert);
                        builder1.setTitle("Pass");
                        builder1.setMessage("This Cylinder Pass.");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        if (EditTextEmptyHold) {
                                           /* if (last_hydro_taste.equals("") && isi_number.equals("") && party_name.equals("")) {
                                                SQLiteDataBaseQueryHolder = "INSERT INTO " + SqliteHelper.TABLE_HST + " " +
                                                        "(company_name,PARTY_NAME,ISI,cyilinder_number, cylinder_type, DATE_OF_TASTE, LAST_HYDRO_TASTE, orignal_tw, current_tw, " +
                                                        "loss_of_weight, water_capacity, Pressure, C1, C2, C3, TE, PE, REMARK, NEXT_HYDRO_TEST)"

                                                        + " VALUES "
                                                        + " ('" + compnay_name + "',"
                                                        + " '" + blank + "',"
                                                        + " '" + blank + "',"
                                                        + " '" + c_number + "',"
                                                        + " '" + c_type + "',"
                                                        + " '" + date_of_taste + "',"
                                                        + " '" + blank + "',"
                                                        + " '" + orignal_tw + "',"
                                                        + " '" + current_tw + "',"
                                                        + " '" + loss_of_weight + "',"
                                                        + " '" + water_capacity + "',"
                                                        + " '" + p + "',"
                                                        + " '" + c1 + "',"
                                                        + " '" + c2 + "',"
                                                        + " '" + c3 + "',"
                                                        + " '" + te + "',"
                                                        + " '" + finalresult + "%',"
                                                        + " 'PASS',"
                                                        + " '" + next_hydro_test + "');";

                                                sqLiteDatabase.execSQL(SQLiteDataBaseQueryHolder);
                                                sqLiteDatabase.close();
                                                finish();
                                            }
                                            else if (last_hydro_taste.equals("") && isi_number.equals("")) {
                                                SQLiteDataBaseQueryHolder = "INSERT INTO " + SqliteHelper.TABLE_HST + " " +
                                                        "(company_name,PARTY_NAME,ISI,cyilinder_number, cylinder_type, DATE_OF_TASTE, LAST_HYDRO_TASTE, orignal_tw, current_tw, " +
                                                        "loss_of_weight, water_capacity, Pressure, C1, C2, C3, TE, PE, REMARK, NEXT_HYDRO_TEST)"

                                                        + " VALUES "
                                                        + " ('" + compnay_name + "',"
                                                        + " '" + party_name + "',"
                                                        + " '" + blank + "',"
                                                        + " '" + c_number + "',"
                                                        + " '" + c_type + "',"
                                                        + " '" + date_of_taste + "',"
                                                        + " '" + blank + "',"
                                                        + " '" + orignal_tw + "',"
                                                        + " '" + current_tw + "',"
                                                        + " '" + loss_of_weight + "',"
                                                        + " '" + water_capacity + "',"
                                                        + " '" + p + "',"
                                                        + " '" + c1 + "',"
                                                        + " '" + c2 + "',"
                                                        + " '" + c3 + "',"
                                                        + " '" + te + "',"
                                                        + " '" + finalresult + "%',"
                                                        + " 'PASS',"
                                                        + " '" + next_hydro_test + "');";

                                                sqLiteDatabase.execSQL(SQLiteDataBaseQueryHolder);
                                                sqLiteDatabase.close();
                                                finish();
                                            }

                                            else if (last_hydro_taste.equals("")){
                                                SQLiteDataBaseQueryHolder = "INSERT INTO " + SqliteHelper.TABLE_HST + " " +
                                                        "(company_name,PARTY_NAME,ISI,cyilinder_number, cylinder_type, DATE_OF_TASTE, LAST_HYDRO_TASTE, orignal_tw, current_tw, " +
                                                        "loss_of_weight, water_capacity, Pressure, C1, C2, C3, TE, PE, REMARK, NEXT_HYDRO_TEST)"

                                                        + " VALUES "
                                                        + " ('" + compnay_name + "',"
                                                        + " '" + party_name + "',"
                                                        + " '" + isi_number + "',"
                                                        + " '" + c_number + "',"
                                                        + " '" + c_type + "',"
                                                        + " '" + date_of_taste + "',"
                                                        + " '" + blank + "',"
                                                        + " '" + orignal_tw + "',"
                                                        + " '" + current_tw + "',"
                                                        + " '" + loss_of_weight + "',"
                                                        + " '" + water_capacity + "',"
                                                        + " '" + p + "',"
                                                        + " '" + c1 + "',"
                                                        + " '" + c2 + "',"
                                                        + " '" + c3 + "',"
                                                        + " '" + te + "',"
                                                        + " '" + finalresult + "%',"
                                                        + " 'PASS',"
                                                        + " '" + next_hydro_test + "');";

                                                sqLiteDatabase.execSQL(SQLiteDataBaseQueryHolder);
                                                sqLiteDatabase.close();
                                                finish();
                                            }
                                            else if (party_name.equals("")){
                                                SQLiteDataBaseQueryHolder = "INSERT INTO " + SqliteHelper.TABLE_HST + " " +
                                                        "(company_name,PARTY_NAME,ISI,cyilinder_number, cylinder_type, DATE_OF_TASTE, LAST_HYDRO_TASTE, orignal_tw, current_tw, " +
                                                        "loss_of_weight, water_capacity, Pressure, C1, C2, C3, TE, PE, REMARK, NEXT_HYDRO_TEST)"

                                                        + " VALUES "
                                                        + " ('" + compnay_name + "',"
                                                        + " '" + blank + "',"
                                                        + " '" + isi_number + "',"
                                                        + " '" + c_number + "',"
                                                        + " '" + c_type + "',"
                                                        + " '" + date_of_taste + "',"
                                                        + " '" + last_hydro_taste + "',"
                                                        + " '" + orignal_tw + "',"
                                                        + " '" + current_tw + "',"
                                                        + " '" + loss_of_weight + "',"
                                                        + " '" + water_capacity + "',"
                                                        + " '" + p + "',"
                                                        + " '" + c1 + "',"
                                                        + " '" + c2 + "',"
                                                        + " '" + c3 + "',"
                                                        + " '" + te + "',"
                                                        + " '" + finalresult + "%',"
                                                        + " 'PASS',"
                                                        + " '" + next_hydro_test + "');";

                                                sqLiteDatabase.execSQL(SQLiteDataBaseQueryHolder);
                                                sqLiteDatabase.close();
                                                finish();
                                            }
                                            else if (isi_number.equals("")){
                                                SQLiteDataBaseQueryHolder = "INSERT INTO " + SqliteHelper.TABLE_HST + " " +
                                                        "(company_name,PARTY_NAME,ISI,cyilinder_number, cylinder_type, DATE_OF_TASTE, LAST_HYDRO_TASTE, orignal_tw, current_tw, " +
                                                        "loss_of_weight, water_capacity, Pressure, C1, C2, C3, TE, PE, REMARK, NEXT_HYDRO_TEST)"

                                                        + " VALUES "
                                                        + " ('" + compnay_name + "',"
                                                        + " '" + party_name + "',"
                                                        + " '" + blank + "',"
                                                        + " '" + c_number + "',"
                                                        + " '" + c_type + "',"
                                                        + " '" + date_of_taste + "',"
                                                        + " '" + last_hydro_taste + "',"
                                                        + " '" + orignal_tw + "',"
                                                        + " '" + current_tw + "',"
                                                        + " '" + loss_of_weight + "',"
                                                        + " '" + water_capacity + "',"
                                                        + " '" + p + "',"
                                                        + " '" + c1 + "',"
                                                        + " '" + c2 + "',"
                                                        + " '" + c3 + "',"
                                                        + " '" + te + "',"
                                                        + " '" + finalresult + "%',"
                                                        + " 'PASS',"
                                                        + " '" + next_hydro_test + "');";

                                                sqLiteDatabase.execSQL(SQLiteDataBaseQueryHolder);
                                                sqLiteDatabase.close();
                                                finish();
                                            }
                                            else {*/
                                                SQLiteDataBaseQueryHolder = "INSERT INTO " + SqliteHelper.TABLE_HST + " " +
                                                        "(company_name,PARTY_NAME,ISI,cyilinder_number, cylinder_type, DATE_OF_TASTE, LAST_HYDRO_TASTE, orignal_tw, current_tw, " +
                                                        "loss_of_weight, water_capacity, Pressure, C1, C2, C3, TE, PE, REMARK, NEXT_HYDRO_TEST)"

                                                        + " VALUES "
                                                        + " ('" + compnay_name + "',"
                                                        + " '" + party_name + "',"
                                                        + " '" + isi_number + "',"
                                                        + " '" + c_number + "',"
                                                        + " '" + c_type + "',"
                                                        + " '" + date_of_taste + "',"
                                                        + " '" + last_hydro_taste + "',"
                                                        + " '" + orignal_tw + "',"
                                                        + " '" + current_tw + "',"
                                                        + " '" + loss_of_weight + "',"
                                                        + " '" + water_capacity + "',"
                                                        + " '" + p + "',"
                                                        + " '" + c1 + "',"
                                                        + " '" + c2 + "',"
                                                        + " '" + c3 + "',"
                                                        + " '" + te + "',"
                                                        + " '" + finalresult + "%',"
                                                        + " 'PASS',"
                                                        + " '" + next_hydro_test + "');";

                                                sqLiteDatabase.execSQL(SQLiteDataBaseQueryHolder);
                                                sqLiteDatabase.close();
                                                finish();
                                        }else {
                                            Toast.makeText(ResultActivity.this, "Please Fill All The Required Fields.", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                        builder1.setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    } else {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(ResultActivity.this);
                        builder1.setIcon(android.R.drawable.ic_dialog_alert);
                        builder1.setTitle("Fail");
                        builder1.setMessage("This Cylinder Fail.");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }
                }
            }
        });

        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p = 0.0;
                w1 = 0.0;
                t = 0.0;
                w2 = 0.0;
                c1 = 0.0;
                c2 = 0.0;
                c3 = 0.0;
                k = 0.0;
                result.setText("");
                et_p.setText("");
                et_w1.setText("");
                et_t.setText("");
                et_w2.setText("");
                et_c1.setText("");
                et_c2.setText("");
                et_c3.setText("");
                et_k.setText("");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newact:
                Intent intent = new Intent(ResultActivity.this, NewActivity.class);
                startActivity(intent);
                return true;
        }
        return (super.onOptionsItemSelected(item));
    }

    public void SQLiteDataBaseBuild() {

        sqLiteDatabase = openOrCreateDatabase(SqliteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }

    public void SQLiteTableBuild() {

        sqLiteDatabase.execSQL(" CREATE TABLE IF NOT EXISTS " + SqliteHelper.TABLE_HST +
                " ( "
                + SqliteHelper.ID + " INTEGER PRIMARY KEY, "
                + SqliteHelper.COMPANY_NAME + " VARCHAR, "
                + SqliteHelper.PARTY_NAME + " VARCHAR, "
                + SqliteHelper.ISI + " VARCHAR, "
                + SqliteHelper.CYLINDER_NUMBER + " VARCHAR, "
                + SqliteHelper.CYLINDER_TYPE + " VARCHAR, "
                + SqliteHelper.DATE_OF_TASTE + " VARCHAR, "
                + SqliteHelper.LAST_HYDRO_TASTE + " VARCHAR, "
                + SqliteHelper.ORIGINAL_TW + " VARCHAR, "
                + SqliteHelper.CURRENT_TW + " VARCHAR, "
                + SqliteHelper.LOSS_OF_WEIGHT + " VARCHAR,"
                + SqliteHelper.WATER_CAPICITY + " VARCHAR,"
                + SqliteHelper.PRESSURE + " VARCHAR, "
                + SqliteHelper.C1 + " VARCHAR, "
                + SqliteHelper.C2 + " VARCHAR, "
                + SqliteHelper.C3 + " VARCHAR, "
                + SqliteHelper.TE + " VARCHAR, "
                + SqliteHelper.PE + " VARCHAR, "
                + SqliteHelper.REMARK + " VARCHAR,"
                + SqliteHelper.NEXT_HYDRO_TEST + " VARCHAR )"
                + "");
    }
}
