package bgu.spl.net.srv.Messages;

import bgu.spl.net.srv.MessageProtocol;
import bgu.spl.net.srv.Student;

public class IsRegisteredMsg extends Message{
    private short courseNumber;
    public IsRegisteredMsg(short op,short courseNumber){
        super(op);
        this.courseNumber=courseNumber;
    }
    public Message act(MessageProtocol protocol){
        if (protocol.getLoggedName()==null||data.getUsers().get(protocol.getLoggedName()).getIsAdmin()||!data.getCourses().containsKey(courseNumber))
            return new ErrorMsg(getOp());
        Student student=(Student) data.getUsers().get(protocol.getLoggedName());
        if (student.getCourses().containsKey(courseNumber))
            return new AckMsg(getOp(),"REGISTERED");
        return new AckMsg(getOp(),"NOT REGISTERED");
    }
}
