package com.Humhealth.Teacher;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface TeacherService {
    void updateTeacher(Long id, String firstName, String lastName, int gradeTaught, String username, String password, String role);

    void saveTeacher(String firstName, String lastName, int gradeTaught, String username, String password, String role);

    Boolean checkTeacherParams(Teacher teacher);

    // void setTeacher(Teacher teacher);
    // Teacher getTeacher();

    Teacher findTeacherById(Long id);

    List<Teacher> getTeacherList();

    List<Teacher> filterTeachers(String firstName, String lastNamem, int grade);
}
