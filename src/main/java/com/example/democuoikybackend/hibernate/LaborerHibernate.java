package com.example.democuoikybackend.hibernate;

import com.example.democuoikybackend.model.Laborer;
import com.example.democuoikybackend.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class LaborerHibernate {
    public void save(Laborer laborer) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(laborer); // Dùng merge để lo cả Thêm và Sửa
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
        }
    }

    public void delete(Integer id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Laborer laborer = session.get(Laborer.class, id);
            if (laborer != null) session.remove(laborer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
        }
    }

    public List<Laborer> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Use the fully qualified class name to avoid IDE/compile resolution issues
            String hql = "from " + Laborer.class.getName();
            return session.createQuery(hql, Laborer.class).list();
        }
    }


}