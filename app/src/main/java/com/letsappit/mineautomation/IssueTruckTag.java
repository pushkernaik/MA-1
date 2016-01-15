package com.letsappit.mineautomation;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.letsappit.mineautomation.BO.Truck;
import com.letsappit.mineautomation.data.DBOps;
import com.nxp.nfclib.classic.IMFClassic;
import com.nxp.nfclib.icode.IICodeSLI;
import com.nxp.nfclib.icode.IICodeSLIL;
import com.nxp.nfclib.icode.IICodeSLIS;
import com.nxp.nfclib.icode.IICodeSLIX;
import com.nxp.nfclib.icode.IICodeSLIX2;
import com.nxp.nfclib.icode.IICodeSLIXL;
import com.nxp.nfclib.icode.IICodeSLIXS;
import com.nxp.nfclib.plus.IPlusSL1;
import com.nxp.nfclib.ultralight.IUltralight;
import com.nxp.nfclib.ultralight.IUltralightC;
import com.nxp.nfclib.ultralight.IUltralightEV1;
import com.nxp.nfcliblite.NxpNfcLibLite;
import com.nxp.nfcliblite.cards.IDESFireEV1;
import com.nxp.nfcliblite.cards.IPlus;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

import utils.Keys;
import utils.NFCOps;
import utils.Util;

public class IssueTruckTag extends AppCompatActivity {

    private AutoCompleteTextView autoCompleteRegNo;
    private TextView detailsTextView;
    private Button writeTagButton;
    ArrayList<String> allRegistrationNumbers;
    private NfcAdapter mAdapter;
    private PendingIntent mPendingIntent;
    JSONObject tagData;
    final int  DIALOG_TYPE_PROGRESS = 1,
            DIALOG_TYPE_ALERT = 2, ALERT_TYPE_FAILURE = 2,
            ALERT_TYPE_SUCCESS = 1;
    ProgressDialog reqProgress;
    AlertDialog reDialog;

    final int DIALOG_TYPE_NOTIFY = 3;
    /** Lite application Tag. */

    /** Create lib lite instance. */


    /** Lite application Tag. */
    static final String TAG = "SampleNxpNfcLibLite";
    /** Create lib lite instance. */
    private NxpNfcLibLite libInstance = null;
    /** Mifare DESFire instance initiated. */
    private IDESFireEV1 mDESFire;

    /** Mifare MFClassic instance initiated. */
    private IMFClassic classic;


    /** Mifare Ultralight instance initiated. */
    private IUltralight mifareUL;
    /** Mifare Ultralight instance initiated. */
    private IUltralightC objUlCardC;
    /** Mifare Ultralight EV1 instance initiated. */
    private IUltralightEV1 objUlCardEV1;
    /** Mifare Plus instance initiated. */
    private IPlus plus;

    /** Mifare Plus SL1 instance initiated. */
    private IPlusSL1 plusSL1;

    /** ICode SLI instance initiated. */
    private IICodeSLI iCodeSli;
    /** ICode SLI-L instance initiated. */
    private IICodeSLIL iCodeSliL;
    /** ICode SLI-S instance initiated. */
    private IICodeSLIS iCodeSliS;
    /** ICode SLI-X instance initiated. */
    private IICodeSLIX iCodeSliX;
    /** ICode SLI-XL instance initiated. */
    private IICodeSLIXL iCodeSliXL;
    /** ICode SLI-XS instance initiated. */
    private IICodeSLIXS iCodeSliXS;
    /** ICode SLIX2 instance initiated. */
    private IICodeSLIX2 iCodeSliX2;

    /** Create imageView instance. */
    private ImageView mImageView = null;
    // private static Handler mHandler;
    /** Create Textview instance initiated. */
    private TextView tv = null;
    /** Checkbox for write select */
    private CheckBox mCheckToWrite = null;

    /**
     * Ultralight First User Memory Page Number.
     */
    private static final int DEFAULT_PAGENO_ULTRALIGHT = 4;
    /**
     * Variable DATA Contain a String.
     */
    private static final String DATA = "This is the data";

    /**
     * KEY_APP_MASTER key used for encrypt data.
     */

    private enum EnumCardType {
        EnumUltraLight,
        EnumClassic,
        EnumUltraLightC,
        EnumUltraLightEV1,
        EnumPlus,
        EnumPlusSL1,
        EnumNTag203x,
        EnumNTag210,
        EnumNTag213215216,
        EnumNTag213F216F,
        EnumNTagI2C,
        EnumNTagI2CPLUS,
        EnumICodeSLI,
        EnumICodeSLIS,
        EnumICodeSLIL,
        EnumICodeSLIX,
        EnumICodeSLIXS,
        EnumICodeSLIXL,
        EnumICodeSLIX2,
        EnumDESFireEV1,
        EnumPlusX,
        EnumPlusS,
        EnumClassicEV1,
        EnumNone

    }


