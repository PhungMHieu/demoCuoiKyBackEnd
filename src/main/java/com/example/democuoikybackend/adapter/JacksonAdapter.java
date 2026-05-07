package com.example.democuoikybackend.adapter;

import com.example.democuoikybackend.model.Laborer;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonAdapter implements DataConverter {
    private ObjectMapper mapper;

    public JacksonAdapter() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public Laborer parse(String data) {
        try {
            // Chuyển chuỗi JSON sang đối tượng Laborer
            return mapper.readValue(data, Laborer.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String stringify(Object obj) {
        try {
            // Chuyển đối tượng (List hoặc Laborer) sang chuỗi JSON
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }
}