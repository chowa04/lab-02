package com.example.listycity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView cityList;
    Button addButton, deleteButton;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityList = findViewById(R.id.city_list);
        addButton = findViewById(R.id.button_add);
        deleteButton = findViewById(R.id.button_delete);

        dataList = new ArrayList<>();
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        // ADD CITY button - Dialog at BOTTOM
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Add City");

                final EditText input = new EditText(MainActivity.this);
                input.setHint("Type city name here");
                builder.setView(input);

                builder.setPositiveButton("CONFIRM", (dialog, which) -> {
                    String cityName = input.getText().toString().trim();
                    if (!cityName.isEmpty()) {
                        dataList.add(cityName);
                        cityAdapter.notifyDataSetChanged();
                        // Scroll to show new city at bottom
                        cityList.smoothScrollToPosition(dataList.size() - 1);
                    }
                });


                AlertDialog dialog = builder.create();


                Window window = dialog.getWindow();
                if (window != null) {
                    WindowManager.LayoutParams params = window.getAttributes();
                    params.gravity = Gravity.BOTTOM;
                    params.y = 100;
                    window.setAttributes(params);
                }

                dialog.show();
            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPosition != -1) {
                    dataList.remove(selectedPosition);
                    cityAdapter.notifyDataSetChanged();
                    selectedPosition = -1;
                }
            }
        });


        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
            }
        });
    }
}