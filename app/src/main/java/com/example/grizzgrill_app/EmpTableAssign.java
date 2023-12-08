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

    //String[] EmployeeNames = {"Steve", "George", "Jenna"};

    ArrayList<String> ENames;
    ArrayList<Integer> TableNumbers;

    //Queries for Database calls later:
    String EQuery = "SELECT EMPLOYEE_NAME FROM EMPLOYEES";
    String TQuery = "SELECT RTABLE_ID FROM RTABLES";
    String AddEmployee = "INSERT INTO EMPLOYEES (EMPLOYEE_ID, EMPLOYEE_NAME) VALUES (NULL, ";
    String RemoveEmployee = "DELETE FROM EMPLOYEES WHERE EMPLOYEE_NAME = ";
    String AddTable = "INSERT INTO RTABLES (RTABLE_ID, RTABLE_SEATS, RTABLE_STATUS) VALUES (NULL, ";
    String RemoveTable = "DELETE FROM RTABLES WHERE RTABLE_ID = ";
    String AssignEmployee = "UPDATE RTABLES SET EMPLOYEE_ID = ";
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

        //Assign HeaderThree components to XML equivalents:
        HeaderThree = findViewById(R.id.headerThree);
        HeaderThreeButton = findViewById(R.id.headerThreeButton);
        HeaderThreeSpinner = findViewById(R.id.headerThreeSpinner);

        //Database stuff goes at the end of onCreate
        createDB();
        getEResult(EQuery);
        getTResult(TQuery);

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

        //Adding Employee or Table:
        HeaderOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Determine if to add Employee or Table:
                if (ManageSelectSpinner.getSelectedItemPosition() == 0) {
                    String EmployeeName = AddEmployee + "'" + HeaderOneEditText.getText().toString() + "');";
                    addEmployee(EmployeeName);

                }
                if (ManageSelectSpinner.getSelectedItemPosition() == 1) {
                    String TableDef = AddTable + "4, 0);";
                    addTable(TableDef);
                }

            }
        });

        //Assigning Employees or Removing Table:
        HeaderTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Determine if to remove Employee or Table:
                if (ManageSelectSpinner.getSelectedItemPosition() == 0) {
                    //If SpinnerPosition is on Employee:
                    //Assign Employee to Table

                    //Get EmployeeID:
                    String EMP_Q = " WHERE RTABLE_ID =";
                    String estring = "";
                    String cstring = ";";
                    String assignString = AssignEmployee + (HeaderTwoSpinner2.getSelectedItemPosition()+1) + estring + EMP_Q + HeaderTwoSpinner1.getSelectedItem().toString() + cstring;
                    db.execSQL(assignString);
                    //assignEmployee(EmployeeName, );
                }
                if (ManageSelectSpinner.getSelectedItemPosition() == 1) {
                    //If SpinnerPosition is on Table:
                    //Remove Table where ID = SpinnerPosition
                    String TableNo = RemoveTable + (HeaderTwoSpinner1.getSelectedItem().toString()) + ";";
                    removeTable(TableNo);

                }

            }
        });

        //Removing Employee:
        HeaderThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Determine if to remove Employee or Table:
                if (ManageSelectSpinner.getSelectedItemPosition() == 0) {
                    //If SpinnerPosition is on Employee:
                    //Remove Employee where ID = SpinnerPosition
                    String EmployeeName = RemoveEmployee + "'" + (HeaderThreeSpinner.getSelectedItem().toString()) + "';";
                    removeEmployee(EmployeeName);
                }
                if (ManageSelectSpinner.getSelectedItemPosition() == 1) {
                    //If SpinnerPosition is on Table:
                    //Remove Table where ID = SpinnerPosition
                    String TableNo = RemoveTable + "'" + (HeaderThreeSpinner.getSelectedItem().toString()) + "');";
                    removeTable(TableNo);

                }

            }
        });
    }
    //GetResult method for Employee Names, set Spinner adapter.
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
        if (HeaderTwoSpinner2Adapter == null) {
            HeaderTwoSpinner2Adapter = new ArrayAdapter(this, R.layout.managespinnerlayout, ENames);
            HeaderTwoSpinner2.setAdapter(HeaderTwoSpinner2Adapter);
        } else {
            HeaderTwoSpinner2Adapter.clear();
            HeaderTwoSpinner2Adapter.addAll(ENames);
            HeaderTwoSpinner2Adapter.notifyDataSetChanged();
            HeaderTwoSpinner2.setAdapter(HeaderTwoSpinner2Adapter);
        }
        if (HeaderThreeSpinnerAdapter == null) {
            HeaderThreeSpinnerAdapter = new ArrayAdapter(this, R.layout.managespinnerlayout, ENames);
            HeaderThreeSpinner.setAdapter(HeaderThreeSpinnerAdapter);
        } else {
            HeaderThreeSpinnerAdapter.clear();
            HeaderThreeSpinnerAdapter.addAll(ENames);
            HeaderThreeSpinnerAdapter.notifyDataSetChanged();
            HeaderThreeSpinner.setAdapter(HeaderThreeSpinnerAdapter);
        }

    }//end of getResult

    //getResult method for TableIDs, set spinner adapter.
    public void getTResult(String q) {
        Cursor result = db.rawQuery(q, null);
        result.moveToFirst();
        int count = result.getCount();
        Log.i("count=", String.valueOf(count));
        //arrays for name, ingredients and preparation for each recipe
        TableNumbers = new ArrayList<Integer>();
        //just to give number for each recipe
        int i = 1;
        if (count >= 1) {
            do {
                //Adjust column index for appropriate column (will be 0 most times)
                TableNumbers.add(result.getInt(0));
                i++;
            } while (result.moveToNext());
        }
        if (HeaderTwoSpinner1Adapter == null) {
            HeaderTwoSpinner1Adapter = new ArrayAdapter(this, R.layout.managespinnerlayout, TableNumbers);
            HeaderTwoSpinner1.setAdapter(HeaderTwoSpinner1Adapter);
        } else {
            HeaderTwoSpinner1Adapter.clear();
            HeaderTwoSpinner1Adapter.addAll(TableNumbers);
            HeaderTwoSpinner1Adapter.notifyDataSetChanged();
            HeaderTwoSpinner1.setAdapter(HeaderTwoSpinner1Adapter);
        }
    }//end of getResult

    public void addEmployee(String q) {
        //First execute query:
        db.execSQL(q);
        //Now update EmployeeNames:
        getEResult(EQuery);
    }
    public void addTable(String q) {
        //First execute query:
        db.execSQL(q);
        //Now update EmployeeNames:
        getTResult(TQuery);
    }

    public void removeEmployee(String q) {
        //First execute query:
        db.execSQL(q);
        //Now update EmployeeNames:
        getEResult(EQuery);
    }
    public void removeTable(String q) {
        //First execute query:
        db.execSQL(q);
        //Now update EmployeeNames:
        getTResult(TQuery);
    }

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