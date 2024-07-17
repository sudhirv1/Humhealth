package com.Humhealth.Marks;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class MarksDaoImpl implements MarksDao{
    @Autowired
    private SessionFactory sessionFactory;
    private Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void save(Marks marks) {
        getCurrentSession().save(marks);
    }

    @Override
    public Marks findById(Long id) {
        return getCurrentSession().get(Marks.class, id);
    }

    @Override
    public List<Marks> findMarksByStudentId(Long studentId){
        return getCurrentSession()
                .createQuery("from Marks where student.id = :studentId", Marks.class)
                .setParameter("studentId", studentId)
                .list();
    }

    @Override
    public Marks findMarksByYearQuarterStudent(int yearIn, int quarterIn, Long studentId){
        return getCurrentSession()
                .createQuery("from Marks where student.id = :studentId AND student.year = :yearIn AND student.quarter = :quarterIn"
                , Marks.class)
                .setParameter("studentId", studentId)
                .setParameter("yearIn", yearIn)
                .setParameter("quarterIn", quarterIn)
                .getSingleResult();
    }

    @Override
    public List<Marks> getAll() {
        return getCurrentSession().createQuery("from Marks", Marks.class).list();
    }

    @Override
    public void update(Marks marks) {
        getCurrentSession().update(marks);
    }

    @Override
    public void delete(Marks marks) {
        getCurrentSession().delete(marks);
    }
    
}
