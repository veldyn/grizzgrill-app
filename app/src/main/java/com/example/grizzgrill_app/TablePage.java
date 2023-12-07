package com.example.grizzgrill_app;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class TablePage extends AppCompatActivity {

    public static SQLiteDatabase db;
    DBHelper myDBHelper;
    Spinner EmployeeSpinner;
    ArrayAdapter EmployeeAdapter;
    TextView TableOneBox;
    TextView TableTwoBox;
    TextView TableThreeBox;
    TextView TableFourBox;
    String[] TableIDs = {"1", "2", "3"};
    ScrollView CurrentTables;

    //Database ArrayLists:
    ArrayList<String> ENames;
    ArrayList<Integer> TableNumbers;

    //Database Queries:
    String EQuery = "SELECT EMPLOYEE_NAME FROM EMPLOYEES";
    String TQuery = "SELECT RTABLE_ID FROM RTABLES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_page);

        //Assign Spinner components to XML equivalents:
        EmployeeSpinner = findViewById(R.id.EmployeeSelect);
        
        //Assign ScrollView to XML equivalent:
        CurrentTables = findViewById(R.id.ListOfOrders);

        //Assign TableBoxes to XML equivalents.
        TableOneBox = findViewById(R.id.tableOneBox);
        TableTwoBox = findViewById(R.id.tableTwoBox);
        TableThreeBox = findViewById(R.id.tableThreeBox);
        TableFourBox = findViewById(R.id.tableFourBox);



        createDB();
        getEResult(EQuery);
    }

    public void getEResult(String q) {
        Cursor result = db.rawQuery(q, null);
        result.moveToFirst();
        int count = result.getCount();
        Log.i("count=", String.valueOf(count));
        //arrays for name, ingredients and preparation for each recipe
        ENames = new ArrayList<String>();
        //just to give number for each recipe
        int i = 1;
        if (count >= 1) {
            do {
                //Adjust column index for appropriate column (will be 0 most times)
                ENames.add(result.getString(0));
                i++;
            } while (result.moveToNext());
        }
        if (EmployeeAdapter == null) {
            EmployeeAdapter = new ArrayAdapter(this, R.layout.managespinnerlayout, ENames);
            EmployeeSpinner.setAdapter(EmployeeAdapter);
        } else {
            EmployeeAdapter.clear();
            EmployeeAdapter.addAll(ENames);
            EmployeeAdapter.notifyDataSetChanged();
            EmployeeSpinner.setAdapter(EmployeeAdapter);
        }
    }//end of getResult

    public void createDB() {
        myDBHelper = new DBHelper(this);
        try {
            myDBHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");
        }

        try {
            myDBHelper.openDataBase();
        } catch (SQLException sqle) {
        }
        db = myDBHelper.getWritableDatabase();
    }
}