package com.example.grizzgrill_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CustomerMenu extends AppCompatActivity {
    Button menuPageBtn;
    Intent menuCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_menu);

        menuCustomer = new Intent(this, menuView.class);
        menuPageBtn = findViewById(R.id.menuPageBtn);

        menuPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerMenu.this.startActivity(menuCustomer);
            }
        });


    }
}