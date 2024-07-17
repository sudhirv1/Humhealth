package com.Humhealth.Marks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Humhealth.Student.Student;
import com.Humhealth.Student.StudentDao;

@Service
public class MarksServiceImpl implements MarksService {
    private MarksDao dao;
    private StudentDao studentDao;

    @Override
    public void saveMarks(Long studentId, int year, int quarter, double tamil, double english, double maths, double science, double socialScience) {
        Marks marks = new Marks(year, quarter, tamil, english, maths, science, socialScience);
        Student student = studentDao.getStudentById(studentId);
        marks.setStudent(student);
        //Add infor about the student from student id
        dao.save(marks);
    }

    @Override
    public Marks findMarksById(Long id) {
        return dao.findById(id);
    }

    @Override
    public List<Marks> findAllMarks() {
        return dao.getAll();
    }

    @Override
    public List<Marks> findMarksByStudentId(Long studentId){
        return dao.findMarksByStudentId(studentId);
    }

    @Override
    public void updateMarks(Long id, int year, int quarter, double tamil, double english, double maths, double science, double socialScience) {
        Marks marks = findMarksById(id);
        marks.setYear(year);
        marks.setQuarter(quarter);
        marks.setTamil(tamil);
        marks.setEnglish(english);
        marks.setMaths(maths);
        marks.setScience(science);
        marks.setSocialScience(socialScience);
        
        dao.update(marks);
    }

    @Override
    public void deleteMarks(Marks marks) {
        dao.delete(marks);
    }

    @Override
    public Marks findMarksByYearQuarterStudent(int yearIn, int quarterIn, Long studentId){
        return dao.findMarksByYearQuarterStudent(yearIn, quarterIn, studentId);
    }

    @Override
    public List<MarksResponse> listMarkEntrys(List<Student> studentList, int year, List<String> periods){
        List<MarksResponse> ans = new ArrayList<>();
        //Output Marks record by year for each student by using getmakrsByYearQuarter()
        //For quarter outpuyt all 4
        //For Half-yearly add 1+2 and 3+4
        //For Annually do 1+2+3+4
        if(periods.get(0).equals("All")){
            periods = new ArrayList<>();
            periods.add("Quartely");
            periods.add("Half-Yearly");
            periods.add("Yearly");
        }
        for(String per : periods){
            for(Student student : studentList){
                switch (per) {
                    case "Quartely" -> {
                        ans.add(consolidateMarksObject(year, Arrays.asList(1), student.getId()));
                        ans.add(consolidateMarksObject(year, Arrays.asList(2), student.getId()));
                        ans.add(consolidateMarksObject(year, Arrays.asList(3), student.getId()));
                        ans.add(consolidateMarksObject(year, Arrays.asList(4), student.getId()));
                    }
                    case "Half-yearly" -> {
                        ans.add(consolidateMarksObject(year, Arrays.asList(1,2), student.getId()));
                        ans.add(consolidateMarksObject(year, Arrays.asList(3,4), student.getId()));
                    }
                    case "Yearly" -> ans.add(consolidateMarksObject(year, Arrays.asList(1,2,3,4), student.getId()));
                    default -> {
                    }
                }
            }
        }
        return ans;
    }

    @Override
    public MarksResponse consolidateMarksObject(int year, List<Integer> quarters, Long studentId){
        int n = quarters.size();

        int tamil = 0;
        int english = 0;
        int maths = 0;
        int science = 0;
        int socialScience = 0;
        int totalMarks = 0;

        String firstName = "";
        String lastName = "";
        for( int quarter : quarters){
            Marks mark = findMarksByYearQuarterStudent(year, quarter, studentId);
            firstName = mark.getStudent().getFirstName();
            lastName = mark.getStudent().getLastName();

            tamil += mark.getTamil();
            english += mark.getEnglish();
            maths += mark.getMaths();
            science += mark.getScience();
            socialScience += mark.getSocialScience();
            totalMarks += mark.getTotal();
        }

        tamil /= n;
        english /=n;
        maths /= n;
        science /= n;
        socialScience /= n;
        totalMarks /= n;

        return new MarksResponse(english, firstName, lastName, maths, science, socialScience, studentDao.getStudentById(studentId), tamil, totalMarks);
    }

