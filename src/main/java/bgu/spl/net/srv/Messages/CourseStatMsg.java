package bgu.spl.net.srv.Messages;
import java.util.Comparator;

import bgu.spl.net.srv.*;

import java.util.ArrayList;


public class CourseStatMsg extends Message {
    private short courseNumber;

    public CourseStatMsg(short op, short courseNumber) {
        super(op);
        this.courseNumber = courseNumber;
    }

    public Message act(MessageProtocol protocol) {

        if (protocol.getLoggedName() == null || data.getCourses().get(courseNumber) == null || !data.getUsers().get(protocol.getLoggedName()).getIsAdmin())
            return new ErrorMsg(getOp());
            Course c = data.getCourses().get(courseNumber);
            String out = "Course: (" + courseNumber + ") " + c.getCourseName() + '\n' + "Seats Available: " +(c.getMaxStudents()- c.getCurrentNumOfStudents()) + "/" + c.getMaxStudents() + '\n' +
                    "Students Registered: [";
        ArrayList<String> stuList;
            synchronized (c.getStudentsInCourse()) {
                stuList = c.getStudentsInCourse();
                stuList.sort(Comparator.naturalOrder()); //as requested, getting alphabet order for student list
            }
            for (int i = 0; i < stuList.size(); i++) {
                if (i == stuList.size() - 1)
                    out = out + stuList.get(i);
                else
                    out = out + stuList.get(i) + ",";
            }
            out = out + "]";
            return new AckMsg(getOp(), out);
        }
   }

