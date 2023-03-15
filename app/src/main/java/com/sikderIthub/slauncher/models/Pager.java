package com.sikderIthub.slauncher.models;

import java.util.ArrayList;
import java.util.List;

public class Pager {
    private List<Apps> appsList = new ArrayList<>();

    public Pager(List<Apps> appsList) {
        this.appsList = appsList;
    }


    public List<Apps> getAppsList() {
        return appsList;
    }
}
