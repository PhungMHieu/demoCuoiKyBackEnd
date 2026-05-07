package com.example.democuoikybackend.service;

import com.example.democuoikybackend.command.Command;
import com.example.democuoikybackend.hibernate.LaborerHibernate;

public class LaborerService {
    private LaborerHibernate laborHibernate;

    public LaborerService() {
        this.laborHibernate = new LaborerHibernate();
    }

    // Hàm duy nhất trong Service
    public void executeCommand(Command cmd) {
        cmd.execute(laborHibernate);
    }
}