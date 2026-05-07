package com.example.democuoikybackend.adapter;

import com.example.democuoikybackend.model.Laborer;
import com.google.gson.Gson;

public class GSonAdapter implements DataConverter {
    private Gson gsonLib;

    public GSonAdapter() {
        this.gsonLib = new Gson();
    }

    @Override
    public Laborer parse(String data) {
        return gsonLib.fromJson(data, Laborer.class);
    }

    @Override
    public String stringify(Object obj) {
        return gsonLib.toJson(obj);
    }
}