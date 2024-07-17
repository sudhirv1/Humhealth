package com.Humhealth.Student;

import com.Humhealth.Attendance.Attendance;
import com.Humhealth.Marks.Marks;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "Students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "attendances")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Attendance> attendances;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "marks_id", referencedColumnName = "id") //Owning side gets the @JoinColumn annotation
    private Marks marks;

    @Column(name = "first_Name")
    private String firstName = null;
    @Column(name = "middle_Name")
    private String middleName = null;
    @Column(name = "last_Name")
    private String lastName = null;
    @Column(name = "gender")
    private String gender = null;
    @Column(name = "dob")
    private String dob = null;
    @Column(name = "grade")
    private int grade = 0;
    @Column(name = "ssn")
    private Long ssn = null;
    @Column(name = "email")
    private String email = null;
    @Column(name = "residing_status")
    private String residingStatus = null;
    @Column(name = "status")
    private String status = null; // Graduated, Enrolled, etc
    @Column(name = "former_reason")
    private String formerReason = null;
    @Column(name = "repeating_grade")
    private Boolean repeatingGrade;
    @Column(name = "transfer_student")
    private Boolean transferStudent;
    @Column(name = "transfer_id")
    private Long transferID;
    @Column(name = "primary_contact")
    private String primaryContact = null;
    @Column(name = "primary_relationship")
    private String primaryRelationship = null;
    @Column(name = "primary_number")
    private Long primaryNumber = null;
    @Column(name = "secondary_contact")
    private String secondaryContact = null;
    @Column(name = "secondary_relationship")
    private String secondaryRelationship = null;
    @Column(name = "secondary_number")
    private Long secondaryNumber = null;
    @Column(name = "address_line1")
    private String addressLine1 = null;
    @Column(name = "address_line2")
    private String addressLine2 = null;
    @Column(name = "city")
    private String city = null;
    @Column(name = "state")
    private String state = null;
    @Column(name = "zip")
    private String zip = null;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Long getSsn() {
        return ssn;
    }

    public void setSsn(Long ssn) {
        this.ssn = ssn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResidingStatus() {
        return residingStatus;
    }

    public void setResidingStatus(String residingStatus) {
        this.residingStatus = residingStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFormerReason() {
        return formerReason;
    }

    public void setFormerReason(String formerReason) {
        this.formerReason = formerReason;
    }

    public Boolean getRepeatingGrade() {
        return repeatingGrade;
    }

    public void setRepeatingGrade(Boolean repeatingGrade) {
        this.repeatingGrade = repeatingGrade;
    }

    public Boolean getTransferStudent() {
        return transferStudent;
    }

    public void setTransferStudent(Boolean transferStudent) {
        this.transferStudent = transferStudent;
    }

    public Long getTransferID() {
        return transferID;
    }

    public void setTransferID(Long transferID) {
        this.transferID = transferID;
    }

    public String getPrimaryContact() {
        return primaryContact;
    }

    public void setPrimaryContact(String primaryContact) {
        this.primaryContact = primaryContact;
    }

    public String getPrimaryRelationship() {
        return primaryRelationship;
    }

    public void setPrimaryRelationship(String primaryRelationship) {
        this.primaryRelationship = primaryRelationship;
    }

    public Long getPrimaryNumber() {
        return primaryNumber;
    }

    public void setPrimaryNumber(Long primaryNumber) {
        this.primaryNumber = primaryNumber;
    }

    public String getSecondaryContact() {
        return secondaryContact;
    }

    public void setSecondaryContact(String secondaryContact) {
        this.secondaryContact = secondaryContact;
    }

    public String getSecondaryRelationship() {
        return secondaryRelationship;
    }

    public void setSecondaryRelationship(String secondaryRelationship) {
        this.secondaryRelationship = secondaryRelationship;
    }

    public Long getSecondaryNumber() {
        return secondaryNumber;
    }

    public void setSecondaryNumber(Long secondaryNumber) {
        this.secondaryNumber = secondaryNumber;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
