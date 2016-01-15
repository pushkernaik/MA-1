package com.letsappit.mineautomation.Master;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.Spinner;

import com.letsappit.mineautomation.BO.ListItem;
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


}
