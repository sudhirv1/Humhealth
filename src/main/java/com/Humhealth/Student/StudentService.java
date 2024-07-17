package com.Humhealth.Student;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public interface StudentService {
    void saveStudent(String firstName, String lastName, String middleName, String gender, String dob, int grade, Long ssn, String email,
                            String residingStatus, String status, String formerReason, Boolean repeatingGrade, Boolean transferStudent, Long transferID,
                            String primaryContact, String primaryRelationship, Long primaryNumber, String secondaryContact, String secondaryRelationship,
                            Long secondaryNumber, String addressLine1, String addressLine2, String city, String state, String zip);
    Boolean checkStudentParams(Student student);
    void updateStudent(Long id, String firstName, String lastName, String middleName, String gender, String dob, int grade, Long ssn, String email,
                            String residingStatus, String status, String formerReason, Boolean repeatingGrade, Boolean transferStudent, Long transferID,
                            String primaryContact, String primaryRelationship, Long primaryNumber, String secondaryContact, String secondaryRelationship,
                            Long secondaryNumber, String addressLine1, String addressLine2, String city, String state, String zip);
    List<Student> getSudentList();
    Student getStudentById(Long id);
    List<Student> filterStudents(Map<String, Object> params);
    List<Student> getStudentsInGrade(int gradeIn);
    List<Student> searchFilteredStudentList(List<Student> students, String criteria, Object value);
    List<Student> searchFilteredOverallStudents(List<Student> students, String criteria, Object value);
}
