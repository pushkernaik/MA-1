package com.letsappit.mineautomation.Master;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.letsappit.mineautomation.BO.ListItem;
import com.letsappit.mineautomation.CRUDLocation;
import com.letsappit.mineautomation.R;

import java.util.ArrayList;

public class Master extends AppCompatActivity {
    String[] master;
    private Spinner list;
    private Button addButton;
    private Button updateButton;
    private Button deleteButton;
    private RecyclerView tableRecyclerView;
    private ArrayList<ListItem> listItemGeneral;
    private TableAdapter tableAdapter;
    private LinearLayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        list = (Spinner) findViewById(R.id.spinner1);
        addButton = (Button) findViewById(R.id.add);
        updateButton = (Button) findViewById(R.id.update);
        deleteButton = (Button) findViewById(R.id.delete);
        tableRecyclerView = (RecyclerView) findViewById(R.id.tables_recycler_view);

        initializeTableContent();
        tableAdapter = new TableAdapter(listItemGeneral,R.layout.list_item,this);
        //tableRecyclerView.setAdapter();
        tableRecyclerView.setHasFixedSize(true);
        //set layout manager
        mLayoutManager = new LinearLayoutManager(this);
        tableRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter
        tableRecyclerView.setAdapter(tableAdapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeTableContent() {
        String[] tableTitles = getResources().getStringArray(R.array.table_titles);
        String[] tableDescription = getResources().getStringArray(R.array.table_description);
        listItemGeneral = new ArrayList<>();
        for (int i = 0;i<tableTitles.length;i++) {
            listItemGeneral.add(new ListItem(tableTitles[i],tableDescription[i]));

        }

    }

    public void add(View v) {
        int id = list.getSelectedItemPosition();
        switch (id) {
            case 0:
                startActivity(new Intent(Master.this, CRUDLocation.class));
                finish();
            case 1:
                finish();
        }
    }

    public void update(View v) {
        int id = list.getSelectedItemPosition();
        switch (id) {
            case 0:
                startActivity(new Intent(Master.this, CRUDLocation.class));
                break;
            case 1:
                break;
        }
    }

}
