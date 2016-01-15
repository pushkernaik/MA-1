package com.letsappit.mineautomation.BO;

import java.util.Date;

/**
 * Created by radhaprasadborkar on 05/01/16.
 */
public class Truck
{
    public String getReg_no() {
        return this.reg_no;
    }

    public String getCode() {
        return this.code;
    }

    public String getTransport_cont_code() {
        return this.transport_cont_code;
    }

    public String getDriverCode() {
        return this.driverCode;
    }

    public String getGroup_code() {
        return this.group_code;
    }

    public String getCard_id() {
        return this.card_id;
    }

    public String getCat_code() {
        return this.cat_code;
    }

    public Date getReg_date() {
        return this.reg_date;
    }

    public int getTare_wt() {
        return this.tare_wt;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public Truck(String code, String reg_no, String transport_cont_code, String driverCode, String group_code, String card_id, String cat_code, Date reg_date, int tare_wt, int capacity) {

        this.code = code;
        this.reg_no = reg_no;
        this.transport_cont_code = transport_cont_code;
        this.driverCode = driverCode;
        this.group_code = group_code;
        this.card_id = card_id;
        this.cat_code = cat_code;
        this.reg_date = reg_date;
        this.tare_wt = tare_wt;
        this.capacity = capacity;
    }

    String code;
    String reg_no;
    String transport_cont_code;
    String driverCode;
    String group_code;
    String card_id;
    String cat_code;
    Date reg_date;
    int tare_wt,capacity;


}
