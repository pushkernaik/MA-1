package com.letsappit.mineautomation;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nxp.nfcliblite.NxpNfcLibLite;

import org.json.JSONException;
import org.json.JSONObject;

import utils.NFCOps;

public class ReadCard extends AppCompatActivity {
    private NxpNfcLibLite libInstance = null;
    private TextView details;
    private LinearLayout parentLinearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initialize();
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

    private void initialize() {
parentLinearLayout = (LinearLayout) findViewById(R.id.parent_card_read) ;

        details = (TextView) findViewById(R.id.details_text_view);
        libInstance = NxpNfcLibLite.getInstance();
        libInstance.registerActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(libInstance!=null)
        {
            libInstance.stopForeGroundDispatch();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(libInstance!=null)
        {
            libInstance.startForeGroundDispatch();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        try {

            JSONObject dataFromTag = NFCOps.readTag(tagFromIntent);

            int cardType = dataFromTag.getInt("c_t");
            switch(cardType)
            {
                case 1 :
                    //truck card
                   JSONObject truck = dataFromTag.getJSONObject("t");
                    displayTruckDetails(truck,dataFromTag.getBoolean("wc"),dataFromTag.getString("prmt"));
                    displayLoadingDetails(dataFromTag.getJSONObject("l"));
                    break;
                case 2:
                    break;
            }



        } catch (JSONException e) {
            e.printStackTrace();
        } finally {

        }

    }
    public LinearLayout getModuleParent()
    {
       return(LinearLayout) getLayoutInflater().inflate(R.layout.card_details,null);

    }
    public RelativeLayout getDetailsParent()
    {
        return(RelativeLayout) getLayoutInflater().inflate(R.layout.card_item_details,null);

    }
    public RelativeLayout createItem(String categoryTitle,String code,String description)
    {
        RelativeLayout itemDetailsView = getDetailsParent();
        TextView category = (TextView) itemDetailsView.findViewById(R.id.cat_title);
        category.setText(categoryTitle);
        TextView code_text_view= (TextView) itemDetailsView.findViewById(R.id.title_text_view);
        code_text_view.setText(code);
        TextView description_text_view = (TextView) itemDetailsView.findViewById(R.id.description_text_view);
        description_text_view.setText(description);
return itemDetailsView;
    }
    public RelativeLayout createItem(String categoryTitle,String value)
    {
        RelativeLayout itemDetailsView = getDetailsParent();
        TextView category = (TextView) itemDetailsView.findViewById(R.id.cat_title);
        category.setText(categoryTitle);
        TextView code_text_view= (TextView) itemDetailsView.findViewById(R.id.title_text_view);
        code_text_view.setText(value);
        TextView description_text_view = (TextView) itemDetailsView.findViewById(R.id.description_text_view);
        description_text_view.setVisibility(View.GONE);
        return itemDetailsView;
    }
    public void displayTruckDetails(JSONObject truck,boolean wCard,String permit) throws JSONException {
        LinearLayout parent = getModuleParent();
        final LinearLayout detailsParent = (LinearLayout) parent.findViewById(R.id.details_linear_layout);
        TextView moduleTitle = (TextView) parent.findViewById(R.id.module_title);
        moduleTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detailsParent.getVisibility() == View.VISIBLE) {
                    detailsParent.setVisibility(View.GONE);
                } else {
                    detailsParent.setVisibility(View.VISIBLE);
                }
            }
        });
        moduleTitle.setText("TRUCK DETAILS");


        detailsParent.addView(createItem("Truck Code", truck.getString("t_c").toString(), wCard ? "Wild Card" :" " ));
        detailsParent.addView(createItem("Tare weight", String.valueOf(truck.getInt("t_w")), "Kg"));
        detailsParent.addView(createItem("driver code", truck.getString("d_c").toString(), "driver description here..."));
        detailsParent.addView(createItem("Transport Contractor code", truck.getString("c_c").toString(), "driver description here..."));
        detailsParent.addView(createItem("Group details", truck.getString("g_c").toString(), "group description here..."));
        detailsParent.addView(createItem("registration details", truck.getString("r_n").toString(), truck.getString("r_d").toString()));
        detailsParent.addView(createItem("Permit details", permit));
        parentLinearLayout.addView(parent);


    }
    public void displayLoadingDetails(JSONObject load) throws JSONException {
        LinearLayout parent = getModuleParent();
        final LinearLayout detailsParent = (LinearLayout) parent.findViewById(R.id.details_linear_layout);
        TextView moduleTitle = (TextView) parent.findViewById(R.id.module_title);
        moduleTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detailsParent.getVisibility() == View.VISIBLE) {
                    detailsParent.setVisibility(View.GONE);
                } else {
                    detailsParent.setVisibility(View.VISIBLE);
                }
            }
        });
        moduleTitle.setText("LOADING DETAILS");


//        detailsParent.addView(createItem("Truck Code", truck.getString("t_c").toString(), wCard ? "Wild Card" :" " ));
//        detailsParent.addView(createItem("Tare weight", String.valueOf(truck.getInt("t_w")), "Kg"));
//        detailsParent.addView(createItem("driver code", truck.getString("d_c").toString(), "driver description here..."));
//        detailsParent.addView(createItem("Transport Contractor code", truck.getString("c_c").toString(), "driver description here..."));
//        detailsParent.addView(createItem("Group details", truck.getString("g_c").toString(), "group description here..."));
//        detailsParent.addView(createItem("registration details", truck.getString("r_n").toString(), truck.getString("r_d").toString()));

        parentLinearLayout.addView(parent);

    }


    private void parseTruckObject(JSONObject dataFromTag) {
      //parse object here
        Log.i("String JSON OBJECT", dataFromTag.toString());
        details.setText(dataFromTag.toString());

    }
}




