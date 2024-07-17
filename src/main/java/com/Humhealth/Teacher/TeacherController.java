package com.Humhealth.Teacher;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Humhealth.Response.Response;

@Controller
@RequestMapping("/teacher")
class TeacherController {
    @Autowired
    private TeacherService teacherService;

	@PostMapping("/teachers/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createTeacher(@RequestParam String firstName, @RequestParam String lastName, @RequestParam int gradeTaught, @RequestParam String username, @RequestParam String password, @RequestParam String role) {
        Response r = new Response();
        if(gradeTaught < 1 || gradeTaught > 12){
            r.setData("Invalid Grade Taught.");
            r.setStatus("Failure");
        }
        else{
            teacherService.saveTeacher(firstName, lastName, gradeTaught, username, password, role);
            r.setData("Teacher Created");
            r.setStatus("Success");
        }
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

	@PostMapping("/teachers/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateTeacher(@PathVariable Long id, @RequestParam String firstName, @RequestParam String lastName, @RequestParam int gradeTaught, @RequestParam String username, @RequestParam String password, @RequestParam String role) {
        teacherService.updateTeacher(id, firstName, lastName, gradeTaught, username, password, role);
        
        Response r = new Response();
        r.setData("Teacher Edited");
        r.setStatus("Success");
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @PostMapping("/teachers/update")
    @PreAuthorize("hasRole('ROLE_TEACHER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> listTeachers(){
        // Call a function in the Service that will then in turn call the DOM and pull all students using Hibernate
		List<Teacher> teacherList = teacherService.getTeacherList();
        
        Response r = new Response();
        r.setData(teacherList);
        r.setStatus("Success");
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @GetMapping("/teachers/filter")
    public ResponseEntity<?> filterTeachers(@RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName, @RequestParam(required = false) int grade) {
        List<Teacher> teacherList = teacherService.filterTeachers(firstName, lastName, grade);

		Response r = new Response();
        r.setData(teacherList);
        r.setStatus("Success");
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

}