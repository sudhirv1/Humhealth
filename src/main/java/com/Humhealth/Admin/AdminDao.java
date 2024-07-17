package com.Humhealth.Admin;

public interface AdminDao {
    void saveAdmin(Admin admin);
    Admin findAdminById(Long id);
    void updateAdmin(Admin admin);
}