    /**
     * KEY_APP_MASTER key used for encrypt data.
     */
    private static final String KEY_APP_MASTER = Keys.ENCRYPTION_KEY;
    /** */
    private byte[] bytesKey = null;
    /** */
    private Cipher cipher = null;
    /** */
    private IvParameterSpec iv = null;

    private boolean bWriteAllowed = false;

    private Tag tag;
    private boolean trucksInserted = false;



    private EnumCardType mCardType = EnumCardType.EnumNone;

    private boolean mIsPerformingCardOperations = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_truck_tag);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //
        libInstance = NxpNfcLibLite.getInstance();
        //intent filter directly picked from manifest
        libInstance.registerActivity(this);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            // Registering the activity for the NFC tag detection mode.
//
//            final Handler mLibhandler = new Handler(mLibhandlercb);
//
//            try {
//                libInstance.setNfcControllerMode(1000, new NfcAdapter.ReaderCallback() {
//
//                    @Override
//                    public void onTagDiscovered(final Tag tagObject) {
//                        NxpLogUtils.d(TAG,
//                                "TAG is Discovered from ReaderCallBack...");
//
//                        tag = tagObject;
//                        mLibhandler.sendEmptyMessage(0);
//
//                    }
//                }, NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_NFC_V | NfcAdapter.FLAG_READER_NFC_B | NfcAdapter.FLAG_READER_NFC_F );
//            } catch (SmartCardException e) {
//
//            }
//        }
        //
        if(DBOps.getAllTruckRegNo(this).size()==0)
        {
            DBOps.insertNewTruck(this,new Truck("TC1", "GA04J4008","TCC1","DC1","GC1","CARDID1000","CAT1",new Date(),7000,3000));
            DBOps.insertNewTruck(this,new Truck("TC2", "GA09J4567","TCC2","DC2","GC2","CARDID2001","CAT2",new Date(),7000,4000));
            DBOps.insertNewTruck(this,new Truck("TC3", "GA04J4444","TCC3","DC3","GC3","CARDID3002","CAT3",new Date(),7000,5000));

        }
        initializeViews();
        getRegistrationNumbers();
        bindDataToAutoCompleteTextView();




    }

    private void bindDataToAutoCompleteTextView() {
        ArrayAdapter<String> adp=new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,allRegistrationNumbers);
        autoCompleteRegNo.setThreshold(1);
        autoCompleteRegNo.setAdapter(adp);
        autoCompleteRegNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    showTruckDetails(allRegistrationNumbers.get(position));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showTruckDetails(String s) throws ParseException {
        final Truck  selectedTruck = DBOps.getTruckByRegNo(this,s);
        String details = "Truck Code : " + selectedTruck.getCode() +"\n"+
                "Tare Weight : " + selectedTruck.getTare_wt() +"\n"+
                "Group : " + selectedTruck.getGroup_code() +"\n"+
                "Capacity : " + selectedTruck.getCapacity() +"\n"+
                "Registration Date : " + Util.getFormatedDate(selectedTruck.getReg_date()) ;
        detailsTextView.setText(details);
        detailsTextView.setVisibility(View.VISIBLE);
        writeTagButton.setVisibility(View.VISIBLE);
        writeTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    startWriteProcedure(selectedTruck);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    private void startWriteProcedure(Truck selectedTruck) throws JSONException {
        tagData = Util.getBaseTruckTagString(selectedTruck, false);
        libInstance = NxpNfcLibLite.getInstance();
        libInstance.registerActivity(this);
        libInstance.startForeGroundDispatch();
        showDialog("NFC READY", "tap the tag to write", DIALOG_TYPE_NOTIFY, ALERT_TYPE_FAILURE);
//        mAdapter = NfcAdapter.getDefaultAdapter(this);
//        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
//        if (mAdapter != null) {
//            mAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
//            showDialog("NFC READY","tap the tag to write",DIALOG_TYPE_NOTIFY,ALERT_TYPE_FAILURE);
//            Log.i("NDEF", "enable forground dispatch");
//        }
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onNewIntent(Intent intent) {
       // if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        //IsoDep isodep = IsoDep.get(tagFromIntent);
        //showMessage("isodep "+ isodep.toString(),'n');

//        try {
//            libInstance.filterIntent(intent, mCallback);
//        } catch (CloneDetectedException e) {
//            e.printStackTrace();
//        }
//
            showDialog("NFC TAG", "writting in progress", DIALOG_TYPE_PROGRESS, ALERT_TYPE_FAILURE);
            try {
                NFCOps.write(tagData.toString(), tagFromIntent, intent.getAction());
                stopProgress();
                showDialog("OPERATION SUCCESSFULL","Tag written successfully" +
                                "\nYou can now move the card away from the device",
                        DIALOG_TYPE_ALERT,
                        ALERT_TYPE_FAILURE);

            } catch (IOException e) {
                e.printStackTrace();
                showDialog("OPERATION FAILED","Connection failed! " +
                        "\nplease do not move the card away from the device while writting is in progress",
                        DIALOG_TYPE_ALERT,
                        ALERT_TYPE_FAILURE);
            } catch (FormatException e) {
                e.printStackTrace();

            }
            finally {

            }

//            stopProgress();
//
//            showDialog("NFC TAG", "writting in progress", DIALOG_TYPE_PROGRESS, ALERT_TYPE_FAILURE);
//            try {
//                libInstance.filterIntent(intent, mCallback);
//            } catch (CloneDetectedException e) {
//                e.printStackTrace();
//            }
//            try {
//                NFCOps.write("abcd".toString(),tagFromIntent);
//            } catch (IOException e) {
//                e.printStackTrace();
//                stopProgress();
//                showDialog("OPERATION FAILED","Connection failed! \nplease do not move the card away from the device while writting is in progress",DIALOG_TYPE_ALERT,ALERT_TYPE_FAILURE);
//            } catch (FormatException e) {
//                stopProgress();
//                e.printStackTrace();
//            }

        }

    //}

    private void initializeViews() {
        autoCompleteRegNo = (AutoCompleteTextView) findViewById(R.id.truck_reg_number);
        detailsTextView = (TextView) findViewById(R.id.details_text_view);
        writeTagButton = (Button) findViewById(R.id.write_button);
        allRegistrationNumbers = new ArrayList<>();

    }
    @Override
    public void onResume() {
        super.onResume();

    }

    public void getRegistrationNumbers() {
      allRegistrationNumbers = DBOps.getAllTruckRegNo(this);

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
            case DIALOG_TYPE_NOTIFY:
                reDialog = new AlertDialog.Builder(this).create();
                reDialog.setTitle(title);

                reDialog.setMessage(message);
                reDialog.show();
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

//    private void testDESFireRead() {
//
//        boolean res = false;
//        try {
//            NxpLogUtils.d(TAG, "testDESFireRead, start");
//            byte[] data = mDESFire.read(5);
//            res = true;
//            showMessage(
//                    "Data Read from the card..." + Utilities.dumpBytes(data),
//                    'd');
//        } catch (SmartCardException e) {
//            showMessage("Data Read from the card: " + res, 'd');
//            e.printStackTrace();
//        }
//        NxpLogUtils.d(TAG, "testDESFireRead, result is " + res);
//        NxpLogUtils.d(TAG, "testDESFireRead, End");
//    }
//
//    /** DESFire Write IO Operations. */
//    private void testDESFireWrite() {
//
//        byte[] data = new byte[] { 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11,
//                0x11 };
//
//        boolean res = false;
//        try {
//            NxpLogUtils.d(TAG, "testDESFireWrite, start");
//            mDESFire.write(data);
//            res = true;
//            showMessage("Data Written: " + Utilities.dumpBytes(data), 'd');
//        } catch (SmartCardException e) {
//            showMessage("Data Written: " + res, 'd');
//            e.printStackTrace();
//        }
//        NxpLogUtils.d(TAG, "testDESFireWrite, result is " + res);
//        NxpLogUtils.d(TAG, "testDESFireWrite, End");
//
//    }
//
//    /** DESFire Update Application master key IO Operations. */
//    private void testDESFireupdateApplicationMasterKey() {
//        byte[] oldKey = new byte[] { 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
//                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
//        byte[] newKey = new byte[] { 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
//                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
//
//        byte[] masterKey = new byte[] { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
//                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
//
//        byte[] appId = { 0x12, 0x12, 0x12 };
//        boolean res = false;
//        try {
//            NxpLogUtils.d(TAG, "testDESFireupdateApplicationMasterKey, start");
//            mDESFire.updateApplicationMasterKey(masterKey, appId, oldKey,
//                    newKey);
//            res = true;
//            showMessage("Update Application MasterKey: " + res, 'd');
//        } catch (SmartCardException e) {
//            showMessage("Update Application MasterKey: " + res, 'd');
//            e.printStackTrace();
//        }
//        NxpLogUtils.d(TAG, "testDESFireupdateApplicationMasterKey, result is "
//                + res);
//        NxpLogUtils.d(TAG, "testDESFireupdateApplicationMasterKey, End");
//    }
//
//    /** DESFire Authenticate IO Operations . */
//    private void testDESFireauthenticate() {
//        byte[] masterKey = new byte[] { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
//                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
//        byte[] appId = { 0x12, 0x12, 0x12 };
//        byte[] appkey = new byte[] { 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
//                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
//
//        boolean res = false;
//        try {
//            NxpLogUtils.d(TAG, "testDESFireauthenticate, start");
//            mDESFire.authenticate(masterKey, appId, appkey);
//            res = true;
//            showMessage("Authenticate: " + res, 'd');
//        } catch (SmartCardException e) {
//            showMessage("Authenticate: " + res, 'd');
//            e.printStackTrace();
//        }
//        NxpLogUtils.d(TAG, "testDESFireauthenticate, result is " + res);
//        NxpLogUtils.d(TAG, "testDESFireauthenticate, End");
//    }
//
//    /** DESFire personalize Operations. */
//    private void testDESFirepersonalize() {
//        byte[] mykey = new byte[] { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
//                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
//        byte[] appKey = new byte[] { 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
//                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
//
//        boolean res = false;
//        try {
//            NxpLogUtils.d(TAG, "testDESFirepersonalize, start");
//
//            mDESFire.personalize(mykey, new byte[]{0x12, 0x12, 0x12}, appKey);
//            res = true;
//            showMessage("personalize: " + res, 'd');
//        } catch (SmartCardException e) {
//            showMessage("personalize: " + res, 'd');
//            e.printStackTrace();
//        }
//        NxpLogUtils.d(TAG, "testDESFirepersonalize, result is " + res);
//        NxpLogUtils.d(TAG, "testDESFirepersonalize, End");
//
//    }
//
//    /** DESFire update PICC Master key Operations . */
//    private void testDESFireupdatePICCMasterKey() {
//        byte[] oldKey = new byte[] { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
//                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
//        byte[] newKey = new byte[] { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
//                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
//        boolean res = false;
//        try {
//            NxpLogUtils.d(TAG, "testDESFireupdatePICCMasterKey, start");
//            mDESFire.updatePICCMasterKey(oldKey, newKey);
//            res = true;
//            showMessage("DESFire Update PICC Master Key: " + res, 'd');
//        } catch (SmartCardException e) {
//            showMessage("DESFire Update PICC Master Key: " + res, 'd');
//            e.printStackTrace();
//        }
//        NxpLogUtils.d(TAG, "testDESFireupdatePICCMasterKey, result is " + res);
//        NxpLogUtils.d(TAG, "testDESFireupdatePICCMasterKey, End");
//
//    }
//
//    /** DESFire Format Operations . */
//    private void testDESFireFormat() {
//        byte[] mykey = new byte[] { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
//                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
//
//        boolean res = false;
//        try {
//            NxpLogUtils.d(TAG, "testDESFireFormat, start");
//            mDESFire.format(mykey);
//            res = true;
//            showMessage("Format: " + res, 'd');
//        } catch (SmartCardException e) {
//            showMessage("Format: " + res, 'd');
//            e.printStackTrace();
//        }
//        NxpLogUtils.d(TAG, "testDESFireFormat, result is " + res);
//        NxpLogUtils.d(TAG, "testDESFireFormat, End");
//    }
//
//    private void initializeCipherinitVector() {
//
//		/* Initialize the Cipher */
//        try {
//            cipher = Cipher.getInstance("AES/CBC/NoPadding");
//        } catch (NoSuchAlgorithmException e) {
//
//            e.printStackTrace();
//        } catch (NoSuchPaddingException e) {
//
//            e.printStackTrace();
//        }
//
//		/* set Application Master Key */
//        bytesKey = KEY_APP_MASTER.getBytes();
//
//		/* Initialize init vector of 16 bytes with 0xCD. It could be anything */
//        byte[] ivSpec = new byte[16];
//        Arrays.fill(ivSpec, (byte) 0xCD);
//        iv = new IvParameterSpec(ivSpec);
//
//    }
//    protected void showMessage(final String str, final char where) {
//
//        switch (where) {
//
//            case 't':
//                Toast.makeText(this, "\n" + str,
//                        Toast.LENGTH_SHORT).show();
//                break;
//            case 'l':
//                NxpLogUtils.i(TAG, "\n" + str);
//                break;
//            case 'd':
//
//                break;
//            case 'a':
//                Toast.makeText(this, "\n" + str,
//                        Toast.LENGTH_SHORT).show();
//                NxpLogUtils.i(TAG, "\n" + str);
//
//                break;
//            case 'n':
//                NxpLogUtils.i(TAG, "Dump Data: " + str);
//
//                break;
//            default:
//                break;
//        }
//        return;
//    }

    private void stopProgress() {
        if (reqProgress != null && reqProgress.isShowing()) {
            reqProgress.dismiss();
        }
        if(reDialog!= null && reDialog.isShowing())
        {
            reDialog.dismiss();
        }
    }
//    private Handler.Callback mLibhandlercb = new Handler.Callback() {
//
//        @Override
//        public boolean handleMessage(final Message arg0) {
//
//            try {
//                libInstance.filterIntent(tag, mCallback );
//            } catch (CloneDetectedException e) {
//                NxpLogUtils.e(TAG,e.getMessage(),e);
//            }
//            return false;
//        }
//
//    };
  //  private Nxpnfcliblitecallback mCallback = new Nxpnfcliblitecallback() {

//
//        @Override
//        public void onNewTagDetected(Tag tag) {
//            Log.d(TAG, "-------------- onNewTagDetected ------");
//
//        }
//
//        @Override
//        public void onUltraLightCardDetected(final IUltralight objUlCard) {
//
//            if(mCardType == EnumCardType.EnumUltraLight && mIsPerformingCardOperations){
//                //Already Some Operations are happening in the same card, discard the callback
//                Log.d(TAG, "----- Already Some Operations are happening in the same card, discard the callback: " + mCardType.toString());
//                return;
//            }
//
//            mIsPerformingCardOperations = true;
//            mCardType = EnumCardType.EnumUltraLight;
//            mifareUL = objUlCard;
//				/* Insert your logic here by commenting the function call below. */
//            try {
//                mifareUL.getReader().connect();
//               // ultralightCardLogic();
//            } catch (Throwable t) {
//                t.printStackTrace();
//                showMessage("Unknown Error Tap Again!", 't');
//            }
//
//
//            mIsPerformingCardOperations = false;
//        }
//
//        @Override
//        public void onUltraLightCCardDetected(final IUltralightC ulC) {
//
//            if(mCardType == EnumCardType.EnumUltraLightC && mIsPerformingCardOperations){
//                //Already Some Operations are happening in the same card, discard the callback
//                Log.d(TAG, "----- Already Some Operations are happening in the same card, discard the callback: " + mCardType.toString());
//                return;
//            }
//            mIsPerformingCardOperations = true;
//            mCardType = EnumCardType.EnumUltraLightC;
//
//            objUlCardC = ulC;
//				/*
//				 * Insert your logic here by commenting the function call below
//				 */
//            try {
//                objUlCardC.getReader().connect();
//               // ultralightcCardLogic();
//            } catch (Throwable t) {
//                showMessage("Unknown Error Tap Again!", 't');
//            }
//
//            mIsPerformingCardOperations = false;
//        }
//
//        @Override
//        public void onUltraLightEV1CardDetected(final IUltralightEV1 ulEV1) {
//
//            if(mCardType == EnumCardType.EnumUltraLightEV1 && mIsPerformingCardOperations){
//                //Already Some Operations are happening in the same card, discard the callback
//                Log.d(TAG, "----- Already Some Operations are happening in the same card, discard the callback: " + mCardType.toString());
//                return;
//            }
//            mIsPerformingCardOperations = true;
//            mCardType = EnumCardType.EnumUltraLightEV1;
//
//            objUlCardEV1 = ulEV1;
//				/*
//				 * Insert your logic here by commenting the function call below
//				 */
//            try {
//                objUlCardEV1.getReader().connect();
//               // ultralightEV1CardLogic();
//            } catch (Throwable t) {
//                showMessage("Unknown Error Tap Again!", 't');
//            }
//
//            mIsPerformingCardOperations = false;
//        }
//
//        @Override
//        public void onClassicCardDetected(final IMFClassic objMFCCard) {
//            Log.e(TAG, "--------------- onClassicCardDetected --------------");
//            if(mCardType == EnumCardType.EnumClassic && mIsPerformingCardOperations){
//                //Already Some Operations are happening in the same card, discard the callback
//                Log.d(TAG, "----- Already Some Operations are happening in the same card, discard the callback: " + mCardType.toString());
//                return;
//            }
//            mIsPerformingCardOperations = true;
//            mCardType = EnumCardType.EnumClassic;
//
//
//            classic = objMFCCard;
//				/* Insert your logic here by commenting the function call below. */
//            try {
//                classic.getReader().connect();
//                //classicCardLogic();
//            } catch (Throwable t) {
//                showMessage("Unknown Error Tap Again!", 't');
//            }
//
//            mIsPerformingCardOperations = false;
//        }
//
//        @Override
//        public void onClassicEV1CardDetected(final IMFClassicEV1 objMFCEV1Card) {
//
//            Log.e(TAG, "--------------- onClassicEV1CardDetected --------------");
//            if(mCardType == EnumCardType.EnumClassicEV1 && mIsPerformingCardOperations){
//                //Already Some Operations are happening in the same card, discard the callback
//                Log.d(TAG, "----- Already Some Operations are happening in the same card, discard the callback: " + mCardType.toString());
//                return;
//            }
//            mIsPerformingCardOperations = true;
//            mCardType = EnumCardType.EnumClassicEV1;
//
//
//            classic = objMFCEV1Card;
//				/* Insert your logic here by commenting the function call below. */
//            try {
//                classic.getReader().connect();
//               // classicEV1CardLogic();
//            } catch (Throwable t) {
//                showMessage("Unknown Error Tap Again!", 't');
//            }
//
//            mIsPerformingCardOperations = false;
//        }
//
//        @Override
//        public void onDESFireCardDetected(final IDESFireEV1 objDESFire) {
//
//            if(mCardType == EnumCardType.EnumDESFireEV1 && mIsPerformingCardOperations){
//                //Already Some Operations are happening in the same card, discard the callback
//                Log.d(TAG, "----- Already Some Operations are happening in the same card, discard the callback: " + mCardType.toString());
//                return;
//            }
//            mIsPerformingCardOperations = true;
//            mCardType = EnumCardType.EnumDESFireEV1;
//
//            mDESFire = objDESFire;
//				/* Insert your logic here by commenting the function call below. */
//            try {
//                mDESFire.getReader().close();
//                mDESFire.getReader().connect();
//                desfireCardLogic();
//            } catch (Throwable t) {
//                t.printStackTrace();
//                showMessage("Unknown Error Tap Again!", 't');
//            }
//
//            mIsPerformingCardOperations = false;
//        }
//
//        @Override
//        public void onPlusCardDetected(final IPlus objMFPlus) {
//
//            if(mCardType == EnumCardType.EnumPlus && mIsPerformingCardOperations){
//                //Already Some Operations are happening in the same card, discard the callback
//                Log.d(TAG, "----- Already Some Operations are happening in the same card, discard the callback: " + mCardType.toString());
//                return;
//            }
//            mIsPerformingCardOperations = true;
//            mCardType = EnumCardType.EnumPlus;
//
//            plus = objMFPlus;
//            try {
//                plus.getReader().connect();
//               // plusCardLogic();
//            } catch (Throwable t) {
//                t.printStackTrace();
//                showMessage("Unknown Error Tap Again!", 't');
//            }
//
//            mIsPerformingCardOperations = false;
//        }
//
//        @Override
//        public void onPlusSL1CardDetected(IPlusSL1 objPlusSL1) {
//            if(mCardType == EnumCardType.EnumPlusSL1 && mIsPerformingCardOperations){
//                //Already Some Operations are happening in the same card, discard the callback
//                Log.d(TAG, "----- Already Some Operations are happening in the same card, discard the callback: " + mCardType.toString());
//                return;
//            }
//            mIsPerformingCardOperations = true;
//            mCardType = EnumCardType.EnumPlusSL1;
//
//            plusSL1 = objPlusSL1;
//            classic = objPlusSL1; // Plus SL1 is completely compatible with
//            // Classic!!
//            try {
//                plusSL1.getReader().connect();
//              //  PlusSL1CardLogic();
//            } catch (Throwable t) {
//                showMessage("Unknown Error Tap Again!", 't');
//            }
//
//            mIsPerformingCardOperations = false;
//        }
//
//        @Override
//        public void onICodeSLIDetected(final IICodeSLI objiCodesli) {
//
//            if(mCardType == EnumCardType.EnumICodeSLI && mIsPerformingCardOperations){
//                //Already Some Operations are happening in the same card, discard the callback
//                Log.d(TAG, "----- Already Some Operations are happening in the same card, discard the callback: " + mCardType.toString());
//                return;
//            }
//            mIsPerformingCardOperations = true;
//            mCardType = EnumCardType.EnumICodeSLI;
//
//            iCodeSli = objiCodesli;
//
//            try {
//                iCodeSli.getReader().connect();
//                //iCodeSLILogic();
//            } catch (Throwable t) {
//                showMessage("Unknown Error Tap Again!", 't');
//            }
//
//            mIsPerformingCardOperations = false;
//        }
//
//        @Override
//        public void onICodeSLILDetected(final IICodeSLIL objiCodeslil) {
//            if(mCardType == EnumCardType.EnumICodeSLIL && mIsPerformingCardOperations){
//                //Already Some Operations are happening in the same card, discard the callback
//                Log.d(TAG, "----- Already Some Operations are happening in the same card, discard the callback: " + mCardType.toString());
//                return;
//            }
//
//            mIsPerformingCardOperations = true;
//            mCardType = EnumCardType.EnumICodeSLIL;
//
//            iCodeSliL = objiCodeslil;
//
//            try {
//                iCodeSliL.getReader().connect();
//               // iCodeSLIlLogic();
//            } catch (Throwable t) {
//                showMessage("Unknown Error Tap Again!", 't');
//            }
//
//            mIsPerformingCardOperations = false;
//        }
//
//        @Override
//        public void onICodeSLISDetected(final IICodeSLIS objiCodeslis) {
//
//            if(mCardType == EnumCardType.EnumICodeSLIS && mIsPerformingCardOperations){
//                //Already Some Operations are happening in the same card, discard the callback
//                Log.d(TAG, "----- Already Some Operations are happening in the same card, discard the callback: " + mCardType.toString());
//                return;
//            }
//
//            mIsPerformingCardOperations = true;
//            mCardType = EnumCardType.EnumICodeSLIS;
//
//            iCodeSliS = objiCodeslis;
//
//            try {
//                iCodeSliS.getReader().connect();
//              //  iCodeSLIsLogic();
//            } catch (Throwable t) {
//                showMessage("Unknown Error Tap Again!", 't');
//            }
//
//            mIsPerformingCardOperations = false;
//        }
//
//        @Override
//        public void onICodeSLIXDetected(final IICodeSLIX objiCodeslix) {
//            if(mCardType == EnumCardType.EnumICodeSLIX && mIsPerformingCardOperations){
//                //Already Some Operations are happening in the same card, discard the callback
//                Log.d(TAG, "----- Already Some Operations are happening in the same card, discard the callback: " + mCardType.toString());
//                return;
//            }
//            mIsPerformingCardOperations = true;
//            mCardType = EnumCardType.EnumICodeSLIX;
//
//            iCodeSliX = objiCodeslix;
//
//            try {
//                iCodeSliX.getReader().connect();
//              //  iCodeSLIxLogic();
//            } catch (Throwable t) {
//                showMessage("Unknown Error Tap Again!", 't');
//            }
//
//            mIsPerformingCardOperations = false;
//        }
//
//        @Override
//        public void onICodeSLIXLDetected(final IICodeSLIXL objiCodeslixl) {
//            if(mCardType == EnumCardType.EnumICodeSLIXL && mIsPerformingCardOperations){
//                //Already Some Operations are happening in the same card, discard the callback
//                Log.d(TAG, "----- Already Some Operations are happening in the same card, discard the callback: " + mCardType.toString());
//                return;
//            }
//
//            mIsPerformingCardOperations = true;
//            mCardType = EnumCardType.EnumICodeSLIXL;
//
//            iCodeSliXL = objiCodeslixl;
//
//            try {
//                iCodeSliXL.getReader().connect();
//               // iCodeSLIxlLogic();
//            } catch (Throwable t) {
//                showMessage("Unknown Error Tap Again!", 't');
//            }
//
//            mIsPerformingCardOperations = false;
//        }
//
//        @Override
//        public void onICodeSLIXSDetected(final IICodeSLIXS objiCodeslixs) {
//            if(mCardType == EnumCardType.EnumICodeSLIXS && mIsPerformingCardOperations){
//                //Already Some Operations are happening in the same card, discard the callback
//                Log.d(TAG, "----- Already Some Operations are happening in the same card, discard the callback: " + mCardType.toString());
//                return;
//            }
//
//            mIsPerformingCardOperations = true;
//            mCardType = EnumCardType.EnumICodeSLIXS;
//
//            iCodeSliXS = objiCodeslixs;
//
//            try {
//                iCodeSliXS.getReader().connect();
//               // iCodeSLIxsLogic();
//            } catch (Throwable t) {
//                showMessage("Unknown Error Tap Again!", 't');
//            }
//
//            mIsPerformingCardOperations = false;
//        }
//
//        @Override
//        public void onICodeSLIX2Detected(final IICodeSLIX2 objiCodeslix2) {
//            if(mCardType == EnumCardType.EnumICodeSLIX2 && mIsPerformingCardOperations){
//                //Already Some Operations are happening in the same card, discard the callback
//                Log.d(TAG, "----- Already Some Operations are happening in the same card, discard the callback: " + mCardType.toString());
//                return;
//            }
//
//            mIsPerformingCardOperations = true;
//            mCardType = EnumCardType.EnumICodeSLIX2;
//
//            iCodeSliX2 = objiCodeslix2;
//
//            try {
//                iCodeSliX2.getReader().connect();
//                //iCodeSLIx2Logic();
//            } catch (Throwable t) {
//                showMessage("Unknown Error Tap Again!", 't');
//            }
//
//
//            mIsPerformingCardOperations = false;
//
//        }
//
//        @Override
//        public void onNTag203xCardDetected(final INTag203x objnTag203x) {
//
//            if(mCardType == EnumCardType.EnumNTag203x && mIsPerformingCardOperations){
//                //Already Some Operations are happening in the same card, discard the callback
//                Log.d(TAG, "----- Already Some Operations are happening in the same card, discard the callback: " + mCardType.toString());
//                return;
//            }
//
//            mIsPerformingCardOperations = true;
//            mCardType = EnumCardType.EnumNTag203x;
//
//            try {
//                objnTag203x.getReader().connect();
//               // ntagCardLogic(objnTag203x);
//            } catch (ReaderException e) {
//
//                e.printStackTrace();
//            }
//
//            mIsPerformingCardOperations = false;
//        }
//
//        @Override
//        public void onNTag210CardDetected(final INTag210 objnTag210) {
//
//            if(mCardType == EnumCardType.EnumNTag210 && mIsPerformingCardOperations){
//                //Already Some Operations are happening in the same card, discard the callback
//                Log.d(TAG, "----- Already Some Operations are happening in the same card, discard the callback: " + mCardType.toString());
//                return;
//            }
//
//            mIsPerformingCardOperations = true;
//            mCardType = EnumCardType.EnumNTag210;
//
//            try {
//                objnTag210.getReader().connect();
//               // ntagCardLogic(objnTag210);
//            } catch (ReaderException e) {
//
//                e.printStackTrace();
//            }
//
//            mIsPerformingCardOperations = false;
//        }
//
//        @Override
//        public void onNTag213215216CardDetected(
//                final INTag213215216 objnTag213215216) {
//
//            if(mCardType == EnumCardType.EnumNTag213215216 && mIsPerformingCardOperations){
//                //Already Some Operations are happening in the same card, discard the callback
//                Log.d(TAG, "----- Already Some Operations are happening in the same card, discard the callback: " + mCardType.toString());
//                return;
//            }
//            mIsPerformingCardOperations = true;
//            mCardType = EnumCardType.EnumNTag213215216;
//
//            try {
//                objnTag213215216.getReader().connect();
//              //  ntagCardLogic(objnTag213215216);
//            } catch (ReaderException e) {
//
//                e.printStackTrace();
//            }
//
//
//            mIsPerformingCardOperations = false;
//
//        }
//
//        @Override
//        public void onNTag213F216FCardDetected(
//                final INTag213F216F objnTag213216f) {
//
//            if(mCardType == EnumCardType.EnumNTag213F216F && mIsPerformingCardOperations){
//                //Already Some Operations are happening in the same card, discard the callback
//                Log.d(TAG, "----- Already Some Operations are happening in the same card, discard the callback: " + mCardType.toString());
//                return;
//            }
//
//            mIsPerformingCardOperations = true;
//            mCardType = EnumCardType.EnumNTag213F216F;
//
//            try {
//                objnTag213216f.getReader().connect();
//              //  ntagCardLogic(objnTag213216f);
//            } catch (ReaderException e) {
//
//                e.printStackTrace();
//            }
//
//            mIsPerformingCardOperations = false;
//
//        }
//
//        @Override
//        public void onNTagI2CCardDetected(final INTagI2C objnTagI2c) {
//
//            if(mCardType == EnumCardType.EnumNTagI2C && mIsPerformingCardOperations){
//                //Already Some Operations are happening in the same card, discard the callback
//                Log.d(TAG, "----- Already Some Operations are happening in the same card, discard the callback: " + mCardType.toString());
//                return;
//            }
//
//            mIsPerformingCardOperations = false;
//            mCardType = EnumCardType.EnumNTagI2C;
//
//            try {
//                objnTagI2c.getReader().connect();
//              //  ntagCardLogic(objnTagI2c);
//            } catch (Exception e) {
//
//                e.printStackTrace();
//            }
//
//            mIsPerformingCardOperations = false;
//        }
//
//        @Override
//        public void onCardNotSupported(Tag tag) {
//          //  tv.setText(" ");
//          //  showImageSnap(R.drawable.mifare_p);
//            showMessage("Card NOT supported" + "n"+tag.toString(), 'n');
//            IsoDep ido
//        }
//
//        @Override
//        public void onNTagI2CplusCardDetected(final INTAGI2Cplus objnTagI2Cplus) {
//
//            if(mCardType == EnumCardType.EnumNTagI2CPLUS && mIsPerformingCardOperations){
//                //Already Some Operations are happening in the same card, discard the callback
//                Log.d(TAG, "----- Already Some Operations are happening in the same card, discard the callback: " + mCardType.toString());
//                return;
//            }
//
//            mIsPerformingCardOperations = false;
//            mCardType = EnumCardType.EnumNTagI2CPLUS;
//
//            try {
//                objnTagI2Cplus.getReader().connect();
//           //     ntagCardLogic(objnTagI2Cplus);
//            } catch (Exception e) {
//
//                e.printStackTrace();
//            }
//
//            mIsPerformingCardOperations = false;
//        }
//    };
//    protected void desfireCardLogic() throws SmartCardException {
//
//        //showImageSnap(R.drawable.desfire_ev1);
//        //tv.setText(" ");
//        showMessage("Card Detected : " + mDESFire.getCardDetails().cardName,
//                'n');
//
//        try {
//            mDESFire.getReader().setTimeout(2000);
//			/* Do the following only if write checkbox is selected */
//            if(bWriteAllowed) {
//                testDESFirepersonalize();
//                testDESFireauthenticate();
//                testDESFireupdatePICCMasterKey();
//                testDESFireauthenticate();
//                testDESFireupdateApplicationMasterKey();
//                testDESFireauthenticate();
//                testDESFireWrite();
//                testDESFireRead();
//            }
//            mDESFire.getReader().setTimeout(2000);
//            //showCardDetails(mDESFire.getCardDetails());
//			/* Do the following only if write checkbox is selected */
//            if(bWriteAllowed) {
//                testDESFireFormat();
//            }
//            mDESFire.getReader().close();
//        } catch (ReaderException e) {
//
//            e.printStackTrace();
//        }
//    }
}
