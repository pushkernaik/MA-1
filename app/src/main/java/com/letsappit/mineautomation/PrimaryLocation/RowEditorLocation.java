package com.letsappit.mineautomation.PrimaryLocation;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.letsappit.mineautomation.BO.PrimaryLocation;
import com.letsappit.mineautomation.R;
import com.letsappit.mineautomation.data.DBOps;
import com.letsappit.mineautomation.data.MAContract;

import java.text.ParseException;
import java.util.Date;

import utils.Util;

public class RowEditorLocation extends AppCompatActivity {
String code ;
    Button add,delete,update;
    private EditText codeEditText,descriptionEditText;
    TextView updated;
    private Date datetime;
    private String description;
    int opType=0;

    final int  DIALOG_TYPE_PROGRESS = 1,
            DIALOG_TYPE_ALERT = 2, ALERT_TYPE_FAILURE = 2,
            ALERT_TYPE_SUCCESS = 1;
    ProgressDialog reqProgress;
    AlertDialog reDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_row_editor_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        code = getIntent().getStringExtra("ROW_CODE");
        opType = getIntent().getIntExtra("OP_TYPE", 0);
        try {
            manageLayoutFromOpType(opType);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void manageLayoutFromOpType(int opType) throws ParseException {
        if(opType!=0)
        {
            if(opType ==1)
            {
                initializeViews(opType);
            }
            else
            {
                initializeViews(opType);
                PrimaryLocation currentPrimaryLocation = DBOps.getLocationByCode(this,code);

                populateViews(currentPrimaryLocation);


            }
        }
    }

    private void populateViews(PrimaryLocation currentPrimaryLocation) {
        codeEditText.setText(currentPrimaryLocation.getCode());
        descriptionEditText.setText(currentPrimaryLocation.getDescription());
        updated.setText("Last Updated on :" + Util.getFormatedDate(currentPrimaryLocation.getDatetime()));
    }

    private void initializeViews(int opType) {

        codeEditText = (EditText) findViewById(R.id.code_edit_text);
        descriptionEditText = (EditText) findViewById(R.id.description_edit_text);
        updated = (TextView) findViewById(R.id.updated_text_view);
        update = (Button) findViewById(R.id.update_location);
        delete = (Button) findViewById(R.id.delete_location);

        add = (Button) findViewById(R.id.add_location);
        if(opType==1)
        {

            add.setVisibility(View.VISIBLE);

            update.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
        }
        else
        {

            add.setVisibility(View.GONE);
            update.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
        }
    }
    public void insert(View v)
    {
        datetime = new Date();
        code = codeEditText.getEditableText().toString();
        description = descriptionEditText.getEditableText().toString();

        long i = DBOps.insertNewLocation(this, new PrimaryLocation(code, description,datetime ));
        if(i!=-1)
        {
            showDialog("Insert Successfull","Entry added successfully",DIALOG_TYPE_ALERT,ALERT_TYPE_SUCCESS);
        }
        else
        {
            showDialog("Error","Failed to add this entry",DIALOG_TYPE_ALERT,ALERT_TYPE_FAILURE);

        }

    }

public void delete(View v)
{

   int i =  DBOps.deleteRow(this, code, MAContract.Location.TABLE_NAME, MAContract.Location.COLUMN_CODE);
    if(i==1)
    {
        showDialog("Delete Successfull","Entry deleted successfully",DIALOG_TYPE_ALERT,ALERT_TYPE_SUCCESS);
    }
    else
    {
        showDialog("Error","Failed to delete this entry",DIALOG_TYPE_ALERT,ALERT_TYPE_FAILURE);
    }

}
    public void update(View v)
    {
        datetime = new Date();
        // code = codeEditText.getEditableText().toString();
        description = descriptionEditText.getEditableText().toString();

        int i = DBOps.updateLocation(this, new PrimaryLocation(code, description, datetime));
        if(i==1)
        {
            showDialog("Update Successfull","Entry updated successfully",DIALOG_TYPE_ALERT,ALERT_TYPE_SUCCESS);
        }
        else
        {
            showDialog("Error","Failed to update this entry",DIALOG_TYPE_ALERT,ALERT_TYPE_FAILURE);
        }
    }
    private void showDialog(String title, String message, int dialogType,
                            int alertType) {

        switch (dialogType) {
            case DIALOG_TYPE_PROGRESS:
                reqProgress = new ProgressDialog(this);
                reqProgress.setTitle(title);
                reqProgress.setMessage(message);

                reqProgress.show();
                break;
            case DIALOG_TYPE_ALERT:
                try {
                    reDialog = new AlertDialog.Builder(this).create();
                    reDialog.setTitle(title);

                    reDialog.setMessage(message);
                    if (alertType == ALERT_TYPE_SUCCESS) {

                        reDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface arg0,
                                                        int arg1) {
                                        // TODO Auto-generated method stub
                                        finish();

                                    }
                                });
                    } else if (alertType == ALERT_TYPE_FAILURE) {
                        reDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface arg0,
                                                        int arg1) {
                                        // TODO Auto-generated method stub

                                    }
                                });
                    }
                    reDialog.show();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                break;

        }

    }





    private void stopProgress() {
        if (reqProgress != null && reqProgress.isShowing()) {
            reqProgress.dismiss();
        }
    }



}
