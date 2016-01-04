package com.letsappit.mineautomation.BO;

/**
 * Created by radhaprasadborkar on 24/12/15.
 */
public class Location {
    String code,description;
    String datetime;

    public Location(String code, String description, String datetime) {
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

    public String getDatetime() {
        return this.datetime;
    }
}
