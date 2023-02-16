package bgu.spl.net.srv.Messages;

import bgu.spl.net.srv.*;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class StudentStatMsg extends Message{
    private String userName;
    public StudentStatMsg(short op,String userName) {
        super(op);
        this.userName=userName;
    }
    public Message act(MessageProtocol protocol) {
        Student studentToCheck;
        if (protocol.getLoggedName()==null||data.getUsers().get(protocol.getLoggedName())==null|| !data.getUsers().get(protocol.getLoggedName()).getIsAdmin())
            return new ErrorMsg(getOp());
        if (data.getUsers().get(userName) == null || data.getUsers().get(userName).getIsAdmin())
            return new ErrorMsg(getOp());
            studentToCheck = (Student) data.getUsers().get(userName);//no we know hes a student
            String out = "Student: " + userName + '\n' + "Courses: [";
            int size = out.length();
        ConcurrentHashMap<Short, Course> h = studentToCheck.getCourses();
        Course[] arr = new Course[data.getCourses().size()+1]; // a way to ger the courses in the right order as courses file
        for (Short key : h.keySet()) {
            arr[h.get(key).getOrder()] = h.get(key);
        }
        for (int i = 1; i < arr.length; i++)
        if (arr[i] != null)
            out = out + arr[i].getCourseNum() + ",";
            if (out.length() != size) //to check if we need to remove the last ','
                out = out.substring(0, out.length() - 1);
            out = out + "]";
            return new AckMsg(getOp(), out);
        }
    }

