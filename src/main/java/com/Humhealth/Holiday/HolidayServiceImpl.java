package com.Humhealth.Holiday;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class HolidayServiceImpl implements HolidayService {
    private HolidayDao dao;
    @Override
    public void save(String holidayName, LocalDate date) {
        Holiday holiday = new Holiday(holidayName, date);
        dao.save(holiday);
    }

    @Override
    public void update(Holiday holiday) {
        dao.update(holiday);
    }

    @Override
    public void deleteById(Long id) {
        dao.delete(id);
    }

    @Override
    public List<Holiday> listHolidays() {
        return dao.listHolidays();
    }

    @Override
    public Set<LocalDate> getHolidayDates(){
        return dao.getHolidayDates();
    }

}
