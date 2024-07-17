package com.Humhealth.Attendance;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.Humhealth.Student.Student;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class AttendanceDaoImpl implements AttendanceDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void save(Attendance attendance) {
        getCurrentSession().saveOrUpdate(attendance);
    }

    @Override
    public Attendance findById(Long id) {
        return getCurrentSession().get(Attendance.class, id);
    }

    @Override
    public List<Attendance> getAll() {
        return getCurrentSession().createQuery("from Attendance", Attendance.class).list();
    }

    @Override
    public List<Attendance> findByStudentId(Long studentId) {
        return getCurrentSession()
                .createQuery("from Attendance where student.id = :studentId", Attendance.class)
                .setParameter("studentId", studentId)
                .list();
    }

    @Override
    public Boolean showedUpToday(Long studentId, LocalDate currDate) {
        return getCurrentSession()
                .createQuery("from Attendance where student.id = :studentId AND where date = :currDate", Attendance.class)
                .setParameter("studentId", studentId)
                .setParameter("date", currDate).getSingleResult().getPresent();
    }

    @Override
    public void update(Attendance attendance) {
        getCurrentSession().update(attendance);
    }

    @Override
    public void delete(Attendance attendance) {
        getCurrentSession().delete(attendance);
    }

    @Override
    public List<Attendance> filterAttendances(Map<String, Object> params) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Attendance> cq = cb.createQuery(Attendance.class);
        Root<Attendance> attendanceRoot = cq.from(Attendance.class);

        List<Predicate> predicates = new ArrayList<>();
        params.forEach((key, value) -> {
            switch (key) {
                case "date" -> predicates.add(cb.equal(attendanceRoot.get("date"), value));
                case "present" -> predicates.add(cb.equal(attendanceRoot.get("present"), value));
                case "officialLeave" -> predicates.add(cb.equal(attendanceRoot.get("officialLeave"), value));
                case "sickLeave" -> predicates.add(cb.equal(attendanceRoot.get("sickLeave"), value));
                case "Student.status" -> predicates.add(cb.equal(attendanceRoot.get("Student.status"), value));
                case "Student.grade" -> predicates.add(cb.equal(attendanceRoot.get("Student.grade"), value));
            }
        });

        cq.where(predicates.toArray(Predicate[]::new));
        return session.createQuery(cq).getResultList();
    }

    @Override
    public int getSickDays(int startMonth, int endMonth, int year, Student student) {
        LocalDate startDate = LocalDate.of(year, startMonth, 1);
        LocalDate endDate = LocalDate.of(year, endMonth, 1).with(TemporalAdjusters.lastDayOfMonth());

    return getCurrentSession()
            .createQuery("from Attendance where student.id = :studentId AND sickLeave = true AND date >= :startDate AND date <= :endDate", Attendance.class)
            .setParameter("studentId", student.getId())
            .setParameter("startDate", startDate)
            .setParameter("endDate", endDate)
            .list().size();
    }

    @Override
    public int getOfficialDays(int startMonth, int endMonth, int year, Student student) {
        LocalDate startDate = LocalDate.of(year, startMonth, 1);
        LocalDate endDate = LocalDate.of(year, endMonth, 1).with(TemporalAdjusters.lastDayOfMonth());

        return getCurrentSession()
                .createQuery("from Attendance where student.id = :studentId AND officialLeave = true AND date >= :startDate AND date <= :endDate", Attendance.class)
                .setParameter("studentId", student.getId())
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .list().size();
    }

    @Override
    public int getPresentDays(int startMonth, int endMonth, int year, Student student){
        LocalDate startDate = LocalDate.of(year, startMonth, 1);
        LocalDate endDate = LocalDate.of(year, endMonth, 1).with(TemporalAdjusters.lastDayOfMonth());

        return getCurrentSession()
                .createQuery("from Attendance where student.id = :studentId AND present = true AND date >= :startDate AND date <= :endDate", Attendance.class)
                .setParameter("studentId", student.getId())
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .list().size();
    }
}

