package com.example.democuoikybackend.command;

import com.example.democuoikybackend.hibernate.LaborerHibernate;
import com.example.democuoikybackend.model.Laborer;

public class AddLaborerCommand implements Command {
    private Laborer laborer;

    public AddLaborerCommand(Laborer laborer) {
        this.laborer = laborer;
    }

    @Override
    public void execute(LaborerHibernate hibernate) {
        hibernate.save(laborer);
    }
}