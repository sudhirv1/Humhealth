package com.Humhealth.Teacher;

import com.Humhealth.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
@Entity
public class Teacher extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "grade_taught")
    private int gradeTaught;

    public Teacher(String firstName, String lastName, int gradeTaught, String username, String password, String role){
        this.firstName = firstName;
        this.lastName = lastName;
        this.gradeTaught = gradeTaught;
        this.setUsername(username);
        this.setPassword(password);
        this.setRole(role);
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

    public int getGradeTaught() {
        return gradeTaught;
    }

    public void setGradeTaught(int gradeTaught) {
        this.gradeTaught = gradeTaught;
    }

}
