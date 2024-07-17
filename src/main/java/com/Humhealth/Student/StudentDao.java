package com.Humhealth.Student;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public interface StudentDao {
    void save(Student student);
    void update(Student student);
    List<Student> getAllStudents();
    Student getStudentById(Long id);
    List<Student> filterStudents(Map<String, Object> params);
    List<Student> getStudentsInGrade(int gradeIn);
}
