package com.Humhealth.Student;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Humhealth.Attendance.Attendance;
import com.Humhealth.Attendance.AttendanceService;
import com.Humhealth.Holiday.HolidayService;
import com.Humhealth.Marks.Marks;
import com.Humhealth.Marks.MarksService;
import com.Humhealth.Response.Response;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private MarksService marksService;

    @Autowired
    private HolidayService holidayService;

    // Create a Student
    @PostMapping("/addStudent")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> addStudent(
    @RequestParam String firstName,
    @RequestParam String lastName,
    @RequestParam String middleName,
    @RequestParam String gender,
    @RequestParam String dob,
    @RequestParam int grade,
    @RequestParam Long ssn,
    @RequestParam String email,
    @RequestParam String residingStatus,
    @RequestParam String status,
    @RequestParam String formerReason,
    @RequestParam Boolean repeatingGrade,
    @RequestParam Boolean transferStudent,
    @RequestParam Long transferID,
    @RequestParam String primaryContact,
    @RequestParam String primaryRelationship,
    @RequestParam Long primaryNumber,
    @RequestParam String secondaryContact,
    @RequestParam String secondaryRelationship,
    @RequestParam Long secondaryNumber,
    @RequestParam String addressLine1,
    @RequestParam String addressLine2,
    @RequestParam String city,
    @RequestParam String state,
    @RequestParam String zip) {
        studentService.saveStudent(firstName, lastName, middleName, gender, dob, grade, ssn, email, residingStatus, status, formerReason,
                                    repeatingGrade, transferStudent, transferID, primaryContact, primaryRelationship, primaryNumber,
                                    secondaryContact, secondaryRelationship, secondaryNumber, addressLine1, addressLine2, city, state, zip);
        
        Response r = new Response();
        r.setData("Added Student");
        r.setStatus("Success");
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    // Edit a student
    @PostMapping("/students/update/{id}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> updateStudent(
            @PathVariable Long id,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String middleName,
            @RequestParam String gender,
            @RequestParam String dob,
            @RequestParam int grade,
            @RequestParam Long ssn,
            @RequestParam String email,
            @RequestParam String residingStatus,
            @RequestParam String status,
            @RequestParam String formerReason,
            @RequestParam Boolean repeatingGrade,
            @RequestParam Boolean transferStudent,
            @RequestParam Long transferID,
            @RequestParam String primaryContact,
            @RequestParam String primaryRelationship,
            @RequestParam Long primaryNumber,
            @RequestParam String secondaryContact,
            @RequestParam String secondaryRelationship,
            @RequestParam Long secondaryNumber,
            @RequestParam String addressLine1,
            @RequestParam String addressLine2,
            @RequestParam String city,
            @RequestParam String state,
            @RequestParam String zip) {
        studentService.updateStudent(id, firstName, lastName, middleName, gender, dob, grade, ssn, email, residingStatus, status,
                formerReason, repeatingGrade, transferStudent, transferID, primaryContact, primaryRelationship, primaryNumber, secondaryContact,
                secondaryRelationship, secondaryNumber, addressLine1, addressLine2, city, state, zip);
        
        Response r = new Response();
        r.setData("Successfully updated student");
        r.setStatus("Success");
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    // List students
    @GetMapping("/students/list")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> listStudents(){
        List<Student> ans = studentService.getSudentList();

        Response r = new Response();
        r.setData(ans);
        r.setStatus("Success");
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    //Filter Students
    @GetMapping("/students/list")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> filterAndListStudents(@RequestParam Map<String, String> params){
        Map<String, Object> filterParams = new HashMap<>();
        final String[] criteria = {""};
        final Object[] val = {null};
        params.forEach((key, value) -> {
            switch (key) {
                case "criteria":
                    criteria[0] = value;
                    break;
                case "value":
                    val[0] = value;
                    break;
                case "grade":
                    filterParams.put(key, Integer.valueOf(value));
                    break;
                case "ssn":
                case "primaryNumber":
                case "secondaryNumber":
                case "transferID":
                    filterParams.put(key, Long.valueOf(value));
                    break;
                case "repeatingGrade":
                case "transferStudent":
                    filterParams.put(key, Boolean.valueOf(value));
                    break;
                default:
                    filterParams.put(key, value);
                    break;
            }
        });
        List<Student> ans = studentService.filterStudents(filterParams);
        ans = studentService.searchFilteredStudentList(ans, criteria[0], val[0]);

        Response r = new Response();
        r.setData(ans);
        r.setStatus("Success");
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    // Think I should delete this
    @GetMapping("/students/{id}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public String displayGivenStudent(@RequestParam Long id){
        // TODO: Get the student's information
        Student student = studentService.getStudentById(id);

        // TODO: Get the student's daily attendance
        LocalDate currDate  = LocalDate.now();
        Boolean presToday = attendanceService.showedUpToday(id, currDate);

        // TODO: Get the student's peridoic attendance
        List<Attendance> studentAttendanceRecord = attendanceService.findAttendancesByStudentId(id);
        int periodAttendance = 0;
        // has to excuse students for holdiay too.
        Set<LocalDate> holidays = holidayService.getHolidayDates();
        for(Attendance at : studentAttendanceRecord){ //Going to default to last 90 days
            if(!holidays.contains(at.getDate()) && (currDate.compareTo(at.getDate()) <= 90) && attendanceService.showedUpToday(id, at.getDate())){
                periodAttendance++;
            }
        }
        System.out.println("Showed up: " + periodAttendance/90 + " percent of the time over past 90 days.");

        // TODO: Get the student's periodic academic performance for each quarter
        List<Marks> studentMarkCollection =  marksService.findMarksByStudentId(id);
        for(Marks CM : studentMarkCollection){
            System.out.println("Tamil: " + CM.getTamil());
            System.out.println("English: " + CM.getEnglish());
            System.out.println("Science: " + CM.getScience());
            System.out.println("Social Science: " + CM.getSocialScience());
            System.out.println("Social Science: " + CM.getMaths());
        }

        // TODO: Get the student's academic + attendance overview
        //Display the number of days the student is in compliance with attendance(not delinquant) not including holidays
        int totalDaysAttended = 0;
        for(Attendance at : studentAttendanceRecord){
            if(!holidays.contains(at.getDate()) && attendanceService.showedUpToday(id, at.getDate())){
                totalDaysAttended ++;
            }
        }
        System.out.println("Attended " + totalDaysAttended + " days total.");
        // Display the percentage of classes passing
        int quarterToChoose;
        if(currDate.getMonthValue() <= 3){
            quarterToChoose = 1;
        }
        else if(currDate.getMonthValue() <= 6){
            quarterToChoose = 2;
        }
        else if(currDate.getMonthValue() <= 9){
            quarterToChoose = 3;
        }
        else{
            quarterToChoose = 4;
        }
        Marks currMarks =  marksService.findMarksByYearQuarterStudent(currDate.getYear(), quarterToChoose, id);
        int failing = 0;
        if(currMarks.getTamil() < 40.0) failing++;
        if(currMarks.getEnglish() < 40.0) failing++;
        if(currMarks.getScience() < 40.0) failing++;
        if(currMarks.getSocialScience() < 40.0) failing++;
        if(currMarks.getMaths() < 40.0) failing++;
        System.out.println("Failing " + ""+(failing/6) + "% of classes.");

        // TODO: Figure out what the return type here should be. We hav a lot of data to send back to the client
        return "Student's Data and Analytics are displayed";
    }

    //Student Overall Report
    @GetMapping("/students/overall")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> overallReport(@RequestParam String criteria, @RequestParam Object val, @RequestParam int year, @RequestParam String compliance, @RequestParam String marksRes
    ,@RequestParam String studentStatus, @RequestParam int grade){
        Map<String, Object> filterParams = new HashMap<>();
        if (!studentStatus.equals("All")) {
            filterParams.put("status", String.valueOf(studentStatus));
        }
        filterParams.put("grade", grade);
        List<Student> ans = studentService.filterStudents(filterParams);

        if(!compliance.equals("All")){
            List<Student> tempList = new ArrayList<>();
            for(Student student : ans){
                if(compliance.equals("Compliance") && attendanceService.attendancePercentage(1, 12, year, student) > 60.0){
                    tempList.add(student);
                }
                else if(compliance.equals("Non-Compliance") && attendanceService.attendancePercentage(1, 12, year, student) <= 60.0){
                    tempList.add(student);
                }
            }
            ans = tempList;
        }

        if(!marksRes.equals("All")){
            List<Student> tempList = new ArrayList<>();
            for(Student student : ans){
                if(marksRes.equals("Pass") && marksService.yearTotalMarkPercent(year, student.getId()) > 50.0){
                    tempList.add(student);
                }
                else if(marksRes.equals("Fail") && marksService.yearTotalMarkPercent(year, student.getId()) <= 50.0){
                    tempList.add(student);
                }
            }
            ans = tempList;
        }

        if(criteria != null && val != null){
            ans = studentService.searchFilteredOverallStudents(ans, criteria, val);
        }

        Response r = new Response();
        r.setStatus("Succefully got overallReport");
        r.setData(ans);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }
}
