package com.Humhealth.Holiday;
import java.time.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
public class Holiday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "holiday_name")
    private String holidayName;
    @Column(name = "date")
    private LocalDate date;

    public Holiday(String holidayName, LocalDate date){
        this.holidayName = holidayName;
        this.date = date;
    }
    //----------------------------------------------------------------
    public String getHolidayName() {
        return holidayName;
    }
    public void setHoldiayName(String holdiayName) {
        this.holidayName = holdiayName;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
}
