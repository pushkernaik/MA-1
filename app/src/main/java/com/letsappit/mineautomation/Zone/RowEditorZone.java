package com.letsappit.mineautomation.Zone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.letsappit.mineautomation.R;

public class RowEditorZone extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_row_activity_zone);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
//    private void manageLayoutFromOpType(int opType) {
//        if(opType!=0)
//        {
//            if(opType ==1)
//            {
//                initializeViews(opType);
//            }
//            else
//            {
//                initializeViews(opType);
//                PrimaryLocation currentPrimaryLocation = DBOps.getLocationByCode(this, code);
//
//                populateViews(currentPrimaryLocation);
//
//
//            }
//        }
//    }

    //private void populateViews(Zone currentLocation) {
//        codeEditText.setText(currentLocation.getCode());
//        descriptionEditText.setText(currentLocation.getDescription());
//        updated.setText(updated.getText() + currentLocation.getDatetime());
//    }
//
//    private void initializeViews(int opType) {
//
//        codeEditText = (EditText) findViewById(R.id.code_edit_text);
//        descriptionEditText = (EditText) findViewById(R.id.description_edit_text);
//        updated = (TextView) findViewById(R.id.updated_text_view);
//        update = (Button) findViewById(R.id.update_location);
//        delete = (Button) findViewById(R.id.delete_location);
//
//
//        if(opType==1)
//        {
//            add = (Button) findViewById(R.id.add_location);
//            add.setVisibility(View.VISIBLE);
//
//            update.setVisibility(View.GONE);
//            delete.setVisibility(View.GONE);
//        }
//        else
//        {
//            add = (Button) findViewById(R.id.add_location);
//            add.setVisibility(View.GONE);
//            update.setVisibility(View.VISIBLE);
//            delete.setVisibility(View.VISIBLE);
//        }
//    }
//    public void insert(View v)
//    {
//        datetime = System.currentTimeMillis();
//        code = codeEditText.getEditableText().toString();
//        description = descriptionEditText.getEditableText().toString();
//
//        long i = DBOps.insertNewLocation(this, new PrimaryLocation(code, description, String.valueOf(datetime)));
//        if(i!=-1)
//        {
//            showDialog("Insert Successfull","Entry added successfully",DIALOG_TYPE_ALERT,ALERT_TYPE_SUCCESS);
//        }
//        else
//        {
//            showDialog("Error","Failed to add this entry",DIALOG_TYPE_ALERT,ALERT_TYPE_FAILURE);
//
//        }
//
//    }
//
//    public void delete(View v)
//    {
//
//        int i =  DBOps.deleteLocation(this, code);
//        if(i==1)
//        {
//            showDialog("Delete Successfull","Entry deleted successfully",DIALOG_TYPE_ALERT,ALERT_TYPE_SUCCESS);
//        }
//        else
//        {
//            showDialog("Error","Failed to delete this entry",DIALOG_TYPE_ALERT,ALERT_TYPE_FAILURE);
//        }
//
//    }
//    public void update(View v)
//    {
//        datetime = System.currentTimeMillis();
//        // code = codeEditText.getEditableText().toString();
//        description = descriptionEditText.getEditableText().toString();
//
//        int i = DBOps.updateLocation(this, new PrimaryLocation(code, description, String.valueOf(datetime)));
//        if(i==1)
//        {
//            showDialog("Update Successfull","Entry updated successfully",DIALOG_TYPE_ALERT,ALERT_TYPE_SUCCESS);
//        }
//        else
//        {
//            showDialog("Error","Failed to update this entry",DIALOG_TYPE_ALERT,ALERT_TYPE_FAILURE);
//        }
//    }
//    private void showDialog(String title, String message, int dialogType,
//                            int alertType) {
//
//        switch (dialogType) {
//            case DIALOG_TYPE_PROGRESS:
//                reqProgress = new ProgressDialog(this);
//                reqProgress.setTitle(title);
//                reqProgress.setMessage(message);
//
//                reqProgress.show();
//                break;
//            case DIALOG_TYPE_ALERT:
//                try {
//                    reDialog = new AlertDialog.Builder(this).create();
//                    reDialog.setTitle(title);
//
//                    reDialog.setMessage(message);
//                    if (alertType == ALERT_TYPE_SUCCESS) {
//
//                        reDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK",
//                                new DialogInterface.OnClickListener() {
//
//                                    @Override
//                                    public void onClick(DialogInterface arg0,
//                                                        int arg1) {
//                                        // TODO Auto-generated method stub
//                                        finish();
//
//                                    }
//                                });
//                    } else if (alertType == ALERT_TYPE_FAILURE) {
//                        reDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK",
//                                new DialogInterface.OnClickListener() {
//
//                                    @Override
//                                    public void onClick(DialogInterface arg0,
//                                                        int arg1) {
//                                        // TODO Auto-generated method stub
//
//                                    }
//                                });
//                    }
//                    reDialog.show();
//                } catch (NullPointerException e) {
//                    e.printStackTrace();
//                }
//                break;
//
//        }
//
//    }
//
//
//
//
//
//    private void stopProgress() {
//        if (reqProgress != null && reqProgress.isShowing()) {
//            reqProgress.dismiss();
//        }
//    }



}
