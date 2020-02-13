package com.yashpawar.HST.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.yashpawar.HST.R;
import com.yashpawar.HST.helper.ListAdapter;
import com.yashpawar.HST.helper.SqliteHelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DisplayDataActivity extends AppCompatActivity {
    SqliteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    ListAdapter listAdapter ;
    ListView LISTVIEW;

    ArrayList<String> ID_Array;
    ArrayList<String> CYLINDER_NUMBER_Array;
    ArrayList<String> CYLINDER_TYPE_Array;
    /*ArrayList<String> DATE_OF_TASTE_Array;
    ArrayList<String> LAST_HYDRO_TASTE_Array;
    ArrayList<String> REMARK_Arry;*/

    String SQLiteDataBaseQueryHolder;

    ArrayList<String> ListViewClickItemArray = new ArrayList<String>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.print_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.printdata:
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + SqliteHelper.TABLE_HST + " ", null);
            if (cursor != null && cursor.getCount() != 0) {
                try {
                    createPdf();
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(DisplayDataActivity.this);
                builder1.setIcon(android.R.drawable.ic_dialog_alert);
                builder1.setTitle("Data not available.");
                builder1.setMessage("Data is not available. Please add some data then try to generate PDF.");
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

            return true;

        case R.id.deletedata:
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + SqliteHelper.TABLE_HST + " ", null);

            if (cursor != null && cursor.getCount() != 0) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(DisplayDataActivity.this);
                builder1.setIcon(android.R.drawable.ic_dialog_alert);
                builder1.setTitle("Delete All Data");
                builder1.setMessage("Are your sure want to delete all Data..?");
                builder1.setCancelable(true);
                builder1.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SQLiteDataBaseQueryHolder = "DROP TABLE " + SqliteHelper.TABLE_HST;
                                sqLiteDatabase.execSQL(SQLiteDataBaseQueryHolder);
                                sqLiteDatabase.close();
                                finish();
                                Toast.makeText(getApplicationContext(), "Data deleted Successfully.", Toast.LENGTH_LONG).show();
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
                return true;
            }else {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(DisplayDataActivity.this);
                builder1.setIcon(android.R.drawable.ic_dialog_alert);
                //builder1.setTitle("Data not available.");
                builder1.setMessage("Data is already cleared.");
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
        return(super.onOptionsItemSelected(item));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        SQLiteDataBaseBuild();
        SQLiteTableBuild();

        //createPdf();


        LISTVIEW =  findViewById(R.id.listView1);
        ID_Array = new ArrayList<>();
        CYLINDER_NUMBER_Array = new ArrayList<>();
        CYLINDER_TYPE_Array = new ArrayList<>();
        /*DATE_OF_TASTE_Array = new ArrayList<>();
        LAST_HYDRO_TASTE_Array = new ArrayList<>();
        REMARK_Arry = new ArrayList<>();*/

        sqLiteHelper = new SqliteHelper(this);

        LISTVIEW.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ShowSingleDataActivity.class);
                intent.putExtra("ListViewClickedItemValue", ListViewClickItemArray.get(position).toString());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        ShowSQLiteDBdata() ;
        super.onResume();
    }

    private void ShowSQLiteDBdata() {
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        ID_Array.clear();
        CYLINDER_NUMBER_Array.clear();
        CYLINDER_TYPE_Array.clear();
        /*DATE_OF_TASTE_Array.clear();
        LAST_HYDRO_TASTE_Array.clear();
        REMARK_Arry.clear();*/

        cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+SqliteHelper.TABLE_HST +" ", null);
        if(cursor!=null && cursor.getCount()!=0) {
            if (cursor.moveToFirst()) {
                do {
                    ID_Array.add(cursor.getString(cursor.getColumnIndex(SqliteHelper.ID)));
                    //Inserting Column ID into Array to Use at ListView Click Listener Method.
                    ListViewClickItemArray.add(cursor.getString(cursor.getColumnIndex(SqliteHelper.ID)));
                    CYLINDER_NUMBER_Array.add(cursor.getString(cursor.getColumnIndex(SqliteHelper.CYLINDER_NUMBER)));
                    CYLINDER_TYPE_Array.add(cursor.getString(cursor.getColumnIndex(SqliteHelper.CYLINDER_TYPE)));
                /*DATE_OF_TASTE_Array.add(cursor.getString(cursor.getColumnIndex(SqliteHelper.DATE_OF_TASTE)));
                LAST_HYDRO_TASTE_Array.add(cursor.getString(cursor.getColumnIndex(SqliteHelper.LAST_HYDRO_TASTE)));
                REMARK_Arry.add(cursor.getString(cursor.getColumnIndex(SqliteHelper.REMARK)));*/
                } while (cursor.moveToNext());
            }
        }
        else {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(DisplayDataActivity.this);
            builder1.setIcon(android.R.drawable.ic_dialog_alert);
            //builder1.setTitle("Data not available.");
            builder1.setMessage("Data is not available. please add some data.");
            builder1.setCancelable(true);
            builder1.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
        }

        listAdapter = new ListAdapter(DisplayDataActivity.this,
                ID_Array,
                CYLINDER_NUMBER_Array,
                CYLINDER_TYPE_Array
                /*DATE_OF_TASTE_Array,
                LAST_HYDRO_TASTE_Array,
                REMARK_Arry*/
        );
        listAdapter.notifyDataSetChanged();
        LISTVIEW.setAdapter(listAdapter);
        cursor.deactivate();
        cursor.close();
        cursor = null;
    }

    public void SQLiteDataBaseBuild(){
        sqLiteDatabase = openOrCreateDatabase(SqliteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);
    }

    public void SQLiteTableBuild(){

        sqLiteDatabase.execSQL(" CREATE TABLE IF NOT EXISTS " + SqliteHelper.TABLE_HST +
                " ( "
                +SqliteHelper.ID +" INTEGER PRIMARY KEY, "
                +SqliteHelper.COMPANY_NAME + " VARCHAR, "
                +SqliteHelper.PARTY_NAME + " VARCHAR, "
                +SqliteHelper.CYLINDER_NUMBER + " VARCHAR, "
                +SqliteHelper.CYLINDER_TYPE+ " VARCHAR, "
                +SqliteHelper.DATE_OF_TASTE+ " VARCHAR, "
                +SqliteHelper.LAST_HYDRO_TASTE+ " VARCHAR, "
                +SqliteHelper.ORIGINAL_TW + " VARCHAR, "
                +SqliteHelper.CURRENT_TW+ " VARCHAR, "
                +SqliteHelper.LOSS_OF_WEIGHT + " VARCHAR,"
                +SqliteHelper.WATER_CAPICITY + " VARCHAR,"
                +SqliteHelper.PRESSURE+ " VARCHAR, "
                +SqliteHelper.C1+ " VARCHAR, "
                +SqliteHelper.C2+ " VARCHAR, "
                +SqliteHelper.C3+ " VARCHAR, "
                +SqliteHelper.TE+ " VARCHAR, "
                +SqliteHelper.PE+ " VARCHAR, "
                +SqliteHelper.REMARK + " VARCHAR,"
                +SqliteHelper.NEXT_HYDRO_TEST + " VARCHAR )"
                +"");
    }

    public void createPdf() throws DocumentException, FileNotFoundException {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            String dir = "/storage/emulated/0/HST/";

            File folder = new File(dir);
            if (!folder.exists()) {
                folder.mkdirs(); }
            Date date = new Date() ;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy HH-mm-ss") ;
            File file =  new File(dir, "HST"+dateFormat.format(date)+".pdf");
            Cursor c1 = sqLiteDatabase.rawQuery("SELECT * FROM "+SqliteHelper.TABLE_HST +" ", null);

            Document document = new Document(PageSize.A4.rotate(), 5f, 5f, 5f, 1f);  // create the document
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            Paragraph p1 = new Paragraph();
            p1.add("Hydro Static Testing");
            p1.setAlignment(Element.ALIGN_CENTER);
            document.add(p1);

            float[] colWidths = {(float) 1.3,4,3,2,4, (float) 3.5,3,3,3,4,4,4,4,4,2, (float) 2.5,2,3,4};
            PdfPTable table = new PdfPTable(colWidths);

            table.setWidthPercentage(100);
            table.spacingBefore();
            table.setSpacingBefore(30f);
            //table.setSpacingAfter(60f);
            document.add(table);

            table.addCell("id");
            table.addCell("Company Name");
            table.addCell("Party Name");
            table.addCell("ISI");
            table.addCell("Cylinder Number");
            table.addCell("Cylinder Gas Type");
            table.addCell("Date of test");
            table.addCell("Last Hydro Test");
            table.addCell("Next Hydro Test");
            table.addCell("Original Tare Weight");
            table.addCell("Current Tare Weight");
            table.addCell("Loss of Weight");
            table.addCell("Water Capacity");
            table.addCell("Pressure");
            table.addCell("C1");
            table.addCell("C2");
            table.addCell("C3");
            //table.addCell("TE");
            table.addCell("PE");
            table.addCell("remark");


            while (c1.moveToNext()) {
                String id = c1.getString(c1.getColumnIndex("id"));
                String company_name = c1.getString(c1.getColumnIndex("company_name"));
                String party_name = c1.getString(c1.getColumnIndex("party_name"));
                String isi = c1.getString(c1.getColumnIndex("ISI"));
                String cyilinder_number = c1.getString(c1.getColumnIndex("cyilinder_number"));
                String cylinder_type = c1.getString(c1.getColumnIndex("cylinder_type"));
                String date_of_taste = c1.getString(c1.getColumnIndex("date_of_taste"));
                String last_hydro_taste = c1.getString(c1.getColumnIndex("last_hydro_taste"));
                String orignal_tw = c1.getString(c1.getColumnIndex("orignal_tw"));
                String current_tw = c1.getString(c1.getColumnIndex("current_tw"));
                String loss_of_weight = c1.getString(c1.getColumnIndex("loss_of_weight"));
                String water_capacity = c1.getString(c1.getColumnIndex("water_capacity"));
                String Pressure = c1.getString(c1.getColumnIndex("Pressure"));
                String C1 = c1.getString(c1.getColumnIndex("C1"));
                String C2 = c1.getString(c1.getColumnIndex("C2"));
                String C3 = c1.getString(c1.getColumnIndex("C3"));
                //String TE = c1.getString(c1.getColumnIndex("TE"));
                String PE = c1.getString(c1.getColumnIndex("PE"));
                String REMARK = c1.getString(c1.getColumnIndex("REMARK"));
                String NEXT_HYDRO_TEST = c1.getString(c1.getColumnIndex("NEXT_HYDRO_TEST"));

                table.addCell(id);
                table.addCell(company_name);
                table.addCell(party_name);
                table.addCell(isi);
                table.addCell(cyilinder_number);
                table.addCell(cylinder_type);
                table.addCell(date_of_taste);
                table.addCell(last_hydro_taste);
                table.addCell(NEXT_HYDRO_TEST);
                table.addCell(orignal_tw);
                table.addCell(current_tw);
                table.addCell(loss_of_weight);
                table.addCell(water_capacity);
                table.addCell(Pressure);
                table.addCell(C1);
                table.addCell(C2);
                table.addCell(C3);
                //table.addCell(TE);
                table.addCell(PE);
                table.addCell(REMARK);

            }
            document.add(table);
            document.addCreationDate();
            document.close();
            Toast.makeText(getApplicationContext(), "PDF created Successfully...!", Toast.LENGTH_LONG).show();

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }
}