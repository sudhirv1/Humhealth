package com.Humhealth.Marks;

import com.Humhealth.Student.Student;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Column;

@Entity
public class Marks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Going to try putting a year and quarter variable in
    @Column(name = "year")
    private int year;
    @Column(name = "quarter")
    private int quarter;

    @Column(name = "tamil")
    private double tamil;
    @Column(name = "english")
    private double english;
    @Column(name = "maths")
    private double maths;
    @Column(name = "science")
    private double science;
    @Column(name = "social_science")
    private double socialScience;
    @Column(name = "total")
    private double total;

    @OneToOne(mappedBy = "marks")
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    public Marks(int year, int quarter, double tamil, double english, double maths, double science, double socialScience){
        this.year = year;
        this.quarter = quarter;
        this.tamil = tamil;
        this.english = english;
        this.maths = maths;
        this.science = science;
        this.socialScience = socialScience;
        
        this.total = quarter + tamil + english + maths + science + socialScience;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
