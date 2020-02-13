package com.yashpawar.HST.Activity;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.yashpawar.HST.R;

public class NewActivity extends AppCompatActivity {
    double va_r = 0, va_l = 0, vb_r = 0, vb_l = 0, vc_r = 0, vc_l = 0, vd_r = 0, vd_l = 0;

    TextView va_et_r, va_et_l, vb_et_r, vb_et_l, vc_et_r, vc_et_l, vd_et_r, vd_et_l, result;
    Button buttonEqual, buttonDel;

    int f = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        setTitle("Find Value of w2");

        buttonEqual = findViewById(R.id.buttoneql);
        buttonDel = findViewById(R.id.buttonDel);

        result = findViewById(R.id.result);
        va_et_r = findViewById(R.id.va_r);
        va_et_l = findViewById(R.id.va_l);
        vb_et_r = findViewById(R.id.vb_r);
        vb_et_l = findViewById(R.id.vb_l);
        vc_et_r = findViewById(R.id.vc_r);
        vc_et_l = findViewById(R.id.vc_l);
        vd_et_r = findViewById(R.id.vd_r);
        vd_et_l = findViewById(R.id.vd_l);

        buttonEqual.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (va_et_r.getText().toString().trim().length() > 0) {
                    va_r = Float.parseFloat(va_et_r.getText().toString());
                } else {
                    va_r = f;
                }
                if (va_et_l.getText().toString().trim().length() > 0) {
                    va_l = Float.parseFloat(va_et_l.getText().toString());
                } else {
                    va_l = f;
                }
                if (vb_et_r.getText().toString().trim().length() > 0) {
                    vb_r = Float.parseFloat(vb_et_r.getText().toString());
                } else {
                    vb_r = f;
                }
                if (vb_et_l.getText().toString().trim().length() > 0) {
                    vb_l = Float.parseFloat(vb_et_l.getText().toString());
                } else {
                    vb_l = f;
                }
                if (vc_et_r.getText().toString().trim().length() > 0) {
                    vc_r = Float.parseFloat(vc_et_r.getText().toString());
                } else {
                    vc_r = f;
                }
                if (vc_et_l.getText().toString().trim().length() > 0) {
                    vc_l = Float.parseFloat(vc_et_l.getText().toString());
                } else {
                    vc_l = f;
                }
                if (vd_et_r.getText().toString().trim().length() > 0) {
                    vd_r = Float.parseFloat(vd_et_r.getText().toString());
                } else {
                    vd_r = f;
                }
                if (vd_et_l.getText().toString().trim().length() > 0) {
                    vd_l = Float.parseFloat(vd_et_l.getText().toString());
                } else {
                    vd_l = f;
                }

                double va = 3.14 * va_r * va_l;
                double vb = 3.14 * vb_r * vb_l;
                double vc = 3.14 * vc_r * vc_l;
                double vd = 3.14 * vd_r * vd_l;
                double w2 = va + vb + vc + vd;

                double res = w2 / 1000;

                double finalresult = (int) (Math.round(res * 10000)) / 10000.0;

                result.setText(finalresult + "");

                    /*String text = result.getText().toString();

                    Intent myIntent = new Intent(v.getContext(), ResultActivity.class);
                    myIntent.putExtra("mytext",text);
                    startActivity(myIntent);
                    //finish();*/
            }
        });

        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                va_r = 0.0;
                va_l = 0.0;
                vb_r = 0.0;
                vb_l = 0.0;
                vc_r = 0.0;
                vc_l = 0.0;
                vd_r = 0.0;
                vd_l = 0.0;

                result.setText("");
                va_et_r.setText("");
                va_et_l.setText("");
                vb_et_r.setText("");
                vb_et_l.setText("");
                vc_et_r.setText("");
                vc_et_l.setText("");
                vd_et_r.setText("");
                vd_et_l.setText("");
            }
        });

        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(result.getText().toString());
                Toast.makeText(getApplicationContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
