package bgu.spl.net.srv.Messages;

import bgu.spl.net.srv.Course;
import bgu.spl.net.srv.MessageProtocol;
import bgu.spl.net.srv.Student;

public class UnregisterMsg extends Message {
    private short courseNumber;

    public UnregisterMsg(short op, short courseNumber) {
        super(op);
        this.courseNumber = courseNumber;
    }
    public Message act(MessageProtocol protocol) {
        if (protocol.getLoggedName() == null || data.getUsers().get(protocol.getLoggedName()).getIsAdmin() || !data.getCourses().containsKey(courseNumber))
            return new ErrorMsg(getOp());
        Student student = (Student) data.getUsers().get(protocol.getLoggedName());
        if (!student.getCourses().containsKey(courseNumber))
            return new ErrorMsg(getOp());
        synchronized (data.getCourses().get(courseNumber)) { //needed because we handle course fields(like num of students) without it could be a contact switch that will get us false answer
            Course c = data.getCourses().get(courseNumber);
            c.setCurrentNumOfStudents(c.getCurrentNumOfStudents() - 1);
            student.getCourses().remove(courseNumber, c);
                c.getStudentsInCourse().remove(student.getUserName());
            return new AckMsg(getOp(), "");
        }
    }
}