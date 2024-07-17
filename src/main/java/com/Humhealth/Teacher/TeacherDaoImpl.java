package com.Humhealth.Teacher;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TeacherDaoImpl implements TeacherDao{
    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    @Transactional
    public void save(Teacher teacher) {
        sessionFactory.getCurrentSession().saveOrUpdate(teacher);
    }

    @Override
    @Transactional
    public void update(Teacher teacher){
        sessionFactory.getCurrentSession().update(teacher);
    }

    @Override
    @Transactional
    public void delete(Teacher teacher) {
        sessionFactory.getCurrentSession().delete(teacher);
    }

    @Override
    @Transactional
    public Teacher findTeacherById(Long id){
        return sessionFactory.getCurrentSession().get(Teacher.class, id);
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<Teacher> getAllTeachers(){
        return getCurrentSession().createQuery("from Teacher").list();
    }

    // Filtering function:
    // My thought porcess here is that for every field except the Date Of Birth(dob), the fields are Strings(even a phone # we treat like a string)
    // so we should be looking for equality for every field except for DOB.
    // Then for DOB, we are going to look for those born on or before the given date.
    @Override
    @SuppressWarnings({"unchecked", "deprecation"})
    public List<Teacher> filterTeachers(String firstName, String lastName, int grade){
        Session session = getCurrentSession();
        Criteria cr = session.createCriteria(Teacher.class);

        if (firstName != null && !firstName.isEmpty()) {
            cr.add(Restrictions.eq("firstName", firstName));
        }
        if (lastName != null && !lastName.isEmpty()) {
            cr.add(Restrictions.eq("lastName", lastName));
        }
        if (grade > 0 && grade <= 12 && (""+grade).length() > 0) {
            cr.add(Restrictions.eq("grade", grade));
        }

        List<Teacher> ans = cr.list();

        return ans;
    }
}
