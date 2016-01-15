package utils;

import com.letsappit.mineautomation.BO.Truck;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by radhaprasadborkar on 17/12/15.
 */
public class Util {
    //public static String characterSet =
    static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static String getFormatedCurrentDate() {
        Date date = new Date();

        return dateFormat.format(date);
    }

    public static String getFormatedDate(Date date) {


        return dateFormat.format(date);
    }

    public static Date getDateFromString(String rawDate) throws ParseException {
        return dateFormat.parse(rawDate);
    }



    public static JSONObject getBaseTruckTagString(Truck truckDetails, boolean wc)throws JSONException
    {

        JSONObject tagObject = new JSONObject();
        tagObject.put("c_t",1);
        tagObject.put("t",getTruckObject(truckDetails));
        tagObject.put("l",getLoadingObject());
        tagObject.put("ul",getUnloadingObject());
        tagObject.put("msr",getMeasures());
        tagObject.put("ts",getTripSummary());
        tagObject.put("wc",false);
        tagObject.put("prmt","");
        return tagObject;

    }

public static JSONObject getTruckObject(Truck truckDetails) throws JSONException {
            JSONObject truck = new JSONObject();
            truck.put("t_c",truckDetails.getCode());
            truck.put("r_n",truckDetails.getReg_no());
            truck.put("t_w",truckDetails.getTare_wt());
            truck.put("d_c",truckDetails.getDriverCode());
            truck.put("c_c",truckDetails.getTransport_cont_code());
            truck.put("g_c",truckDetails.getGroup_code());
            truck.put("r_d",truckDetails.getReg_date());
            return truck;

        }
    public static JSONObject getLoadingObject() throws JSONException {
        JSONObject load = new JSONObject();
        load.put("m_c_c","");
        load.put("l_d","");
        load.put("o_c","");
        load.put("m_c","");
        load.put("a_c","");
        load.put("s_p_l","");
        load.put("s_s_l","");
        load.put("s_t_l","");
        load.put("pe_p_l","");
        load.put("pe_s_l","");
        load.put("pe_t_l","");
        load.put("m_h_c","");
        load.put("u_c","");
        return load;

    }
    public static JSONObject getUnloadingObject() throws JSONException {
        JSONObject unLoad = new JSONObject();
        unLoad.put("u_d","");
        unLoad.put("e_p_l","");
        unLoad.put("e_s_l","");
        unLoad.put("e_t_l","");
        unLoad.put("m_h_c","");
        unLoad.put("u_c","");
        return unLoad;

    }

    public static JSONArray getMeasures() throws  JSONException
    {
        JSONArray  measures = new JSONArray();
        return measures;
    }

    public static JSONObject createMeasureObject() throws  JSONException
    {
        JSONObject measure = new JSONObject();
        measure.put("w_c","");
        measure.put("g_w","");
        measure.put("r_t","");
        return measure;

    }
    public static JSONArray getTripSummary() throws  JSONException
    {
        JSONArray  tripSummary = new JSONArray();

        return tripSummary;
    }
    public static JSONObject createTripSummaryObject(int rom,int proc,int rej,String date) throws  JSONException
    {
        JSONObject tripSummary = new JSONObject();
        tripSummary.put("ro_tc",rom);
        tripSummary.put("p_tc",proc);
        tripSummary.put("r_tc",rej);
        tripSummary.put("r_t",date);

        return tripSummary;

    }


}
