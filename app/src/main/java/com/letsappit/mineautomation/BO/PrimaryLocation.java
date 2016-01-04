package com.letsappit.mineautomation.BO;

/**
 * Created by radhaprasadborkar on 24/12/15.
 */
public class PrimaryLocation {
    String code,description;
    String datetime;

    public PrimaryLocation(String code, String description, String datetime) {
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
