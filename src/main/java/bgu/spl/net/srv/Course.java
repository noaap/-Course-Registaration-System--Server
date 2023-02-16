package bgu.spl.net.srv;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Course {
    private short courseNum;
    private String courseName;
    private List<Short> preCourses;
    private int maxStudents;
    private int currentNumOfStudents;
    private ArrayList<String> studentsInCourse;
    private int order;
    public Course (short courseNum,String courseName, List <Short>preCourses,int maxStudents,int order){
        this.courseNum=courseNum;
        this.courseName=courseName;
        this.preCourses=preCourses;
        this.maxStudents=maxStudents;
        this.currentNumOfStudents=0;
        studentsInCourse=new ArrayList<>();
        this.order=order;
    }

    public short getCourseNum() {
        return courseNum;
    }

    public String getCourseName() {
        return courseName;
    }

    public List<Short> getPreCourses() {
        return preCourses;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public int getCurrentNumOfStudents() {
        return currentNumOfStudents;
    }


    public void setCurrentNumOfStudents(int currentNumOfStudents) {
        this.currentNumOfStudents = currentNumOfStudents;
    }

    public ArrayList< String> getStudentsInCourse() {
        return studentsInCourse;
    }
    public int getOrder() {
        return order;
    }
}
