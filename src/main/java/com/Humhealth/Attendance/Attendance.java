package com.Humhealth.Attendance;

import java.time.LocalDate;

import com.Humhealth.Student.Student;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "present")
    private Boolean present;
    @Column(name = "sick_leave")
    private Boolean sickLeave;
    @Column(name = "official_leave")
    private Boolean officialLeave;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "student_id", insertable = false, updatable = false, nullable = false)
    private Student student;
    //--------------------------------------------------------------------------------------
    public Attendance(Boolean present, Boolean sickLeave, Boolean officialLeave, LocalDate date){
        this.present = present;
        this.sickLeave = sickLeave;
        this.officialLeave = officialLeave;
        this.date = date;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getPresent() {
        return present;
    }

    public void setPresent(Boolean present) {
        this.present = present;
    }

    public Boolean getSickLeave() {
        return sickLeave;
    }

    public void setSickLeave(Boolean sickLeave) {
        this.sickLeave = sickLeave;
    }

    public Boolean getOfficialLeave() {
        return officialLeave;
    }

    public void setOfficialLeave(Boolean officialLeave) {
        this.officialLeave = officialLeave;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(java.time.LocalDate date2) {
        this.date = date2;
    }
    
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

}
