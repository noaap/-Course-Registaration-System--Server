package bgu.spl.net.srv.Messages;

import bgu.spl.net.srv.Course;
import bgu.spl.net.srv.MessageProtocol;
import bgu.spl.net.srv.Student;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class MyCoursesMsg extends Message {
    public MyCoursesMsg(short op) {
        super(op);
    }

    public Message act(MessageProtocol protocol) {
        if (protocol.getLoggedName() == null || data.getUsers().get(protocol.getLoggedName()).getIsAdmin())//no one is logged in/user not an admin
            return new ErrorMsg(getOp());
            Student student = (Student) data.getUsers().get(protocol.getLoggedName());
            String out = "[";
        ConcurrentHashMap<Short, Course> h = student.getCourses();
        Course[] arr = new Course[data.getCourses().size()+1];
        for (Short key : h.keySet()) { //a wat to organize the course number as in the courses file
            arr[h.get(key).getOrder()] = h.get(key);
        }
        for (int i = 1; i < arr.length; i++)
            if (arr[i] != null)
                out = out + arr[i].getCourseNum() + ",";//getting the places as theyre in the order we got in the courser text
            if (out.length()!= 1) //to check if we need to remove the last ','
                out = out.substring(0, out.length() - 1);
            out = out + "]";
            return new AckMsg(getOp(), out);
    }
}