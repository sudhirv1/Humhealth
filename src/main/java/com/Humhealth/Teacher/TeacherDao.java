package com.Humhealth.Teacher;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface TeacherDao {
    void save(Teacher teacher);
    void update(Teacher teacher);
    void delete(Teacher teacher);
    List<Teacher> getAllTeachers();
    List<Teacher> filterTeachers(String firstName, String lastName, int grade);
    Teacher findTeacherById(Long id);
}
