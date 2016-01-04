package com.letsappit.mineautomation;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.letsappit.mineautomation.Master.Master;
import com.letsappit.mineautomation.data.MADbHelper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {
    private NfcAdapter mAdapter;
    private PendingIntent mPendingIntent;
    private IntentFilter[] mFilters;
    private String[][] mTechLists;
    private static TextView mText;
    private int mCount = 0;

    private static final String TAG = MainActivity.class.getSimpleName();
    private Button master;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        MADbHelper db = new MADbHelper(this);
        db.getWritableDatabase();
        master = (Button) findViewById(R.id.master);

        setSupportActionBar(toolbar);
        mAdapter = NfcAdapter.getDefaultAdapter(this);
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        try {
            ndef.addDataType("*/*");
            Log.i("NDEF","data added to filter");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("fail", e);
        }
        mFilters = new IntentFilter[] {
                ndef
        };
        mTechLists = new String[][] { new String[] { NfcF.class.getName() } };

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        ///mText = (TextView) findViewById(R.id.tagText);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.i("NDEF", "onresume");
        if (mAdapter != null) {
            mAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
            Log.i("NDEF", "enable forground dispatch");
        }
    }
    @Override
    public void onNewIntent(Intent intent) {
        String tagData = "Discovered tag with intent: " + intent;
        Log.i("Foreground dispatch", "Discovered tag with intent: " + intent);
        Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        if(tagFromIntent!=null) {
            tagData =""+ tagFromIntent.getId()+
                    tagFromIntent.getTechList()+
                    tagFromIntent.toString();
            Log.i("Foreground dispatch", "Discovered tag with intent: " + tagData);
        }
        MifareClassic miFareClassic = MifareClassic.get(tagFromIntent);
        //NdefFormatable NDEF
        if(miFareClassic!=null) {

            mText.setText(readTag(miFareClassic) + " technology : " + tagFromIntent.getId());
        }






//        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
//        NdefMessage[] msgs;
//        if (rawMsgs != null) {
//            msgs = new NdefMessage[rawMsgs.length];
//            for (int i = 0; i < rawMsgs.length; i++) {
//                msgs[i] = (NdefMessage) rawMsgs[i];
//            }
//        } else {
//            // Unknown tag type
//            byte[] empty = new byte[0];
//            byte[] id = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
//            Parcelable tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
//            byte[] payload = "hello".getBytes();
//            NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, id, payload);
//            NdefMessage msg = new NdefMessage(new NdefRecord[] { record });
//           // msgs = new NdefMessage[] { msg };
//            Tag tag1 = (Tag)tag;
//            try {
//                write(msg,tag1);
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (FormatException e) {
//                e.printStackTrace();
//            }
//        }



    }
    void processIntent(Intent intent) {

        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        // only one message sent during the beam
        NdefMessage msg = (NdefMessage) rawMsgs[0];
        // record 0 contains the MIME type, record 1 is the AAR, if present
        mText.setText(new String(msg.getRecords()[0].getPayload()));
    }
//    public static void writeTag(final Tag tag, String tagText) {
//        final NfcA nfcATag = NfcA.get(tag);
//
//        final byte[] send = Encoding.getByteFromString(tagText);
//
//        final byte[] arrByt = new byte[7];
//        arrByt[0] = 0x02; //Command Flag 0x02 works fine
//        arrByt[1] = 0x21;
//        arrByt[2] = 0x06;
//        arrByt[3] = 0x00;
//        arrByt[4] = 0x00;
//        arrByt[5] = 0x00;
//        arrByt[6] = 0x00;
//
//        try {
//            nfcATag.connect();
//
//            Log.e(TAG, "trancive length :" + nfcATag.getMaxTransceiveLength() + "send message length : " + arrByt.length);
//           new Thread(new Runnable() {
//               @Override
//               public void run() {
//                   try {
//                       byte[] response = nfcATag.transceive(arrByt);
//
//                       nfcATag.close();
//                       mText.setText("Discovered tag " + readTag(nfcATag));
//                       Log.i(TAG,"response : " + response + " response String : " +Encoding.getStringFromBytes(response));
//                   } catch (IOException e) {
//                       e.printStackTrace();
//                       Log.e(TAG, "IOException while Tranceive....", e);
//                   }
//               }
//           }).start();
//
////            nfcATag.a(4, "abcd".getBytes(Charset.forName("US-ASCII")));
////            nfcATag.writePage(5, "efgh".getBytes(Charset.forName("US-ASCII")));
////            nfcATag.writePage(6, "ijkl".getBytes(Charset.forName("US-ASCII")));
////            nfcATag.writePage(7, "mnop".getBytes(Charset.forName("US-ASCII")));
//        } catch (IOException e) {
//            Log.e(TAG, "IOException while closing MifareUltralight...", e);
//        }  finally {
////            try {
////
////                nfcATag.close();
////
////            } catch (IOException e) {
////                Log.e(TAG, "IOException while closing MifareUltralight...", e);
////            }
////            catch (NullPointerException e)
////            {
////                Log.e(TAG, "Null pointer exception...", e);
////            }
//        }
//    }

    private NdefRecord createRecord(String text) throws UnsupportedEncodingException {

        //create the message in according with the standard
        String lang = "en";
        byte[] textBytes = text.getBytes("US-ASCII");
        byte[] langBytes = lang.getBytes("US-ASCII");
        int langLength = langBytes.length;
        int textLength = textBytes.length;

        byte[] payload = new byte[1 + langLength + textLength];
        payload[0] = (byte) langLength;

        // copy langbytes and textbytes into payload
        System.arraycopy(langBytes, 0, payload, 1, langLength);
        System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);

        NdefRecord recordNFC = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload);
        return recordNFC;
    }
    private void write(NdefMessage message, Tag tag) throws IOException, FormatException {

        Ndef ndef = Ndef.get(tag);
        ndef.connect();
        ndef.writeNdefMessage(message);
        ndef.close();
    }
    public static String readTag( MifareClassic nfcATag) {

        try {
            if(nfcATag.isConnected()) {

                int s = nfcATag.getSectorCount();
               int b =  nfcATag.getBlockCount();
                byte[] a = nfcATag.readBlock(1);

               String atqa = new String(a, Charset.forName("US-ASCII"));
                return "sector count = " + s + "\nblock count = " + b+"\nblock 2 = "+atqa;
            }
            else
            {
                nfcATag.connect();
                int s = nfcATag.getSectorCount();
                int b =  nfcATag.getBlockCount();
                byte[] a = nfcATag.readBlock(1);

                String atqa = new String(a, Charset.forName("US-ASCII"));
                return "sector count = " + s + "\nblock count = " + b+"\nblock 2 = "+atqa;
            }

        } catch (IOException e) {
            Log.e(TAG, "IOException while writing MifareUltralight message...", e);
        } finally {
            if (nfcATag != null) {
                try {
                    nfcATag.close();
                }
                catch (IOException e) {
                    Log.e(TAG, "Error closing tag...", e);
                }
            }
        }
        return null;
    }
    @Override
    public void onPause() {
        super.onPause();
        if (mAdapter != null) mAdapter.disableForegroundDispatch(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void masterClicked(View v)
    {
        startActivity(new Intent(this,Master.class));
    }
}