    @Override
    public List<MarksResponse> computeMarksReport(String criteria, Object value, List<Student> students, int year, List<String> periods, String subjectTopper, Boolean overallTopper, String marksResult){
        List<MarksResponse> ans = new ArrayList<>();
        //Take the students list and then we have to filter their marks
        //Year and period will get us marks which will also let us figure out marksReuslt
        for(String period : periods){
            switch (period) {
                case "Quarterly" ->                     {
                        //Has to run 4 time for each quarter
                        int startMonth = 1;
                        int endMonth = 3;
                        int q = 1;
                        for(int i = 0; i < 3; i = i + 1){
                            List<Integer> quarters = Arrays.asList(q);
                            List<Student> res = marksReportAndPrint(year, quarters, students, startMonth, endMonth, subjectTopper);
                            
                            if(overallTopper){
                                res = marksReportAndPrint(year, quarters, students, startMonth, endMonth, "Total");
                            }
                            
                            for(Student student : res){
                                ans.add(consolidateMarksObject(year, quarters, student.getId()));
                            }
                            startMonth = endMonth+1;
                            endMonth+=3;
                            q++;
                        }                          }
                case "Half-yearly" ->                     {
                        //Has to run 2 times
                        int startMonth = 1;
                        int endMonth = 6;
                        List<Integer> quarters = Arrays.asList(1,2);
                        for(int i = 0; i < 1; i = i + 1){
                            List<Student> res = marksReportAndPrint(year, quarters, students, startMonth, endMonth, subjectTopper);
                            if(overallTopper){
                                res = marksReportAndPrint(year, quarters, students, startMonth, endMonth, "Total");
                            }
                            
                            for(Student student : res){
                                ans.add(consolidateMarksObject(year, quarters, student.getId()));
                            }
                            startMonth = endMonth+1;
                            endMonth+=6;
                            quarters = Arrays.asList(3,4);//switch quarters for the 2nd/final iteration of the loop
                        }                          }
                case "Yearly" ->                     {
                        int startMonth = 1;
                        int endMonth = 12;
                        List<Integer> quarters = Arrays.asList(1,2,3,4);
                        List<Student> res = marksReportAndPrint(year, quarters, students, startMonth, endMonth, subjectTopper);
                        if(overallTopper){
                            res = marksReportAndPrint(year, quarters, students, startMonth, endMonth, "Total");
                        }       for(Student student : res){
                            ans.add(consolidateMarksObject(year, quarters, student.getId()));
                        }                          }
                default -> {
                }
            }
        }

        if(criteria != null && value != null){
            switch(criteria){
                case "S.No" -> {
                    return ans.stream().filter(marksResp -> marksResp.getStudent().getId().equals(value)).collect(Collectors.toList());
                }
                case "Student Name" -> {
                    return ans.stream().filter(marksResp -> (marksResp.getStudent().getFirstName() + marksResp.getStudent().getLastName()) == value).collect(Collectors.toList());
                }
                case "Grade" -> {
                    return ans.stream().filter(marksResp -> marksResp.getStudent().getGrade() == (int)value).collect(Collectors.toList());
                }
                case "Tamil" -> {
                    return ans.stream().filter(marksResp -> Objects.equals(marksResp.getTamil(), value)).collect(Collectors.toList());
                }
                case "English" -> {
                    return ans.stream().filter(marksResp -> Objects.equals(marksResp.getEnglish(), value)).collect(Collectors.toList());
                }
                case "Maths" -> {
                    return ans.stream().filter(marksResp -> Objects.equals(marksResp.getMaths(), value)).collect(Collectors.toList());
                }
                case "Science" -> {
                    return ans.stream().filter(marksResp -> Objects.equals(marksResp.getScience(), value)).collect(Collectors.toList());
                }
                case "Social" -> {
                    return ans.stream().filter(marksResp -> Objects.equals(marksResp.getSocialScience(), value)).collect(Collectors.toList());
                }
                case "Total marks" -> {
                    return ans.stream().filter(marksResp -> Objects.equals(marksResp.getTotalMarks(), value)).collect(Collectors.toList());
                }
                case "Percentage" -> {
                    return ans.stream().filter(marksResp -> Objects.equals(marksResp.getTotalMarks()/500, value)).collect(Collectors.toList());
                }
                case "Result" -> {
                    return ans.stream().filter(marksResp -> Objects.equals(checkFailYear(year,marksResp.getStudent().getId()), value)).collect(Collectors.toList());
                }
            }
        }
        return ans;
    }

    @Override
    public List<Student> marksReportAndPrint(int year, List<Integer> quarters, List<Student> students, int startMonth, int endMonth, String subjectTopper){
        TreeMap<Double, List<Student>> mp = new TreeMap<>();
        //The best way to apply toppers would be to make a sorted map of students based on their subject scores
        for(Student student : students){
            double totalForKey = 0;
            for(int quarter : quarters){
                Marks mark = findMarksByYearQuarterStudent(year, quarter, student.getId());
                totalForKey += getSpecificMark(mark, subjectTopper);
            }
            mp.computeIfAbsent(totalForKey, k -> new ArrayList<>()).add(student);
        }
        Double topMark = mp.lastKey();
        List<Student> subjectTopperStudents = mp.get(topMark);

        return subjectTopperStudents;
    }

    @Override
    public double getSpecificMark(Marks mark, String subject){
        return switch (subject) {
            case "English" -> mark.getEnglish();
            case "Math" -> mark.getMaths();
            case "Science" -> mark.getScience();
            case "Social Science" -> mark.getSocialScience();
            case "Tamil" -> mark.getTamil();
            case "Total" -> mark.getTotal();
            default -> 0;
        };
    }

    @Override
    public Boolean checkFailYear(int year, Long studentId){
        for(int q = 1; q <= 4; q++){
            Marks mark = findMarksByYearQuarterStudent(year, q, studentId);
            if(mark.getTamil() < 50) return true;
            if(mark.getMaths() < 50) return true;
            if(mark.getScience() < 50) return true;
            if(mark.getSocialScience() < 50) return true;
            if(mark.getEnglish() < 50) return true;
        }
        return false;
    }

    //Duplicated most of the func. When I figure out what return types to use I should remove this func
    @Override
    public double yearTotalMarkPercent(int yearIn, Long studentId){
        int n = 4;

        int totalMarks = 0;

        for(int i = 0; i < 4; i++){
            Marks mark = findMarksByYearQuarterStudent(yearIn, i, studentId);
            totalMarks += mark.getTotal();
        }
        return totalMarks /= n;
    }
}
