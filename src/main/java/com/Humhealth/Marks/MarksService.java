package com.Humhealth.Marks;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Humhealth.Student.Student;

@Service
public interface MarksService {
    void saveMarks(Long studentId, int year, int quarter, double tamil, double english, double maths, double science, double socialScience);
    Marks findMarksById(Long id);
    List<Marks> findAllMarks();
    void updateMarks(Long id, int year, int quarter, double tamil, double english, double maths, double science, double socialScience);
    void deleteMarks(Marks marks);
    List<Marks> findMarksByStudentId(Long studentId);
    Marks findMarksByYearQuarterStudent(int yearIn, int quarterIn, Long studentId);
    List<MarksResponse> listMarkEntrys(List<Student> studentList, int year, List<String> periods);
    MarksResponse consolidateMarksObject(int year, List<Integer> quarters, Long studentId);
    List<MarksResponse> computeMarksReport(String criteria, Object value, List<Student> students, int year, List<String> periods, String subjectTopper, Boolean overallTopper, String marksResult);
    List<Student> marksReportAndPrint(int year, List<Integer> quarters, List<Student> students, int startMonth, int endMonth, String subjectTopper);
    double getSpecificMark(Marks mark, String subject);
    Boolean checkFailYear(int year, Long studentId);
    double yearTotalMarkPercent(int yearIn, Long studentId);
}

