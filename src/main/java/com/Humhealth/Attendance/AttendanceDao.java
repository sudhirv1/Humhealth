package com.Humhealth.Attendance;

import com.Humhealth.Student.Student;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
public interface AttendanceDao {
    void save(Attendance attendance);
    Attendance findById(Long id);
    List<Attendance> getAll();
    List<Attendance> findByStudentId(Long studentId);
    void update(Attendance attendance);
    void delete(Attendance attendance);
    Boolean showedUpToday(Long studentId, LocalDate currDate);
    List<Attendance> filterAttendances(Map<String, Object> params);
    int getSickDays(int startMonth, int endMonth, int year, Student student);
    int getOfficialDays(int startMonth, int endMonth, int year, Student student);
    int getPresentDays(int startMonth, int endMonth, int year, Student student);
}