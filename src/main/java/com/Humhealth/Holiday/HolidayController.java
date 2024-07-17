package com.Humhealth.Holiday;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Humhealth.Response.Response;


@RestController
@RequestMapping("/holiday")
public class HolidayController {
    @Autowired
    private HolidayService holidayService;

    @PostMapping("/addHoliday")
    public ResponseEntity<?> addHoliday(@RequestParam String holidayName, @RequestParam LocalDate date) {
        holidayService.save(holidayName, date);

        Response r = new Response();
        r.setStatus("Success");
        r.setData("holiday update succesfully");
        return new ResponseEntity<>(r, HttpStatus.OK);
    }
}
