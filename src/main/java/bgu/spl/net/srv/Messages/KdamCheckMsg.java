package bgu.spl.net.srv.Messages;

import bgu.spl.net.srv.Course;
import bgu.spl.net.srv.Database;
import bgu.spl.net.srv.MessageProtocol;

import javax.swing.text.html.HTMLDocument;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class KdamCheckMsg extends Message {
    private short courseNumber;

    public KdamCheckMsg(short op, short courseNumber) {
        super(op);
        this.courseNumber = courseNumber;
    }

    public Message act(MessageProtocol protocol) {

        if (protocol.getLoggedName() == null || data.getCourses().get(courseNumber) == null || data.getUsers().get(protocol.getLoggedName()).getIsAdmin())
            return new ErrorMsg(getOp());
            Course c = data.getCourses().get(courseNumber);
            String out = "[";
            List<Short> kdam;
            kdam = c.getPreCourses();
            Course[] arr = new Course[data.getCourses().size() + 1];
            ; // a way to ger the courses in the right order as courses file
            ConcurrentHashMap<Short, Course> courses = data.getCourses();
            for (int i = 0; i < kdam.size(); i++) {
                Course cKdam = courses.get(kdam.get(i));
                arr[cKdam.getOrder()] = cKdam;
            }
            for (int i = 1; i < arr.length; i++) {
                if (arr[i] != null)
                    out = out + arr[i].getCourseNum() + ",";
            }
            if (out.length() != 1) //to check if we need to remove the last ','
                out = out.substring(0, out.length() - 1);
            out = out + "]";
            return new AckMsg(getOp(), out);
        }
    }
