package com.Humhealth.Admin;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public class AdminDaoImpl implements AdminDao{
    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    @Transactional
    public void saveAdmin(Admin admin){
        getCurrentSession().save(admin);
    }

    @Override
    public Admin findAdminById(Long id) {
        return getCurrentSession().get(Admin.class, id);
    }

    @Override
    public void updateAdmin(Admin admin) {
        getCurrentSession().update(admin);
    }
}
