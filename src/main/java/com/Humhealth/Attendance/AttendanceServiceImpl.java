package com.Humhealth.Attendance;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Humhealth.Student.Student;
import com.Humhealth.Student.StudentDao;


@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceDao attendanceDao;

    @Autowired
    private StudentDao studentDao;

    @Override
    public void saveAttendance(Boolean present, Boolean sickLeave, Boolean officialLeave, LocalDate date, Long studentId){
        Attendance attendance = new Attendance(present, sickLeave, officialLeave, date);//Not this constructor doesn't set student
        Student student = studentDao.getStudentById(studentId);
        attendance.setStudent(student);
        attendanceDao.save(attendance);
    }

    @Override
    public Attendance findAttendanceById(Long id) {
        return attendanceDao.findById(id);
    }

    @Override
    public List<Attendance> findAllAttendances() {
        return attendanceDao.getAll();
    }

    @Override
    public List<Attendance> findAttendancesByStudentId(Long studentId) {
        return attendanceDao.findByStudentId(studentId);
    }

    @Override
    public void updateAttendance(Long id, Boolean present, Boolean sickLeave, Boolean officialLeave, LocalDate date){
        Attendance attendance = findAttendanceById(id);

        attendance.setDate(date);
        attendance.setPresent(present);
        attendance.setOfficialLeave(officialLeave);
        attendance.setSickLeave(sickLeave);

        attendanceDao.update(attendance);
    }

    @Override
    public void deleteAttendance(Long id) {
        Attendance attendance = findAttendanceById(id);
        attendanceDao.delete(attendance);
    }

    @Override
    public void markAttendance(Long studentId, LocalDate attendanceDate, Boolean present, Boolean sickLeave, Boolean officialLeave) {
        Student student = studentDao.getStudentById(studentId);
        if (student != null) {
            saveAttendance( present, sickLeave, officialLeave, attendanceDate, studentId);
        }
    }

    @Override
    public Boolean showedUpToday(Long studentId, LocalDate currDate){
        return attendanceDao.showedUpToday(studentId, currDate);
    }

    @Override
    public List<Attendance> filter(Map<String, Object> params, String criteria, Object value) {
        // Apply Filters
        List<Attendance> ans = attendanceDao.filterAttendances(params);
        //Search By
        switch(criteria){
            case "S.No" -> {
                return ans.stream().filter(attendance -> attendance.getStudent().getId().equals(value)).collect(Collectors.toList());
            }
            case "Student Name" -> {
                return ans.stream().filter(attendance -> (attendance.getStudent().getFirstName() + attendance.getStudent().getLastName()) == value).collect(Collectors.toList());
            }
            case "Grade" -> {
                return ans.stream().filter(attendance -> attendance.getStudent().getGrade() == (int)value).collect(Collectors.toList());
            }
            case "Attendance Date" -> {
                return ans.stream().filter(attendance -> attendance.getDate() == value).collect(Collectors.toList());
            }
            case "Sick Leave" -> {
                return ans.stream().filter(attendance -> Objects.equals(attendance.getSickLeave(), value)).collect(Collectors.toList());
            }
            case "Official Leave" -> {
                return ans.stream().filter(attendance -> Objects.equals(attendance.getOfficialLeave(), value)).collect(Collectors.toList());
            }
            case "Attendance Status" -> {
                return ans.stream().filter(attendance -> Objects.equals(attendance.getPresent(), value)).collect(Collectors.toList());
            }
        }
        return ans;
    }

    @Override
    public String isCompliantRetStr(int presentDays, int totalDays){
        if(presentDays/totalDays > 0.8){
            return "True";
        }
        else{
            return "False";
        }
    }

    @Override
    public List<AttendanceResponse> computeAttendanceStats(String criteria, Object value, List<String> periods, List<Integer> years, List<Student> stageOneStudents, Boolean halfPeriodLeave,
    String comparison, int sickTarget, int officialLeaveTarget){
        List<AttendanceResponse> ans = new ArrayList<>();
        for(String per : periods){
            switch (per) {
                case "Quarterly" ->                     {
                        //Has to run 4 time for each quarter
                        int startMonth = 1;
                        int endMonth = 3;
                        int halfTimePnt = 46;
                        for(int i = 0; i < 3; i = i + 1){
                            ans.addAll(attStatCheckAndAdd(years,stageOneStudents, startMonth, endMonth, halfTimePnt, halfPeriodLeave, comparison, sickTarget, officialLeaveTarget));
                            startMonth = endMonth+1;
                            endMonth+=3;
                        }                          }
                case "Half-yearly" ->                     {
                        //Has to run 2 times
                        int startMonth = 1;
                        int endMonth = 6;
                        int halfTimePnt = 92;
                        for(int i = 0; i < 1; i = i + 1){
                            ans.addAll(attStatCheckAndAdd(years,stageOneStudents, startMonth, endMonth, halfTimePnt, halfPeriodLeave, comparison, sickTarget, officialLeaveTarget));
                            startMonth = endMonth+1;
                            endMonth+=6;
                        }                          }
                case "Yearly" ->                     {
                        int startMonth = 1;
                        int endMonth = 12;
                        int halfTimePnt = 183;
                        ans.addAll(attStatCheckAndAdd(years,stageOneStudents, startMonth, endMonth, halfTimePnt, halfPeriodLeave, comparison, sickTarget, officialLeaveTarget));
                    }
                default -> {
                }
            }
        }

        if(criteria != null && value != null){
            switch(criteria){
                case "S.No" -> {
                    return ans.stream().filter(attendanceResp -> attendanceResp.getStudent().getId().equals(value)).collect(Collectors.toList());
                }
                case "Student Name" -> {
                    return ans.stream().filter(attendanceResp -> (attendanceResp.getStudent().getFirstName() + attendanceResp.getStudent().getLastName()) == value).collect(Collectors.toList());
                }
                case "Grade" -> {
                    return ans.stream().filter(attendanceResp -> attendanceResp.getStudent().getGrade() == (int)value).collect(Collectors.toList());
                }
                case "Year" -> {
                    return ans.stream().filter(attendanceResp -> Objects.equals(attendanceResp.getYear(), value)).collect(Collectors.toList());
                }
                case "Total Working Days" -> {
                    return ans.stream().filter(attendanceResp -> Objects.equals(attendanceResp.getTotalDays(), value)).collect(Collectors.toList());
                }
                case "Total Offical Leave" -> {
                    return ans.stream().filter(attendanceResp -> Objects.equals(attendanceResp.getOfficialDayCnt(), value)).collect(Collectors.toList());
                }
                case "Total Sick Days" -> {
                    return ans.stream().filter(attendanceResp -> Objects.equals(attendanceResp.getSickDayCnt(), value)).collect(Collectors.toList());
                }
                case "Total Absent Days" -> {
                    return ans.stream().filter(attendanceResp -> Objects.equals(attendanceResp.getTotalDays() - attendanceResp.getPresentDayCnt(), value)).collect(Collectors.toList());
                }
                case "Total Present Days" -> {
                    return ans.stream().filter(attendanceResp -> Objects.equals(attendanceResp.getPresentDayCnt(), value)).collect(Collectors.toList());
                }
                case "Compliance Status" -> {
                    return ans.stream().filter(attendanceResp -> Objects.equals((attendanceResp.getPresentDayCnt() / attendanceResp.getTotalDays() > 0.7), value)).collect(Collectors.toList());
                }
            }
        }
        return ans;
    }

    @Override
    public List<AttendanceResponse> attStatCheckAndAdd( List<Integer> years, List<Student> stageOneStudents, int startMonth, int endMonth, int halfTimeLim, Boolean halfPeriodLeave,
    String comparison, int sickTarget, int officialLeaveTarget){
        List<AttendanceResponse> ans = new ArrayList<>();
        for(int year : years){
            for(Student student : stageOneStudents){
                int sickDayCnt = attendanceDao.getSickDays(startMonth, endMonth, year, student);
                int officialDayCnt = attendanceDao.getOfficialDays(startMonth, endMonth, year, student);
                int presentDayCnt = attendanceDao.getPresentDays(startMonth, endMonth, year, student);
                int totalDays = sickDayCnt + officialDayCnt + presentDayCnt;
                if((sickDayCnt + officialDayCnt < halfTimeLim && !halfPeriodLeave) || (sickDayCnt + officialDayCnt >= halfTimeLim && halfPeriodLeave)){
                    if(comparison.equals("equals") && sickDayCnt == sickTarget && officialDayCnt == officialLeaveTarget){
                        ans.add(new AttendanceResponse(student, year, totalDays, sickDayCnt, officialDayCnt, presentDayCnt));
                    }
                    else if(comparison.equals("less than") && sickDayCnt < sickTarget && officialDayCnt < officialLeaveTarget){
                        ans.add(new AttendanceResponse(student, year, totalDays, sickDayCnt, officialDayCnt, presentDayCnt));
                    }
                    else if(comparison.equals("greater than") && sickDayCnt > sickTarget && officialDayCnt > officialLeaveTarget){
                        ans.add(new AttendanceResponse(student, year, totalDays, sickDayCnt, officialDayCnt, presentDayCnt));
                    }
                }
            }
        }
        return ans;
    }

    @Override
    public double attendancePercentage(int startMonth, int endMonth, int year, Student student){
        int sickDayCnt = attendanceDao.getSickDays(startMonth, endMonth, year, student);
        int officialDayCnt = attendanceDao.getOfficialDays(startMonth, endMonth, year, student);
        int presentDayCnt = attendanceDao.getPresentDays(startMonth, endMonth, year, student);
        int totalDays = sickDayCnt + officialDayCnt + presentDayCnt;

        return presentDayCnt/totalDays;
    }
}

