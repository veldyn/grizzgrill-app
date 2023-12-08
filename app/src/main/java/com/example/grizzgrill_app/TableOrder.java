package com.example.grizzgrill_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Spinner;

import java.io.IOException;
import java.util.ArrayList;

public class TableOrder extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener{
    public static SQLiteDatabase db;
    DBHelper myDBHelper;
    ListView lvSelection;
    ListView lvOrders;
    ArrayAdapter adapterT;
    ArrayAdapter myArrayAdapter;
    ArrayAdapter OrdersAdapter;
    String[] SpinnerArr = {"Breakfast", "Lunch", "Dinner", "Dessert"};
    String[] Breakfast = {"Blueberry Coffee Cake Muffin $3.99",
            "Breakfast Wrap $6.79",
            "Plain Bagel $3.49",
            "Cereal $4.79",
            "Small Coffee $6.49",
            "Large Coffee $8.49",
            "Medium Coffee $7.49",
            "Warm Apple Crostata $6.49"
    };
    String[] Lunch = {"Ultimate Grilled Cheese $6.99",
            "Chicken Pesto Wrap $8.79",
            "Tuna Melt $10.49",
            "Turkey Spinach Salad $6.79",
            "Cheese Burger$6.49",
            "Earlybird Burger$6.49"
    };
    String[] Dinner = {"3 Beef Soft Taco $13.99",
            "Chicken Fingers $8.79",
            "Mac n Cheese $9.49",
            "Smash Burger $10.79"
    };
    String[] Dessert = {"Black Tie Mousse Cake $6.99",
            "Dolcini $2.79",
            "Seasonal Sicilian Cheesecake $6.49",
            "S'mores Layer Cake $6.79",
            "Warm Apple Crostata $6.49"
    };
    Spinner mySpinner;
    Spinner spinnerT;
    TextView priceTxt;
    TextView EmployeeName;

    //Database Elements:
    ArrayList<Integer> TableNumbers;
    ArrayList<String> ENames;
    //Database queries:
    String TQuery = "SELECT RTABLE_ID FROM RTABLES";
    String grabEmployeeQuery = "SELECT EMPLOYEES.EMPLOYEE_NAME FROM EMPLOYEES JOIN RTABLES ON RTABLES.EMPLOYEE_ID = EMPLOYEES.EMPLOYEE_ID WHERE RTABLES.RTABLE_ID = ";

    float orderPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_order);

        spinnerT = findViewById(R.id.TableSp);

        // Creating an ArrayAdapter using the string array and a default spinner layout
        String[] numbers = {"1", "2", "3", "4", "5"};
        //adapterT = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, numbers);

        // Specify the layout to use when the list of choices appears
        //adapterT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        //spinnerT.setAdapter(adapterT);

        lvSelection = findViewById(R.id.ordersSelection);
        lvOrders = findViewById(R.id.ordersLV);
        mySpinner = findViewById(R.id.mySpinner);
        priceTxt = findViewById(R.id.price);
        EmployeeName = findViewById(R.id.Employee);
        mySpinner.setOnItemSelectedListener(this);
        lvSelection.setOnItemClickListener(this);
        lvOrders.setOnItemClickListener(this);

        myArrayAdapter = new ArrayAdapter(this, R.layout.managespinnerlayout,SpinnerArr);
        OrdersAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        lvOrders.setAdapter(OrdersAdapter);
        mySpinner.setAdapter(myArrayAdapter);
        mySpinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Breakfast);


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Lunch);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Dinner);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Dessert);

        createDB();
        getTResult(TQuery);


        spinnerT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                OrdersAdapter.clear();
                OrdersAdapter.notifyDataSetChanged();
                orderPrice = 0;
                priceTxt.setText(String.valueOf(orderPrice));
                
                getEResult(grabEmployeeQuery + (position+1) + ";");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                    lvSelection.setAdapter(adapter);

                if
                (position == 1)
                    lvSelection.setAdapter(adapter1);

                if
                (position == 2)
                    lvSelection.setAdapter(adapter2);

                if
                (position == 3)
                    lvSelection.setAdapter(adapter3);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Check if the clicked ListView is lvSelection or lvOrders
        if (parent.getId() == R.id.ordersSelection) {
            // Get the selected item from lvSelection
            String selectedItem = (String) parent.getItemAtPosition(position);
            String[] parts = selectedItem.split("\\$");
            if (parts.length > 1) {
                // parts[1] contains the price as a string
                String priceString = parts[1].trim();

                // Convert the price string to a float
                try {
                    orderPrice += Float.parseFloat(priceString);

                    // Set the float value to priceTxt
                    priceTxt.setText(String.valueOf(orderPrice));
                } catch (NumberFormatException e) {
                    // Handle the case where the conversion to float fails
                    e.printStackTrace();
                }
            }

            // Add the selected item to lvOrders using OrdersAdapter
            OrdersAdapter.add(selectedItem);
            OrdersAdapter.notifyDataSetChanged();
        } else if (parent.getId() == R.id.ordersLV) {
            // Get the selected item from lvOrders
            String selectedItem = (String) parent.getItemAtPosition(position);
            String[] parts = selectedItem.split("\\$");
            if (parts.length > 1) {
                // parts[1] contains the price as a string
                String priceString = parts[1].trim();

                // Convert the price string to a float
                try {
                    orderPrice -= Float.parseFloat(priceString);

                    // Set the float value to priceTxt
                    priceTxt.setText(String.valueOf(orderPrice));
                } catch (NumberFormatException e) {
                    // Handle the case where the conversion to float fails
                    e.printStackTrace();
                }
            }

            // Remove the selected item from lvOrders using OrdersAdapter
            OrdersAdapter.remove(selectedItem);
            OrdersAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @SuppressLint("Range")
    public void getEResult(String q) {
        Cursor result = db.rawQuery(q, null);
        if (result != null && result.moveToNext()) {
            result.moveToNext();
            EmployeeName.setText(result.getString(0));
        } else {
            EmployeeName.setText("No Employee Assigned");
        }
        result.close();
    }//end of getResult

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
        if (adapterT == null) {
            adapterT = new ArrayAdapter(this, R.layout.managespinnerlayout, TableNumbers);
            spinnerT.setAdapter(adapterT);
        } else {
            adapterT.clear();
            adapterT.addAll(TableNumbers);
            adapterT.notifyDataSetChanged();
            spinnerT.setAdapter(adapterT);
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