package com.Humhealth.Holiday;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.time.LocalDate;

@Service
public interface HolidayDao {
    void save(Holiday holiday);
    void update(Holiday holiday);
    void delete(Long id);
    List<Holiday> listHolidays();
    Set<LocalDate> getHolidayDates();
}
