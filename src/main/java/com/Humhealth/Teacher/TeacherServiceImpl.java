package com.Humhealth.Teacher;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {
    private TeacherDao dao;
    private PasswordEncoder passwordEncoder;

    // @Override
    // public void setTeacher(Teacher teacher){
    //     this.teacher = teacher;
    // }

    // @Override
    // public Teacher getTeacher(){
    //     return this.teacher;
    // }

    @Override
    public List<Teacher> getTeacherList(){
        return dao.getAllTeachers();
    }

    @Override
    public void updateTeacher(Long id, String firstName, String lastName, int gradeTaught, String username, String password, String role){
        //Get teacher by id and set all the variables to the inputs
        Teacher teacher = findTeacherById(id);
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        teacher.setGradeTaught(gradeTaught);
        teacher.setUsername(username);
        teacher.setPassword(password);
        teacher.setRole(role);

        //Security handling: Re-Encode password if changed for the update
        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));
        dao.update(teacher);
    }

    @Override
    public Teacher findTeacherById(Long id){
        return dao.findTeacherById(id);
    }

    @Override
    public void saveTeacher(String firstName, String lastName, int gradeTaught, String username, String password, String role){
        //Security handling: Re-Encode password if changed for the update
        Teacher teacher = new Teacher(firstName, lastName, gradeTaught, username, password, role);
        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));
        dao.save(teacher);
    }

    @Override
    public Boolean checkTeacherParams(Teacher teacher){
        return !(teacher.getGradeTaught() < 1 || teacher.getGradeTaught() > 12);
    }

    @Override
    public List<Teacher> filterTeachers(String firstName, String lastName, int grade){
        return dao.filterTeachers(firstName, lastName, grade);
    }

}
