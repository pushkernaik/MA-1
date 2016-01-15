package com.letsappit.mineautomation.BO;


import java.util.Date;

/**
 * Created by radhaprasadborkar on 24/12/15.
 */
public class PrimaryLocation extends Location{


    public PrimaryLocation(String code, String description, Date datetime) {
        super(code, description, datetime);
    }

    public String getCode() {
        return super.getCode();
    }

    public String getDescription() {
        return super.getDescription();
    }

    public Date getDatetime() {
        return super.getDatetime();
    }
}
