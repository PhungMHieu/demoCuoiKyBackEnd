package com.example.democuoikybackend.command;

import com.example.democuoikybackend.hibernate.LaborerHibernate;

public interface Command {
    void execute(LaborerHibernate dao);
}