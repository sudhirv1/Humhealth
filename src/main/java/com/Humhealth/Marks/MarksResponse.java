package com.Humhealth.Marks;

import com.Humhealth.Student.Student;

public class MarksResponse {
    private Student student;
    private String firstName;
    private String lastName;
    private double tamil;
    private double english;
    private double maths;
    private double science;
    private double socialScience;
    private double totalMarks;

    public MarksResponse(double english, String firstName, String lastName, double maths, double science, double socialScience, Student student, double tamil, double totalMarks) {
        this.english = english;
        this.firstName = firstName;
        this.lastName = lastName;
        this.maths = maths;
        this.science = science;
        this.socialScience = socialScience;
        this.student = student;
        this.tamil = tamil;
        this.totalMarks = totalMarks;
    }

    public Student getStudent() {
        return this.student;
    }

    public void setStudentId(Student student) {
        this.student = student;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getTamil() {
        return tamil;
    }

    public void setTamil(double tamil) {
        this.tamil = tamil;
    }

    public double getEnglish() {
        return english;
    }

    public void setEnglish(double english) {
        this.english = english;
    }

    public double getMaths() {
        return maths;
    }

    public void setMaths(double maths) {
        this.maths = maths;
    }

    public double getScience() {
        return science;
    }

    public void setScience(double science) {
        this.science = science;
    }

    public double getSocialScience() {
        return socialScience;
    }

    public void setSocialScience(double socialScience) {
        this.socialScience = socialScience;
    }

    public double getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(double totalMarks) {
        this.totalMarks = totalMarks;
    }
}
