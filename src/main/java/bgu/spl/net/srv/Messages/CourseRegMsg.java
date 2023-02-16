package bgu.spl.net.srv.Messages;

import bgu.spl.net.srv.*;

import java.util.List;

public class CourseRegMsg extends Message{
    private short courseNumber;
    private Database data=Database.getInstance();
    public CourseRegMsg(short op,short courseNumber){
        super(op);
        this.courseNumber=courseNumber;
    }
    public Message act(MessageProtocol protocol) {
        if(protocol.getLoggedName()!=null&&data.getCourses().get(courseNumber)!=null&&data.getUsers().get(protocol.getLoggedName())!=null) {
            synchronized (data.getCourses().get(courseNumber)) { //sync on the selected course, especially for the max student
                User user = data.getUsers().get(protocol.getLoggedName());
                Course c = data.getCourses().get(courseNumber); //getting the course from the course map
                int leftIncCurse = c.getMaxStudents() - c.getCurrentNumOfStudents();
                if (user == null || user.getIsAdmin() || leftIncCurse <= 0)//no user connected/the user is admin/no place in course
                    return new ErrorMsg(getOp());
                Student student = (Student) user;//no we know he is a student
                if ( student.getCourses().containsKey(courseNumber))//already registered
                    return new ErrorMsg(getOp());
                List<Short> list=data.getCourses().get(courseNumber).getPreCourses();
                for (Short s:list) {//checks for having all the pre courses needed
                    if (!student.getCourses().containsKey(s))
                        return new ErrorMsg(getOp());
                }
                c.setCurrentNumOfStudents(c.getCurrentNumOfStudents() + 1);
                student.getCourses().put(courseNumber, c);
                c.getStudentsInCourse().add(student.getUserName());
                return new AckMsg(getOp(), "");
            }
        }
        return new ErrorMsg(getOp());
    }
    }

