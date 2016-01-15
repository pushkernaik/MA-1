package utils;

import android.annotation.TargetApi;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Build;
import android.util.Log;

import com.letsappit.mineautomation.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;

/**
 * Created by radhaprasadborkar on 15/12/15.
 */
public class NFCOps {
    static byte[] mykey = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    private static final String TAG = MainActivity.class.getSimpleName();

    public void writeTag(Tag tag, String tagText) {
        MifareUltralight ultralight = MifareUltralight.get(tag);

        try {
            ultralight.connect();
            ultralight.writePage(4, "fixed".getBytes(Charset.forName("US-ASCII")));
            ultralight.writePage(5, "variable".getBytes(Charset.forName("US-ASCII")));
            ultralight.writePage(6, "ijkl".getBytes(Charset.forName("US-ASCII")));
            ultralight.writePage(7, "mnop".getBytes(Charset.forName("US-ASCII")));
        } catch (IOException e) {
            Log.e(TAG, "IOException while closing MifareUltralight...", e);
        } finally {
            try {
                ultralight.close();
            } catch (IOException e) {
                Log.e(TAG, "IOException while closing MifareUltralight...", e);
            }
        }
    }

    public static JSONObject readTag(Tag tag) throws JSONException {
        Ndef ndefTag = Ndef.get(tag);
        try {
            ndefTag.connect();
            NdefMessage ndefMessage = ndefTag.getNdefMessage();
            NdefRecord[] ndefrec = ndefMessage.getRecords();
            int length = ndefrec[0].getPayload().length;
            byte[] payload = ndefrec[0].getPayload();
            String textEncoding = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
            int languageCodeLength = payload[0] & 0077;
            String text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
            JSONObject data = new JSONObject(text);

            return data;
        } catch (FormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void write(String text, final Tag tag, String action) throws IOException, FormatException {
        NdefRecord newRec = NFCOps.createTextRecord(text, Locale.ENGLISH, false);
        final NdefMessage newNdefMessage = new NdefMessage(newRec);
        if (action.equals(NfcAdapter.ACTION_NDEF_DISCOVERED)) {
            Ndef ndefTag = Ndef.get(tag);
            try {
                ndefTag.connect();
                if(ndefTag.isConnected()) {
                    ndefTag.writeNdefMessage(newNdefMessage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (FormatException e) {
                e.printStackTrace();
            }
        } else if (action.equals(NfcAdapter.ACTION_TAG_DISCOVERED)) {
            // NdefFormatable ndefTag = NdefFormatable.get(tag);

                    IsoDep isoDep = IsoDep.get(tag);
                    if (isoDep != null) {

                        try {
                            isoDep.connect();
                            isoDep.transceive(mykey);
                            isoDep.close();
                            Log.v("NFC WRITE","tranceive complete ...max trancv length " +isoDep.getMaxTransceiveLength()+"    myKeyLength"+mykey.length);
                            if(!isoDep.isConnected())
                            {
                                NdefFormatable ndefFormatable = NdefFormatable.get(isoDep.getTag());
                                if(ndefFormatable!=null) {
                                    ndefFormatable.connect();
                                    ndefFormatable.format(newNdefMessage);
                                    Log.v("NFC WRITE", "tranceive complete ...max trancv length " + ndefFormatable.toString());
                                }
                                else
                                {
                                    Log.v("NFC WRITE","ndefformatable null");
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (FormatException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        Log.v("NFC WRITE","isodep null");

                    }





//    try {
//        ndefTag.connect();
//        NdefRecord newRec = NFCOps.createTextRecord(text, Locale.ENGLISH, false);
//        NdefMessage newNdefMessage = new NdefMessage(newRec);
//        ndefTag.format(newNdefMessage);
//    } catch (IOException e) {
//        e.printStackTrace();
//    } catch (FormatException e) {
//        e.printStackTrace();
//    }
        }

    }

    public static NdefRecord createTextRecord(String payload, Locale locale, boolean encodeInUtf8) {
        byte[] langBytes = locale.getLanguage().getBytes(Charset.forName("US-ASCII"));
        Charset utfEncoding = encodeInUtf8 ? Charset.forName("UTF-8") : Charset.forName("UTF-16");
        byte[] textBytes = payload.getBytes(utfEncoding);
        int utfBit = encodeInUtf8 ? 0 : (1 << 7);
        char status = (char) (utfBit + langBytes.length);
        byte[] data = new byte[1 + langBytes.length + textBytes.length];
        data[0] = (byte) status;
        System.arraycopy(langBytes, 0, data, 1, langBytes.length);
        System.arraycopy(textBytes, 0, data, 1 + langBytes.length, textBytes.length);
        NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN,
                NdefRecord.RTD_TEXT, new byte[0], data);
        return record;
    }


    public static JSONObject getJSONFromTag(Ndef tag) throws IOException, FormatException, JSONException {
        tag.connect();
        JSONObject tagDetails = new JSONObject(tag.getNdefMessage().toString());
        return tagDetails;
    }
}


    ///initial writting logic //


//    private void testDESFirepersonalize(Tag tag) {
//        byte[] mykey = new byte[] { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
//                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
//        byte[] appKey = new byte[] { 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
//                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
//
//        boolean res = false;
//        try {
//            NxpLogUtils.d(TAG, "testDESFirepersonalize, start");
//
//            personalize(mykey, new byte[]{0x12, 0x12, 0x12}, appKey);
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

