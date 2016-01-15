package com.letsappit.mineautomation.BO;

/**
 * Created by radhaprasadborkar on 09/01/16.
 */
public class GridMenuItem {
    int resId,colorId;
    String name;
    Class activity;

    public Class getActivity() {
        return this.activity;
    }

    public GridMenuItem(int resId, int colorId, String name, Class activity) {
        this.resId = resId;
        this.colorId = colorId;
        this.name = name;
        this.activity = activity;

    }

    public int getColorId() {
        return this.colorId;
    }

    public String getName() {
        return this.name;
    }

    public int getResId() {

        return this.resId;
    }
}
