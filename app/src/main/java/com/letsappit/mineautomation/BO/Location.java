package com.letsappit.mineautomation.BO;

import java.util.Date;

/**
 * Created by radhaprasadborkar on 04/01/16.
 */
public class Location {
    private String code,description;
    private Date datetime;

    public Location(String code, String description, Date datetime) {
        this.code = code;
        this.description = description;
        this.datetime = datetime;
    }
    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    public Date getDatetime() {
        return this.datetime;
    }

}
