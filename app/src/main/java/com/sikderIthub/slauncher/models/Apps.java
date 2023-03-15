package com.sikderIthub.slauncher.models;

import android.graphics.drawable.Drawable;

public class Apps {
    private String name;
    private String packageName;
    private Drawable image;
    private boolean appInDrawer;

    public Apps(String packageName, String name, Drawable image, boolean appInDrawer) {
        this.name = name;
        this.packageName = packageName;
        this.image = image;
        this.appInDrawer = appInDrawer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public boolean isAppInDrawer() {
        return appInDrawer;
    }

    public void setAppInDrawer(boolean appInDrawer) {
        this.appInDrawer = appInDrawer;
    }
}
