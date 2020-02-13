package com.yashpawar.HST.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.yashpawar.HST.R;

public class CheckActivity extends AppCompatActivity {
    double dentcheck = 0, og_tw = 0, cu_tw = 0, weight_with_waterr = 0;
    EditText et_dentcheck, et_cu_tw, et_og_tw, weight_with_water;
    TextView tv_tw;
    Button btn_check, btn_clear;
    Switch rustedcyl, hummingsound;
    String compnay_name, c_number, c_type, date_of_taste, last_hydro_taste, next_hydro_test, isi_number, party_name;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        btn_check = findViewById(R.id.check);
        btn_clear = findViewById(R.id.clear);
        et_dentcheck = findViewById(R.id.dentcheck);
        et_cu_tw = findViewById(R.id.cu_tw);
        et_og_tw = findViewById(R.id.og_tw);
        tv_tw = findViewById(R.id.tv_tw);
        rustedcyl = findViewById(R.id.rustedcyl);
        hummingsound = findViewById(R.id.hummingsound);
        weight_with_water = findViewById(R.id.weight_with_water);

        Intent i = getIntent();
        compnay_name = i.getStringExtra("compnay_name");
        c_number = i.getStringExtra("c_number");
        c_type = i.getStringExtra("c_type");
        date_of_taste = i.getStringExtra("date_of_taste");
        last_hydro_taste = i.getStringExtra("last_hydro_taste");
        next_hydro_test = i.getStringExtra("next_hydro_test");
        isi_number = i.getStringExtra("isi_number");
        party_name = i.getStringExtra("party_name");

        //Toast.makeText(getApplicationContext(), party_name, Toast.LENGTH_LONG).show();

        btn_check.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (et_dentcheck.getText().toString().trim().equalsIgnoreCase("")) {
                    et_dentcheck.setError("Please Enter Value");
                } else if (et_og_tw.getText().toString().trim().equalsIgnoreCase("")) {
                    et_og_tw.setError("Please enter Original Tare Weight");
                } else if (et_cu_tw.getText().toString().trim().equalsIgnoreCase("")) {
                    et_cu_tw.setError("Please enter Current Tare Weight");
                } else if (weight_with_water.getText().toString().trim().equalsIgnoreCase("")) {
                    weight_with_water.setError("Please enter Weight with Water");
                }
                else {
                    dentcheck = Float.parseFloat(et_dentcheck.getText().toString());
                    og_tw = Float.parseFloat(et_og_tw.getText().toString());
                    cu_tw = Float.parseFloat(et_cu_tw.getText().toString());
                    weight_with_waterr = Float.parseFloat(weight_with_water.getText().toString());

                    double a = og_tw - cu_tw;
                    double b = og_tw + cu_tw;
                    double c = b / 2;
                    double ans = a / c * 100;

                    double loss_of_weight = (int)(Math.round(ans * 100))/100.0;
                    tv_tw.setText(loss_of_weight + " %");

                    final double water_capacity =  weight_with_waterr - cu_tw;

                    if (dentcheck > 3) {
                        //Toast.makeText(getApplicationContext(),"Nothing Happened",Toast.LENGTH_LONG).show();
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(CheckActivity.this);
                        builder1.setIcon(android.R.drawable.ic_dialog_alert);
                        builder1.setTitle("Fail");
                        builder1.setMessage("Dent is greater than 3mm.");
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
                    else if (hummingsound.isChecked() && !rustedcyl.isChecked()) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(CheckActivity.this);
                    builder1.setIcon(android.R.drawable.ic_dialog_alert);
                    builder1.setTitle("Fail");
                    builder1.setMessage("Cylinder is Rusted and don't have Humming Sound.");
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
                    else if (!rustedcyl.isChecked()) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(CheckActivity.this);
                    builder1.setIcon(android.R.drawable.ic_dialog_alert);
                    builder1.setTitle("Fail");
                    builder1.setMessage("Cylinder is Rusted.");
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
                    else if (hummingsound.isChecked()) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(CheckActivity.this);
                    builder1.setIcon(android.R.drawable.ic_dialog_alert);
                    builder1.setTitle("Fail");
                    builder1.setMessage("Cylinder has Humming Sound.");
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
                    else if (loss_of_weight > 5){
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(CheckActivity.this);
                        builder1.setIcon(android.R.drawable.ic_dialog_alert);
                        builder1.setTitle("Fail");
                        builder1.setMessage("Tare Weight is Greater than 5%.");
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
                    else if (loss_of_weight < 0.00){
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(CheckActivity.this);
                        builder1.setIcon(android.R.drawable.ic_dialog_alert);
                        builder1.setTitle("Fail");
                        builder1.setMessage("Please fill information correctly.");
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
                    else {
                          AlertDialog.Builder builder1 = new AlertDialog.Builder(CheckActivity.this);
                          builder1.setIcon(android.R.drawable.ic_dialog_alert);
                          builder1.setTitle("Pass");
                          builder1.setMessage("Cylinder is PASS...!");
                          builder1.setCancelable(true);
                          builder1.setPositiveButton("OK",
                                  new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                        String orignal_tw = et_og_tw.getText().toString();
                                        String current_tw =et_cu_tw.getText().toString();
                                        String loss_of_weight =tv_tw.getText().toString();
                                        String wc = String.valueOf(water_capacity);

                                          Intent myIntent = new Intent(CheckActivity.this, ResultActivity.class);
                                            myIntent.putExtra("compnay_name",compnay_name);
                                            myIntent.putExtra("c_number",c_number);
                                            myIntent.putExtra("c_type",c_type);
                                            myIntent.putExtra("orignal_tw",orignal_tw);
                                            myIntent.putExtra("current_tw",current_tw);
                                            myIntent.putExtra("loss_of_weight",loss_of_weight);
                                            myIntent.putExtra("date_of_taste",date_of_taste);
                                            myIntent.putExtra("last_hydro_taste",last_hydro_taste);
                                            myIntent.putExtra("water_capacity",wc);
                                            myIntent.putExtra("next_hydro_test",next_hydro_test);
                                            myIntent.putExtra("isi_number",isi_number);
                                            myIntent.putExtra("party_name",party_name);
                                          startActivity(myIntent);
                                          finish();

                                            dentcheck = 0.0;
                                            og_tw = 0.0;
                                            cu_tw = 0.0;
                                            weight_with_waterr = 0.0;

                                            et_dentcheck.setText("");
                                            et_cu_tw.setText("");
                                            et_og_tw.setText("");
                                            tv_tw.setText("");
                                            weight_with_water.setText("");

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
                        }
                }
            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dentcheck = 0.0;
                og_tw = 0.0;
                cu_tw = 0.0;
                weight_with_waterr = 0.0;

                et_dentcheck.setText("");
                et_cu_tw.setText("");
                et_og_tw.setText("");
                tv_tw.setText("");
                weight_with_water.setText("");

                if (hummingsound.isChecked()){
                    hummingsound.toggle();
                }
                if (rustedcyl.isChecked()){
                    rustedcyl.toggle();
                }
            }
        });
    }
}
