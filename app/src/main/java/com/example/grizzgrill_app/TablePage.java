package com.example.grizzgrill_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.Spinner;

public class TablePage extends AppCompatActivity {

    Spinner EmployeeSpinner;
    ArrayAdapter EmployeeAdapter;
    String[] EmployeeNames = {"Steve", "George", "Jenna"};
    String[] TableIDs = {"1", "2", "3"};
    ScrollView CurrentTables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_page);

        //Assign Spinner components to XML equivalents:
        EmployeeSpinner = findViewById(R.id.EmployeeSelect);
        EmployeeAdapter = new ArrayAdapter(this, R.layout.managespinnerlayout, EmployeeNames);
        EmployeeSpinner.setAdapter(EmployeeAdapter);
        
        //Assign ScrollView to XML equivalent:
        CurrentTables = findViewById(R.id.ListOfOrders);
    }
}