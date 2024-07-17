package com.Humhealth.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService{
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminDao adminDao;

    @Override
    public void saveAdmin(String firstName, String lastName, String username, String password, String role){
        Admin admin = new Admin(firstName, lastName, username, password, role);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminDao.saveAdmin(admin);
    }

    @Override
    public Admin findAdminById(Long id){
        return adminDao.findAdminById(id);
    }

    @Override
    public void updateAdmin(Long id, String firstName, String lastName, String username, String password, String role){
        Admin admin = adminDao.findAdminById(id);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminDao.updateAdmin(admin);
    }
}