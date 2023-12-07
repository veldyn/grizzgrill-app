package com.example.grizzgrill_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class menuView extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener{
    ListView lvSelection;
    ArrayAdapter myArrayAdapter;
    String[] SpinnerArr = {"Breakfast", "Lunch", "Dinner", "Dessert"};
    String[] Breakfast = {"Blueberry Coffee Cake Muffin $3.99",
            "Breakfast Wrap $6.79",
            "Plain Bagel $3.49",
            "Cereal $4.79",
            "Small Coffee $6.49",
            "Large Coffee $8.49",
            "Meduim Coffee $7.49",
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
    TextView header;
    int index = 0;
    Intent intent4DA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_view);

        header = findViewById(R.id.header);
        lvSelection = findViewById(R.id.ordersLV);
        mySpinner = findViewById(R.id.mySpinner);
        mySpinner.setOnItemSelectedListener(this);
        lvSelection.setOnItemClickListener(this);

        myArrayAdapter = new ArrayAdapter(this, R.layout.managespinnerlayout,SpinnerArr);
        mySpinner.setAdapter(myArrayAdapter);
        mySpinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Breakfast);


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Lunch);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Dinner);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Dessert);



        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if
                (position == 0)
                    lvSelection.setAdapter(adapter);
                if
                (position == 0)
                    header.setText("Breakfast Options:");
                if
                (position == 1)
                    lvSelection.setAdapter(adapter1);
                if
                (position == 1)
                    header.setText("Lunch Options:");

                if
                (position == 2)
                    lvSelection.setAdapter(adapter2);
                if
                (position == 2)
                    header.setText("Dinner Options:");

                if
                (position == 3)
                    lvSelection.setAdapter(adapter3);
                if
                (position == 3)
                    header.setText("Dessert Options:");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

