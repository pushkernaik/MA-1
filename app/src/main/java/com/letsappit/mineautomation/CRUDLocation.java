package com.letsappit.mineautomation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.letsappit.mineautomation.BO.Location;
import com.letsappit.mineautomation.data.DBOps;

import java.util.ArrayList;

public class CRUDLocation extends AppCompatActivity {

    private EditText codeEditText;
    private EditText descEditText;
    private Button ok;
    Spinner codeSpinner;
    String code,description;
    long datetime;
    public String[] codes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
codeEditText = (EditText) findViewById(R.id.code);
        descEditText = (EditText)  findViewById(R.id.description);
        codeSpinner = (Spinner) findViewById(R.id.ids);
        ok = (Button)  findViewById(R.id.done);
       final ArrayList<Location> allLocations = DBOps.getAllLocations(this);
        codes = new String[allLocations.size()];
        for (int i=0;i<allLocations.size();i++) {
            codes[i] = allLocations.get(i).getCode();
        }
        ArrayAdapter<String> codeAdapter =
                new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,codes);
        codeSpinner.setAdapter(codeAdapter);
        codeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                codeEditText.setText(codes[position]);
                descEditText.setText(allLocations.get(position).getDescription());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void insert(View v)
    {
        datetime = System.currentTimeMillis();
        code = codeEditText.getEditableText().toString();
        description = descEditText.getEditableText().toString();

        DBOps.insertNewLocation(this, new Location(code, description, String.valueOf(datetime)));
    }
    public void update(View v)
    {
        datetime = System.currentTimeMillis();
        code = codeEditText.getEditableText().toString();
        description = descEditText.getEditableText().toString();

        DBOps.updateLocation(this, new Location(code, description, String.valueOf(datetime)));
    }
}
