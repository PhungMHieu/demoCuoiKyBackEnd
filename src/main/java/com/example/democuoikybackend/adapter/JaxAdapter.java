package com.example.democuoikybackend.adapter;

import com.example.democuoikybackend.model.Laborer;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.StringReader;
import java.io.StringWriter;

public class JaxAdapter implements DataConverter {

    @Override
    public Laborer parse(String data) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Laborer.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (Laborer) unmarshaller.unmarshal(new StringReader(data));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String stringify(Object obj) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            StringWriter writer = new StringWriter();
            marshaller.marshal(obj, writer);
            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}