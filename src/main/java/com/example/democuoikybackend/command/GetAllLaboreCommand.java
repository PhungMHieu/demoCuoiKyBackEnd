package com.example.democuoikybackend.command;

import com.example.democuoikybackend.hibernate.LaborerHibernate;
import com.example.democuoikybackend.model.Laborer;

import java.util.List;

public class GetAllLaboreCommand implements Command {
    private List<Laborer> resultList;

    @Override
    public void execute(LaborerHibernate hibernate) {
        this.resultList = hibernate.findAll();
    }

    public List<Laborer> getResultList() {
        return resultList;
    }
}
