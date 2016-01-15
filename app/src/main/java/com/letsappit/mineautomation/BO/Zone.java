package com.letsappit.mineautomation.BO;


import java.util.Date;

/**
 * Created by radhaprasadborkar on 02/01/16.
 */
public class Zone extends Location{



    PrimaryLocation primaryLocation;

    public Zone(String code, String description, Date datetime,PrimaryLocation primaryLocation) {
        super(code, description, datetime);
       this.primaryLocation=primaryLocation;

    }

    public PrimaryLocation getPrimaryLocation() {
        return this.primaryLocation;
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
