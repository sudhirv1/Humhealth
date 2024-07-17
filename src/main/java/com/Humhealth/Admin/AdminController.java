package com.Humhealth.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.Humhealth.Response.Response;
import com.Humhealth.Teacher.TeacherService;

// The AdminController file will handle endpoints for behaviors exclusive to the Admin role

@RestControllerAdvice
@RequestMapping("/admin")
public class AdminController {
	@Autowired
    private TeacherService teacherService;

	@Autowired
	private AdminService adminService;

	@PostMapping("/teachers/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateTeacher(@PathVariable Long id, @RequestParam String firstName, @RequestParam String lastName, @RequestParam int gradeTaught, @RequestParam String username, @RequestParam String password, @RequestParam String role) {
        teacherService.updateTeacher(id, firstName, lastName, gradeTaught, username, password, role);
        
        Response r = new Response();
        r.setStatus("Success");
        r.setData("Teacher Updated Succesfully");
        return new ResponseEntity<>(r,HttpStatus.OK);
    }

	@PostMapping("/teachers")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createTeacher(@RequestParam String firstName, @RequestParam String lastName, @RequestParam int gradeTaught, @RequestParam String username, @RequestParam String password, @RequestParam String role) {
        teacherService.saveTeacher(firstName, lastName, gradeTaught, username, password, role);

        Response r = new Response();
        r.setStatus("Success");
        r.setData("Teacher Created Succesfully");
        return new ResponseEntity<>(r,HttpStatus.OK);
    }

	@PostMapping("/admins")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createAdmin(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String username, @RequestParam String password, @RequestParam String role) {
        adminService.saveAdmin(firstName, lastName, username, password, role);
        
        Response r = new Response();
        r.setStatus("Success");
        r.setData("Admin Created Succesfully");
        return new ResponseEntity<>(r,HttpStatus.OK);
    }

    @PostMapping("/admins/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateAdmin(@PathVariable Long id, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String username, @RequestParam String password, @RequestParam String role) {
        adminService.updateAdmin(id, firstName, lastName, username, password, role);
        
        Response r = new Response();
        r.setStatus("Success");
        r.setData("Admin Updated Succesfully");
        return new ResponseEntity<>(r,HttpStatus.OK);
    }
}