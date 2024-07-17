package com.Humhealth.Holiday;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jakarta.persistence.EntityManager;

@Repository
@Transactional
public class HolidayDaoImpl implements HolidayDao {
    private EntityManager entityManager;

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void save(Holiday holiday) {
        getCurrentSession().save(holiday);
    }

    @Override
    public void update(Holiday holiday) {
        getCurrentSession().update(holiday);
    }

    @Override
    public void delete(Long id) {
        Holiday holiday = entityManager.find(Holiday.class, id);
        entityManager.remove(holiday);
        entityManager.flush();
        entityManager.clear();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Holiday> listHolidays() {
        return getCurrentSession().createQuery("from Holiday").list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<LocalDate> getHolidayDates(){
        List<LocalDate> tempL = getCurrentSession().createQuery("SELECT date from Holiday").list();
        return new HashSet<>(tempL);
    }
}