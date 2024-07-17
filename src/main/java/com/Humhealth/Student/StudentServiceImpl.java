package com.Humhealth.Student;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService{
    private StudentDao dao;

    public Boolean validDate(String date){
        String regex = "^(0[1-9]|1[0-2])/([0-2][0-9]|3[01])/\\d{2}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);

        return matcher.matches();
    }

    @Override
    public void saveStudent(String firstName, String lastName, String middleName, String gender, String dob, int grade, Long ssn, String email,
                            String residingStatus, String status, String formerReason, Boolean repeatingGrade, Boolean transferStudent, Long transferID,
                            String primaryContact, String primaryRelationship, Long primaryNumber, String secondaryContact, String secondaryRelationship,
                            Long secondaryNumber, String addressLine1, String addressLine2, String city, String state, String zip) {
        //Not goin to nmake the class too big with a constructor for this
        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setMiddleName(middleName);
        student.setGender(gender);
        student.setDob(dob);
        student.setGrade(grade);
        student.setSsn(ssn);
        student.setEmail(email);
        student.setResidingStatus(residingStatus);
        student.setStatus(status);
        student.setFormerReason(formerReason);
        student.setRepeatingGrade(repeatingGrade);
        student.setTransferStudent(transferStudent);
        student.setTransferID(transferID);
        student.setPrimaryContact(primaryContact);
        student.setPrimaryRelationship(primaryRelationship);
        student.setPrimaryNumber(primaryNumber);
        student.setSecondaryContact(secondaryContact);
        student.setSecondaryRelationship(secondaryRelationship);
        student.setSecondaryNumber(secondaryNumber);
        student.setAddressLine1(addressLine1);
        student.setAddressLine2(addressLine2);
        student.setCity(city);
        student.setState(state);
        student.setZip(zip);

        dao.save(student);
    }

    @Override
    public void updateStudent(Long id, String firstName, String lastName, String middleName, String gender, String dob, int grade, Long ssn, String email,
                            String residingStatus, String status, String formerReason, Boolean repeatingGrade, Boolean transferStudent, Long transferID,
                            String primaryContact, String primaryRelationship, Long primaryNumber, String secondaryContact, String secondaryRelationship,
                            Long secondaryNumber, String addressLine1, String addressLine2, String city, String state, String zip) {
        Student student = dao.getStudentById(id);

        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setMiddleName(middleName);
        student.setGender(gender);
        student.setDob(dob);
        student.setGrade(grade);
        student.setSsn(ssn);
        student.setEmail(email);
        student.setResidingStatus(residingStatus);
        student.setStatus(status);
        student.setFormerReason(formerReason);
        student.setRepeatingGrade(repeatingGrade);
        student.setTransferStudent(transferStudent);
        student.setTransferID(transferID);
        student.setPrimaryContact(primaryContact);
        student.setPrimaryRelationship(primaryRelationship);
        student.setPrimaryNumber(primaryNumber);
        student.setSecondaryContact(secondaryContact);
        student.setSecondaryRelationship(secondaryRelationship);
        student.setSecondaryNumber(secondaryNumber);
        student.setAddressLine1(addressLine1);
        student.setAddressLine2(addressLine2);
        student.setCity(city);
        student.setState(state);
        student.setZip(zip);

        dao.update(student);
    }

    @Override
    public List<Student> getSudentList(){
        return dao.getAllStudents();
    }

    @Override
    public List<Student> filterStudents(Map<String, Object> params){
        return dao.filterStudents(params);
    }

    @Override
    public Student getStudentById(Long id){
        return dao.getStudentById(id);
    }

    @Override
    public List<Student> getStudentsInGrade(int gradeIn){
        return dao.getStudentsInGrade(gradeIn);
    }

    @Override
    public List<Student> searchFilteredStudentList(List<Student> students, String criteria, Object value){
        switch(criteria){
            case "S.No" -> {
                return students.stream().filter(student -> student.getId().equals(value)).collect(Collectors.toList());
            }
            case "Student Name" -> {
                return students.stream().filter(student -> (student.getFirstName() + student.getLastName()) == value).collect(Collectors.toList());
            }
            case "Gender" -> {
                return students.stream().filter(student -> student.getGender() == value).collect(Collectors.toList());
            }
            case "Date of Birth" -> {
                return students.stream().filter(student -> student.getDob() == value).collect(Collectors.toList());
            }
            case "SSN Number" -> {
                return students.stream().filter(student -> student.getSsn().equals(value)).collect(Collectors.toList());
            }
            case "Grade" -> {
                return students.stream().filter(student -> student.getGrade() == (int)value).collect(Collectors.toList());
            }
            case "Residing Status" -> {
                return students.stream().filter(student -> student.getResidingStatus() == value).collect(Collectors.toList());
            }
            case "Primary Contact Details" -> {
                return students.stream().filter(student -> (student.getPrimaryContact() + student.getPrimaryNumber().toString()) == value).collect(Collectors.toList());
            }
            case "Repetition Grade" -> {
                return students.stream().filter(student -> student.getRepeatingGrade().equals(value)).collect(Collectors.toList());
            }
            case "Transferred Student" -> {
                return students.stream().filter(student -> student.getTransferStudent().equals(value)).collect(Collectors.toList());
            }
            case "Address" -> {
                return students.stream().filter(student -> (student.getAddressLine1() + student.getAddressLine2()) == value).collect(Collectors.toList());
            }
        }
        return students;
    }

    @Override
    public List<Student> searchFilteredOverallStudents(List<Student> students, String criteria, Object value){
        switch(criteria){
            case "S.No" -> {
                return students.stream().filter(student -> student.getId().equals(value)).collect(Collectors.toList());
            }
            case "Student Name" -> {
                return students.stream().filter(student -> (student.getFirstName() + student.getLastName()) == value).collect(Collectors.toList());
            }
            case "Date of Birth" -> {
                return students.stream().filter(student -> student.getDob() == value).collect(Collectors.toList());
            }
            case "Grade" -> {
                return students.stream().filter(student -> student.getGrade() == (int)value).collect(Collectors.toList());
            }
        }
        return students;
    }

    @Override
    public Boolean checkStudentParams(Student student){
        if(student.getFirstName().length() <= 0 || student.getLastName().length() <= 0 || student.getMiddleName().length() <= 0){
            return false;
        }
        if(!"Male".equals(student.getGender()) && !"Female".equals(student.getGender())){
            return false;
        }
        if(!validDate(student.getDob())){
            return false;
        }
        if(student.getGrade() < 0 || student.getGrade() > 12){
            return false;
        }
        if(Long.toString(student.getSsn()).length() != 12) return false;
        if(student.getEmail().length() < 1){
            return false;
        }
        if(!student.getResidingStatus().equals("Hosteller") && !student.getResidingStatus().equals("Dayscholar")){
            return false;
        }
        if(!student.getStatus().equals("Pursuing") && !student.getStatus().equals("Former")){
            return false;
        }
        if(student.getFormerReason().length() < 1){
            return false;
        }
        if(Long.toString(student.getTransferID()).length() < 1){
            return false;
        }
        if(student.getPrimaryContact().length() <= 0){
            return false;
        }
        if(student.getPrimaryRelationship().length() <= 0){
            return false;
        }
        if(String.valueOf(student.getPrimaryNumber()).length() != 10){
            return false;
        }
        if(student.getSecondaryContact().length() <= 0){
            return false;
        }
        if(student.getSecondaryRelationship().length() <= 0){
            return false;
        }
        if(String.valueOf(student.getSecondaryNumber()).length() != 10){
            return false;
        }
        if(student.getAddressLine1().length() <= 0){
            return false;
        }
        if(student.getAddressLine2().length() <= 0){
            return false;
        }
        if(student.getCity().length() <= 0){
            return false;
        }
        if(student.getState().length() <= 0){
            return false;
        }
        return student.getZip().length() > 0;
    }
}
