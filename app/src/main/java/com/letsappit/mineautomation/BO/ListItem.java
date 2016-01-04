package com.letsappit.mineautomation.BO;

/**
 * Created by radhaprasadborkar on 28/12/15.
 */
public class ListItem  {
    String title;

    public String getDescription() {
        return this.description;
    }

    public String getTitle() {
        return this.title;
    }

    String description;

    public ListItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

}
