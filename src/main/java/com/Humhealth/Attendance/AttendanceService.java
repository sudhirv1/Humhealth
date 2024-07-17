package com.Humhealth.Attendance;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.Humhealth.Student.Student;

public interface AttendanceService {
    void saveAttendance(Boolean present, Boolean sickLeave, Boolean officialLeave, LocalDate date, Long studentId);
    Attendance findAttendanceById(Long id); //Pulls an individual attendance entry by using an attendance entry ID
    List<Attendance> findAllAttendances();
    List<Attendance> findAttendancesByStudentId(Long studentId); // Uses a Student's student table ID to retrieve a list of its attendance objects
    void updateAttendance(Long id, Boolean present, Boolean sickLeave, Boolean officialLeave, LocalDate date);
    void deleteAttendance(Long id);
    Boolean showedUpToday(Long studentId, LocalDate currDate);
    void markAttendance(Long studentId, LocalDate attendanceDate, Boolean present, Boolean sickLeave, Boolean officialLeave);
    List<Attendance> filter(Map<String, Object> params, String criteria, Object value);
    String isCompliantRetStr(int presentDays, int totalDays);
    List<AttendanceResponse> computeAttendanceStats(String criteria, Object value, List<String> periods, List<Integer> years, List<Student> stageOneStudents, Boolean halfLeave,
    String comparison, int sickTarget, int officialLeaveTarget);
    List<AttendanceResponse> attStatCheckAndAdd( List<Integer> years, List<Student> stageOneStudents, int startMonth, int endMonth, int halfTimeLim, Boolean halfLeave, String comparison, int sickTarget, int officialLeaveTarget);
    double attendancePercentage(int startMonth, int endMonth, int year, Student student);
}

