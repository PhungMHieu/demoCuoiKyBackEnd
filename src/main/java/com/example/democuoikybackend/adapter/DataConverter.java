package com.example.democuoikybackend.adapter;

import com.example.democuoikybackend.model.Laborer;

public interface DataConverter {
    Laborer parse(String data);
    String stringify(Object obj);
}