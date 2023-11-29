package com.example.grizzgrill_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.grizzgrill_app.R;

public class MainActivity extends AppCompatActivity {
    //Buttons for Customer and Management View:
    Button customerBtn;
    Button managementBtn;

    Intent customerActivity;
    Intent managementActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customerBtn = findViewById(R.id.customerBtn);
        managementBtn = findViewById(R.id.managementBtn);

        //Create Activities to link with intent
        customerActivity = new Intent(this, TablePage.class);
        managementActivity = new Intent(this, ManagementMenu.class);

        customerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Change to Customer Portion of App:
                MainActivity.this.startActivity(customerActivity);
            }
        });

        managementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            MainActivity.this.startActivity(managementActivity);

            }
        });
    }
}