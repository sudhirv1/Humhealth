package com.Humhealth.Holiday;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
@Service
public interface HolidayService {
    void save(String holdiayName, LocalDate date);
    void update(Holiday holiday);
    void deleteById(Long id);
    List<Holiday> listHolidays();
    Set<LocalDate> getHolidayDates();
}