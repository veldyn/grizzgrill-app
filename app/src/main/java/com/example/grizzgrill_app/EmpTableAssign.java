package com.example.grizzgrill_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.grizzgrill_app.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class EmpTableAssign extends AppCompatActivity {

    //Database stuff:
    public static SQLiteDatabase db;
    DBHelper myDBHelper;

    //Spinner components (Manage Employees / Manage Tables):
    Spinner ManageSelectSpinner;
    ArrayAdapter ManageSelectAdapter;
    String[] ManageSpinnerOptions = {"Manage Employees", "Manage Tables"};

    //HeaderOne components (Add Employee & Add Table):
    TextView HeaderOne;
    Button HeaderOneButton;
    EditText HeaderOneEditText;

    //HeaderTwo components (Assigning Employees & Remove Tables):
    TextView HeaderTwo;
    Button HeaderTwoButton;
    Spinner HeaderTwoSpinner1;
    Spinner HeaderTwoSpinner2;
    ArrayAdapter HeaderTwoSpinner1Adapter;
    ArrayAdapter HeaderTwoSpinner2Adapter;

    //HeaderThree components (Removing Employees):
    TextView HeaderThree;
    Button HeaderThreeButton;
    Spinner HeaderThreeSpinner;
    ArrayAdapter HeaderThreeSpinnerAdapter;

    //Generic Components for Functionality:

    String[] EmployeeNames = {"Steve", "George", "Jenna"};

    ArrayList<String> ENames;
    ArrayList<Integer> TableNumbers;
    String[] TableIDs = {"1", "2", "3"};
    String Query = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_table_assign);

        //Assign Spinner components to XML equivalents:
        ManageSelectSpinner = findViewById(R.id.manageSelectSpinner);
        ManageSelectAdapter = new ArrayAdapter(this, R.layout.managespinnerlayout, ManageSpinnerOptions);
        ManageSelectSpinner.setAdapter(ManageSelectAdapter);

        //Assign HeaderOne components to XML equivalents:
        HeaderOne = findViewById(R.id.headerOne);
        HeaderOneButton = findViewById(R.id.headerOneButton);
        HeaderOneEditText = findViewById(R.id.headerOneEditText);

        //Assign HeaderTwo components to XML equivalents:
        HeaderTwo = findViewById(R.id.headerTwo);
        HeaderTwoButton = findViewById(R.id.headerTwoButton);
        HeaderTwoSpinner1 = findViewById(R.id.headerTwoSpinner1);
        HeaderTwoSpinner2 = findViewById(R.id.headerTwoSpinner2);
        HeaderTwoSpinner1Adapter = new ArrayAdapter(this, R.layout.managespinnerlayout, TableIDs);
        //HeaderTwoSpinner2Adapter = new ArrayAdapter<String>(this, R.layout.managespinnerlayout, ENames);
        HeaderTwoSpinner1.setAdapter(HeaderTwoSpinner1Adapter);
        //HeaderTwoSpinner2.setAdapter(HeaderTwoSpinner2Adapter);

        //Assign HeaderThree components to XML equivalents:
        HeaderThree = findViewById(R.id.headerThree);
        HeaderThreeButton = findViewById(R.id.headerThreeButton);
        HeaderThreeSpinner = findViewById(R.id.headerThreeSpinner);
        HeaderThreeSpinnerAdapter = new ArrayAdapter(this, R.layout.managespinnerlayout, EmployeeNames);
        HeaderThreeSpinner.setAdapter(HeaderThreeSpinnerAdapter);

        createDB();
        Query = "select * from customer";
        getResult(Query);

        ManageSelectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    //Enable Employee Management options:
                    //HeaderOne employee options:
                    HeaderOne.setText("Add an Employee");
                    HeaderOneEditText.setVisibility(View.VISIBLE);

                    //HeaderTwo employee options:
                    HeaderTwo.setText("Assign an Employee");
                    HeaderTwoSpinner1.setVisibility(View.VISIBLE);
                    HeaderTwoSpinner2.setVisibility(View.VISIBLE);
                    HeaderTwoButton.setText("Assign");

                    //HeaderThree employee options:
                    HeaderThree.setText("Remove an Employee");
                    HeaderThree.setVisibility(View.VISIBLE);
                    HeaderThreeSpinner.setVisibility(View.VISIBLE);
                    HeaderThreeButton.setVisibility(View.VISIBLE);
                }
                if (position == 1) {
                    //Enable Table Management options
                    //HeaderOne table options:
                    HeaderOne.setText("Add a Table");
                    HeaderOneEditText.setVisibility(View.INVISIBLE);

                    //HeaderTwo table options:
                    HeaderTwo.setText("Remove a Table");
                    HeaderTwoButton.setText("Remove");
                    HeaderTwoSpinner1.setVisibility(View.VISIBLE);
                    HeaderTwoSpinner2.setVisibility(View.INVISIBLE);

                    //HeaderThree table options:
                    HeaderThree.setVisibility(View.INVISIBLE);
                    HeaderThreeButton.setVisibility(View.INVISIBLE);
                    HeaderThreeButton.setVisibility(View.INVISIBLE);
                    HeaderThreeSpinner.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }
    public void getResult(String q) {
        Cursor result = db.rawQuery(q, null);
        result.moveToFirst();
        int count = result.getCount();
        Log.i("count=", String.valueOf(count));
        //arrays for name, ingredients and preparation for each recipe
        ENames = new ArrayList<String>();
        String[] testArr;
        //just to give number for each recipe
        int i = 1;
        if (count >= 1) {
            do {
                ENames.add(result.getString(1));
                i++;
            } while (result.moveToNext());
        }
        String[] ENamesArray = ENames.toArray(new String[ENames.size()]);
        HeaderTwoSpinner2Adapter = new ArrayAdapter(this, R.layout.managespinnerlayout, ENamesArray);
        HeaderTwoSpinner2.setAdapter(HeaderTwoSpinner2Adapter);
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