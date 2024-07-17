package com.Humhealth.Marks;
import java.util.List;

public interface MarksDao {
    void save(Marks marks);
    Marks findById(Long id);
    List<Marks> getAll();
    void update(Marks marks);
    void delete(Marks marks);
    List<Marks> findMarksByStudentId(Long studentId);
    Marks findMarksByYearQuarterStudent(int yearIn, int quarterIn, Long studentId);
}

