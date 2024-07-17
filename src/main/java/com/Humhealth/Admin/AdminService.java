package com.Humhealth.Admin;

import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    void saveAdmin(String firstName, String lastName, String username, String password, String role);
    Admin findAdminById(Long id);
    void updateAdmin(Long id, String firstName, String lastName, String username, String password, String role);
}