package com.Humhealth.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


@Repository
public class StudentDaoImpl implements StudentDao{
    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void save(Student student){
        getCurrentSession().save(student);
    }

    @Override
    public void update(Student student){
        getCurrentSession().update(student);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Student> getAllStudents(){
        return getCurrentSession().createQuery("from Student").list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Student> getStudentsInGrade(int gradeIn){
        return getCurrentSession().createQuery("from Student where student.grade = :gradeIn")
        .setParameter("gradeIn", gradeIn)
        .list();
    }

    @Override
    public Student getStudentById(Long id){
        return sessionFactory.getCurrentSession().get(Student.class, id);
    }

    @Override
    public List<Student> filterStudents(Map<String, Object> params) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Student> cq = cb.createQuery(Student.class);
        Root<Student> studentRoot = cq.from(Student.class);

        List<Predicate> predicates = new ArrayList<>();

        params.forEach((key, value) -> {
            switch (key) {
                case "firstName":
                    predicates.add(cb.like(studentRoot.get("firstName"), "%" + value + "%"));
                    break;
                case "lastName":
                    predicates.add(cb.like(studentRoot.get("lastName"), "%" + value + "%"));
                    break;
                case "gender":
                    predicates.add(cb.equal(studentRoot.get("gender"), value));
                    break;
                case "dob":
                    predicates.add(cb.equal(studentRoot.get("dob"), value));
                    break;
                case "grade":
                    predicates.add(cb.equal(studentRoot.get("grade"), value));
                    break;
                case "ssn":
                    predicates.add(cb.equal(studentRoot.get("ssn"), value));
                    break;
                case "email":
                    predicates.add(cb.equal(studentRoot.get("email"), value));
                    break;
                case "residingStatus":
                    predicates.add(cb.equal(studentRoot.get("residingStatus"), value));
                    break;
                case "status":
                    predicates.add(cb.equal(studentRoot.get("status"), value));
                    break;
                case "formerReason":
                    predicates.add(cb.equal(studentRoot.get("formerReason"), value));
                    break;
                case "repeatingGrade":
                    predicates.add(cb.equal(studentRoot.get("repeatingGrade"), value));
                    break;
                case "transferStudent":
                    predicates.add(cb.equal(studentRoot.get("transferStudent"), value));
                    break;
                case "transferID":
                    predicates.add(cb.equal(studentRoot.get("transferID"), value));
                    break;
                case "primaryContact":
                    predicates.add(cb.equal(studentRoot.get("primaryContact"), value));
                    break;
                case "primaryRelationship":
                    predicates.add(cb.equal(studentRoot.get("primaryRelations"), value));
                    break;
                case "primaryNumber":
                    predicates.add(cb.equal(studentRoot.get("primaryNumber"), value));
                    break;
                case "secondaryContact":
                    predicates.add(cb.equal(studentRoot.get("secondaryContact"), value));
                    break;
                case "secondaryRelationship":
                    predicates.add(cb.equal(studentRoot.get("secondaryRelationship"), value));
                    break;
                case "secondaryNumber":
                    predicates.add(cb.equal(studentRoot.get("secondaryNumber"), value));
                    break;
                case "addressLine1":
                    predicates.add(cb.equal(studentRoot.get("addressLine1"), value));
                    break;
                case "addressLine2":
                    predicates.add(cb.equal(studentRoot.get("addressLine2"), value));
                    break;
                case "city":
                    predicates.add(cb.equal(studentRoot.get("city"), value));
                    break;
                case "state":
                    predicates.add(cb.equal(studentRoot.get("state"), value));
                case "zip":
                    predicates.add(cb.equal(studentRoot.get("zip"), key));
                    break;
            }
        });

        cq.where(predicates.toArray(Predicate[]::new)); //Applying our list of filters to cq
        return session.createQuery(cq).getResultList();
    }
}
