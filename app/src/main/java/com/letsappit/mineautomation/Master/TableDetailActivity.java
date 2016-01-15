package com.letsappit.mineautomation.Master;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.letsappit.mineautomation.BO.ListItem;
import com.letsappit.mineautomation.PrimaryLocation.RowAdapterLocation;
import com.letsappit.mineautomation.PrimaryLocation.RowEditorLocation;
import com.letsappit.mineautomation.R;
import com.letsappit.mineautomation.Zone.RowAdapterZone;
import com.letsappit.mineautomation.Zone.RowEditorZone;
import com.letsappit.mineautomation.data.DBOps;
import com.letsappit.mineautomation.data.MAContract;

import java.util.ArrayList;

public class TableDetailActivity extends AppCompatActivity {

    private int tableId;
    private RecyclerView rowsRecyclerView;
    private ArrayList<ListItem> listItemGeneral;
    Class opClass;

    private LinearLayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rowsRecyclerView = (RecyclerView) findViewById(R.id.tables_recycler_view);
        listItemGeneral = new ArrayList<>();
        tableId = getIntent().getIntExtra("TABLE_ID",0);
        getTableDetails(tableId);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TableDetailActivity.this,opClass);
                intent.putExtra("OP_TYPE",1);
                startActivity(intent);

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getTableDetails(int tableId) {
        switch(tableId)
        {
            case 0:
                listItemGeneral = DBOps.getAllRows(this, MAContract.Location.TABLE_NAME,MAContract.Location.COLUMN_CODE,MAContract.Location.COLUMN_DESCRIPTION);
                RowAdapterLocation rowAdapter = new RowAdapterLocation(listItemGeneral,R.layout.list_item,this);
                displayTableContents(rowAdapter);
                opClass = RowEditorLocation.class;
                break;
            case 1:
                listItemGeneral = DBOps.getAllRows(this, MAContract.Zone.TABLE_NAME,MAContract.Zone.COLUMN_CODE,MAContract.Zone.COLUMN_DESCRIPTION);
                RowAdapterZone rowAdapterZone = new RowAdapterZone(listItemGeneral,R.layout.list_item,this);
                displayTableContents(rowAdapterZone);
                opClass = RowEditorZone.class;
                break;
            case 2:
//                listItemGeneral = DBOps.getAllRows(this, MAContract.Location.TABLE_NAME,MAContract.Location.COLUMN_CODE,MAContract.Location.COLUMN_DESCRIPTION);
//                RowAdapterLocation rowAdapter = new RowAdapterLocation(listItemGeneral,R.layout.list_item,this);
//                displayTableContents(rowAdapter);
//                opClass = RowEditorLocation.class;
//                break;

        }
    }

    private void displayTableContents(RecyclerView.Adapter adapter) {

        //tableRecyclerView.setAdapter();
        rowsRecyclerView.setHasFixedSize(true);
        //set layout manager
        mLayoutManager = new LinearLayoutManager(this);
        rowsRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter
        rowsRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTableDetails(tableId);
    }
}
