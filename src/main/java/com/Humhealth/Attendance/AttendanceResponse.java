package com.Humhealth.Attendance;

import com.Humhealth.Student.Student;

public class AttendanceResponse {
    private Student student;
    private int year;
    private int totalDays;
    private int sickDayCnt;
    private int officialDayCnt;
    private int presentDayCnt;

    public AttendanceResponse(Student student, int year,int totalDays, int sickDayCnt, int officialDayCnt, int presentDayCnt){
        this.student = student;
        this.year = year;
        this.totalDays = totalDays;
        this.sickDayCnt = sickDayCnt;
        this.officialDayCnt = officialDayCnt;
        this.presentDayCnt = presentDayCnt;
    }
    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        this.student = student;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getTotalDays() {
        return totalDays;
    }
    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }
    public int getSickDayCnt() {
        return sickDayCnt;
    }
    public void setSickDayCnt(int sickDayCnt) {
        this.sickDayCnt = sickDayCnt;
    }
    public int getOfficialDayCnt() {
        return officialDayCnt;
    }
    public void setOfficialDayCnt(int officialDayCnt) {
        this.officialDayCnt = officialDayCnt;
    }
    public int getPresentDayCnt() {
        return presentDayCnt;
    }
    public void setPresentDayCnt(int presentDayCnt) {
        this.presentDayCnt = presentDayCnt;
    }
}
