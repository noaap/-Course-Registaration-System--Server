package bgu.spl.net.srv;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Student extends User{
    private ConcurrentHashMap<Short,Course> courses;

    public Student(String userName,String password){
        super(userName, password);
        this.courses=new ConcurrentHashMap<>();
        isAdmin=false;
    }

    public ConcurrentHashMap<Short, Course> getCourses() {
        return courses;
    }


}
