package com.example.grizzgrill_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManagementMenu extends AppCompatActivity {


    Intent ManagementSettings;
    Intent TablePage;
    Intent TableOrder;
    Button TablePageBtn;
    Button TableOrderBtn;
    Button ManagementSettingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_menu);

        //Create intents to change pages
        ManagementSettings = new Intent(this, EmpTableAssign.class);
        TablePage = new Intent(this, TablePage.class);
        TableOrder = new Intent(this, TableOrder.class);

        //Assign buttons to XML equivalents:
        TablePageBtn = findViewById(R.id.tablePageBtn);
        TableOrderBtn = findViewById(R.id.tableOrderBtn);
        ManagementSettingsBtn = findViewById(R.id.emp_table_assignBtn);

        //OnClickListeners for Buttons, starting activities.
        TablePageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Change to Customer Portion of App:
                ManagementMenu.this.startActivity(TablePage);
            }
        });

        TableOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Change to Customer Portion of App:
                ManagementMenu.this.startActivity(TableOrder);
            }
        });
        ManagementSettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Change to Customer Portion of App:
                ManagementMenu.this.startActivity(ManagementSettings);
            }
        });
    }
}