package com.yashpawar.HST.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.yashpawar.HST.R;
import com.yashpawar.HST.helper.SqliteHelper;

public class ShowSingleDataActivity extends AppCompatActivity  {
    String IDholder;
    TextView id,company_name, cylinder_number, cylinder_type, date_of_test, last_hydro_test, og_tw, cu_tw, pressure, c1, c2, c3, te, pe, loss_of_weight, remark, weight_with_water,next_hydro_test, isi_number, party_name;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    Button Delete, Edit;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder ;
    SqliteHelper SqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_data);

        //id =  findViewById(R.id.ID);
        company_name =  findViewById(R.id.company_name);
        isi_number =  findViewById(R.id.isi_number);
        cylinder_number =  findViewById(R.id.cylinder_number);
        cylinder_type =  findViewById(R.id.cylinder_type);
        date_of_test =  findViewById(R.id.date_of_test);
        last_hydro_test =  findViewById(R.id.last_hydro_test);
        og_tw =  findViewById(R.id.og_tw);
        cu_tw =  findViewById(R.id.cu_tw);
        next_hydro_test =  findViewById(R.id.next_hydro_test);
        weight_with_water =  findViewById(R.id.weight_with_water);
        pressure =  findViewById(R.id.pressure);
        c1 =  findViewById(R.id.c1);
        c2 =  findViewById(R.id.c2);
        c3 =  findViewById(R.id.c3);
        te =  findViewById(R.id.te);
        pe =  findViewById(R.id.pe);
        loss_of_weight =  findViewById(R.id.loss_of_weight);
        party_name =  findViewById(R.id.party_name);

        remark =  findViewById(R.id.remark);
        Delete = findViewById(R.id.buttonDelete);
        SqliteHelper = new SqliteHelper(this);

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenSQLiteDataBase();
                SQLiteDataBaseQueryHolder = "DELETE FROM "+ SqliteHelper.TABLE_HST +" WHERE id = "+IDholder+"";
                sqLiteDatabase.execSQL(SQLiteDataBaseQueryHolder);
                sqLiteDatabase.close();
                /*Intent intent = new Intent(getApplicationContext(),DisplayDataActivity.class);
                startActivity(intent);*/
                finish();
            }
        });
        }

    @Override
    protected void onResume() {
        ShowSingleRecordInTextView();
        super.onResume();
    }

    public void ShowSingleRecordInTextView() {
        sqLiteDatabase = SqliteHelper.getWritableDatabase();
        IDholder = getIntent().getStringExtra("ListViewClickedItemValue");
        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + SqliteHelper.TABLE_HST
                + " WHERE id = " + IDholder + "", null);
        if (cursor.moveToFirst()) {
            do {
                //id.setText(cursor.getString(cursor.getColumnIndex(SqliteHelper.ID)));
                company_name.setText(cursor.getString(cursor.getColumnIndex(SqliteHelper.COMPANY_NAME)));
                party_name.setText(cursor.getString(cursor.getColumnIndex(SqliteHelper.PARTY_NAME)));
                isi_number.setText(cursor.getString(cursor.getColumnIndex(SqliteHelper.ISI)));
                cylinder_number.setText(cursor.getString(cursor.getColumnIndex(SqliteHelper.CYLINDER_NUMBER)));
                cylinder_type.setText(cursor.getString(cursor.getColumnIndex(SqliteHelper.CYLINDER_TYPE)));
                date_of_test.setText(cursor.getString(cursor.getColumnIndex(SqliteHelper.DATE_OF_TASTE)));
                last_hydro_test.setText(cursor.getString(cursor.getColumnIndex(SqliteHelper.LAST_HYDRO_TASTE)));
                og_tw.setText(cursor.getString(cursor.getColumnIndex(SqliteHelper.ORIGINAL_TW)));
                cu_tw.setText(cursor.getString(cursor.getColumnIndex(SqliteHelper.CURRENT_TW)));
                loss_of_weight.setText(cursor.getString(cursor.getColumnIndex(SqliteHelper.LOSS_OF_WEIGHT)));
                weight_with_water.setText(cursor.getString(cursor.getColumnIndex(SqliteHelper.WATER_CAPICITY)));
                pressure.setText(cursor.getString(cursor.getColumnIndex(SqliteHelper.PRESSURE)));
                c1.setText(cursor.getString(cursor.getColumnIndex(SqliteHelper.C1)));
                c2.setText(cursor.getString(cursor.getColumnIndex(SqliteHelper.C2)));
                c3.setText(cursor.getString(cursor.getColumnIndex(SqliteHelper.C3)));
                te.setText(cursor.getString(cursor.getColumnIndex(SqliteHelper.TE)));
                pe.setText(cursor.getString(cursor.getColumnIndex(SqliteHelper.PE)));
                remark.setText(cursor.getString(cursor.getColumnIndex(SqliteHelper.REMARK)));
                next_hydro_test.setText(cursor.getString(cursor.getColumnIndex(SqliteHelper.NEXT_HYDRO_TEST)));
            }
            while (cursor.moveToNext());
            cursor.close();
        }
    }

    public void OpenSQLiteDataBase(){
        sqLiteDatabaseObj = openOrCreateDatabase(SqliteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);
    }
}
