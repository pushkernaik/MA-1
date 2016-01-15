package com.letsappit.mineautomation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.letsappit.mineautomation.BO.GridMenuItem;
import com.letsappit.mineautomation.Master.Master;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static TextView mText;
    private int mCount = 0,SPAN_COUNT=2;
    private GridLayoutManager menuLayoutManager;
    ArrayList<GridMenuItem> mainMenu;
    private static final String TAG = MainActivity.class.getSimpleName();
    private Button master;
    private RecyclerView menuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initialize();
        createMenuItems();
        populateList();

    }

    private void createMenuItems() {
        GridMenuItem item1 = new GridMenuItem(R.drawable.load,R.color.bgLoading,"LOADING",Master.class);
        GridMenuItem item2 = new GridMenuItem(R.drawable.unload,R.color.bgUnloading,"UNLOADING",Master.class);
        //GridMenuItem item3 = new GridMenuItem(R.drawable.ic_action_wb,R.color.bgWeighBridge,"WEIGHBRIDGE",Master.class);
        GridMenuItem item4 = new GridMenuItem(R.drawable.issue_card,R.color.bgWrite,"ISSUE CARD",IssueTruckTag.class);
        GridMenuItem item5 = new GridMenuItem(R.drawable.read_card,R.color.bgRead,"READ CARD",ReadCard.class);
        GridMenuItem item6 = new GridMenuItem(R.drawable.master,R.color.bgSettings,"MASTER",Master.class);
        GridMenuItem item7 = new GridMenuItem(R.drawable.settings,R.color.bgSettings,"SETTINGS",Master.class);
        mainMenu = new ArrayList<>();
        mainMenu.add(item1);
        mainMenu.add(item2);
      //  mainMenu.add(item3);
        mainMenu.add(item4);
        mainMenu.add(item5);
        mainMenu.add(item6);
        mainMenu.add(item7);


    }

    public void initialize()
    {
        master = (Button) findViewById(R.id.master);
        menuList = (RecyclerView) findViewById(R.id.menu_recycler_view);

        menuLayoutManager = new GridLayoutManager(this,SPAN_COUNT, GridLayoutManager.VERTICAL,false);
        menuList.setLayoutManager(menuLayoutManager);

    }
    public void populateList()
    {
        MenuAdapter adapter = new MenuAdapter(mainMenu,R.layout.menu_list_item,this);

        menuList.setAdapter(adapter);
    }

    @Override
    public void onResume() {
       super.onResume();
//        Log.i("NDEF", "onresume");
//        if (mAdapter != null) {
//            mAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
//            Log.i("NDEF", "enable forground dispatch");
//        }
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


    @Override
    public void onPause() {
        super.onPause();
        //if (mAdapter != null) mAdapter.disableForegroundDispatch(this);
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

    public void issueCard(View v)
    {
        startActivity(new Intent(this,IssueTruckTag.class));
    }
    public void readCard(View v)
    {
        startActivity(new Intent(this,ReadCard.class));
    }
}
