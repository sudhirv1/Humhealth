package com.Humhealth.Attendance;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Humhealth.Response.Response;
import com.Humhealth.Student.Student;
import com.Humhealth.Student.StudentService;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private StudentService studentService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> listDayAttendanceInGrade(@RequestParam LocalDate dayIn, @RequestParam int gradeIn) {
        List<Student> studentList = studentService.getStudentsInGrade(gradeIn);
        Response r = new Response();
        r.setData(studentList);
        r.setStatus("Success");
        return new ResponseEntity<>(r,HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> generateAttReport(@RequestParam Map<String, String> params) {
        Map<String, Object> filterParams = new HashMap<>();
        final String[] criteria = {""};
        final Object[] val = {null};
        params.forEach((key, value) -> {
            switch (key) {
                case "criteria" -> criteria[0] = value;
                case "value" -> val[0] = value;
                case "date" -> filterParams.put(key, LocalDate.parse(value));
                case "present", "officialLeave", "sickLeave", "transferID" -> filterParams.put(key, Boolean.valueOf(value));
                case "Student.grade" -> filterParams.put(key, Integer.valueOf(value));
                default -> //if it's Student.status(String variable)
                    filterParams.put(key, value);
            }
        });

        List<Attendance> attendanceList = attendanceService.filter(filterParams, criteria[0], val[0]);
        Response r = new Response();
        r.setData(attendanceList);
        r.setStatus("Success");
        return new ResponseEntity<>(r,HttpStatus.OK);
    }

    @GetMapping("/mark/periodRepport")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> periodReport(Map<String, String> params, @RequestParam String criteria, @RequestParam Object val, @RequestParam int sickTarget, @RequestParam int officialLeaveTarget,
    @RequestParam String complianceStatus, @RequestParam Boolean halfPeriodLeave, @RequestParam String comparison, @RequestParam List<String> periods, @RequestParam List<Integer> years){
        Map<String, Object> filterParams = new HashMap<>();
        params.forEach((key, value) -> {
            if (key.equals("Student.status") && !value.equals("All")) {
                filterParams.put(key, Boolean.valueOf(value));
            } else if (key.equals("Student.grade")) {
                filterParams.put(key, Integer.valueOf(value));
            }
        });

        // Use the student filtering to filter by "Student Status" and "Student Grade"
        List<Student> stageOneStudents = studentService.filterStudents(filterParams);
        // Finish up the filtering and display the results
        List<AttendanceResponse> ans = attendanceService.computeAttendanceStats(criteria, val, periods, years, stageOneStudents, halfPeriodLeave, comparison, sickTarget, officialLeaveTarget);
        
        
        Response r = new Response();


        r.setData(ans);
        r.setStatus("Period Report Returned");
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> createAttendance(@RequestParam Boolean present, @RequestParam Boolean sickLeave, @RequestParam Boolean officialLeave, @RequestParam LocalDate date, @RequestParam Long studentId) {
        attendanceService.saveAttendance(present, sickLeave, officialLeave, date, studentId);
        Response r = new Response();
        r.setStatus("Success");
        r.setData("Attendance Saved Succesfully");
        return new ResponseEntity<>(r,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> getAttendanceById(@PathVariable Long id) {
        Attendance attendance = attendanceService.findAttendanceById(id);

        Response r = new Response();
        r.setStatus("Success");
        r.setData(attendance);
        return new ResponseEntity<>(r,HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> getAllAttendances() {
        List<Attendance> attendanceList = attendanceService.findAllAttendances();

        Response r = new Response();
        r.setStatus("Success");
        r.setData(attendanceList);
        return new ResponseEntity<>(r,HttpStatus.OK);
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> getAttendancesByStudentId(@PathVariable Long studentId) {
        List<Attendance> attendances = attendanceService.findAttendancesByStudentId(studentId);
        
        Response r = new Response();
        r.setStatus("Success");
        r.setData(attendances);
        return new ResponseEntity<>(r,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> updateAttendance(@PathVariable Long id, @RequestParam Boolean present, @RequestParam Boolean sickLeave, @RequestParam Boolean officialLeave, @RequestParam LocalDate date) {
        attendanceService.updateAttendance(id, present, sickLeave, officialLeave, date);

        Response r = new Response();
        r.setStatus("Success");
        r.setData("Attendance Updated Succesfully");
        return new ResponseEntity<>(r,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> deleteAttendance(@PathVariable Long id) {
        attendanceService.deleteAttendance(id);

        Response r = new Response();
        r.setStatus("Success");
        r.setData("Attendance Deleted Succesfully");
        return new ResponseEntity<>(r,HttpStatus.OK);
    }

    // New endpoint to mark attendance
    @PostMapping("/mark")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> markAttendance(@RequestParam Long studentId, @RequestParam String date, @RequestParam Boolean present, @RequestParam Boolean sickLeave, @RequestParam Boolean officialLeave) {
        LocalDate attendanceDate = LocalDate.parse(date);
        attendanceService.markAttendance(studentId, attendanceDate, present, sickLeave, officialLeave);

        Response r = new Response();
        r.setStatus("Success");
        r.setData("Attendance Marked Succesfully");
        return new ResponseEntity<>(r,HttpStatus.OK);
    }
}

