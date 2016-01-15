package com.letsappit.mineautomation.BO;

/**
 * Created by radhaprasadborkar on 28/12/15.
 */
public class ListItem  {
    // List Item for common listing such as tables and rows where items are displayed as Code and Description
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
