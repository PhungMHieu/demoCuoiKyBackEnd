package com.example.democuoikybackend.command;

import com.example.democuoikybackend.hibernate.LaborerHibernate;

public class DeleteLaborerCommand implements Command {
    private Integer id;

    public DeleteLaborerCommand(Integer id) {
        this.id = id;
    }

    @Override
    public void execute(LaborerHibernate hibernate) {
        hibernate.delete(id);
    }
}