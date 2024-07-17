package com.Humhealth.Marks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.Humhealth.Response.Response;
import com.Humhealth.Student.Student;
import com.Humhealth.Student.StudentService;

@RestControllerAdvice
@RequestMapping("/Marks")
public class  MarksController {
    @Autowired
    StudentService studentService;

    @Autowired
    MarksService marksService;

    @GetMapping("/markReport")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> listMarkEntryForm(@RequestParam int grade, @RequestParam int year, @RequestParam List<String> periods){
        List<Student> studentList = studentService.getStudentsInGrade(grade);
        List<MarksResponse> ans = marksService.listMarkEntrys(studentList, year, periods);
        
        Response r = new Response();
        r.setData(ans);
        r.setStatus("Succesfully listing student mark entry rows");
        return new ResponseEntity<>(r, HttpStatus.OK);

    }

    @GetMapping("/markReport")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> periodicMarkReport(@RequestParam String criteria, @RequestParam Object val, @RequestParam Map<String, String> params, @RequestParam List<String> periods,
    @RequestParam Integer year, @RequestParam String subjectToppers, @RequestParam Boolean overallTopper, @RequestParam String marksResult){
        Map<String, Object> filterParams = new HashMap<>();
        params.forEach((key, value) -> {
            if (key.equals("Student.status") && !value.equals("All")) {
                filterParams.put(key, Boolean.valueOf(value));
            } else if (key.equals("Student.grade")) {
                filterParams.put(key, Integer.valueOf(value));
            }
        });

        // Use the student filtering to filter by "Student Status" and "Student Grade" to apply the 2 student filters
        List<Student> stageOneStudents = studentService.filterStudents(filterParams);
        // Apply the other 5 filters
        List<MarksResponse> ans = marksService.computeMarksReport(criteria, val, stageOneStudents, year, periods, subjectToppers, overallTopper, marksResult);
        Response r = new Response();
        r.setData(ans);
        r.setStatus("Success");
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @PostMapping("/saveMarks/{studentId}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> saveMarks(@PathVariable Long studentId, @RequestParam int year, @RequestParam int quarter, @RequestParam double tamil, @RequestParam double english, @RequestParam double maths, @RequestParam double science, @RequestParam double socialScience){
        marksService.saveMarks(studentId, year, quarter, tamil, english, maths, science, socialScience);

        Response r = new Response();
        r.setData("Saved Marks");
        r.setStatus("Success");
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @PostMapping("/editMarks/{id}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> editMarks(@PathVariable Long id, @RequestParam int year, @RequestParam int quarter, @RequestParam double tamil, @RequestParam double english, @RequestParam double maths, @RequestParam double science, @RequestParam double socialScience){
        marksService.updateMarks(id, year, quarter, tamil, english, maths, science, socialScience);

        Response r = new Response();
        r.setData("Edited Marks");
        r.setStatus("Success");
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

}
