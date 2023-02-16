package bgu.spl.net.srv.Messages;

import bgu.spl.net.srv.MessageProtocol;
import bgu.spl.net.srv.Student;

public class StudentRegMsg extends Message{
    private String userName;
    private String password;
    public StudentRegMsg(short op,String userName,String password){
        super(op);
        this.userName=userName;
        this.password=password;
    }
    public Message act(MessageProtocol protocol){
            if (data.getUsers().containsKey(userName)||protocol.getLoggedName()!=null)
                return new ErrorMsg(getOp());
             if(data.getUsers().putIfAbsent(userName, new Student(userName, password))!=null)//if someone got ahead of us' return an error
               return new ErrorMsg(getOp());
        return new AckMsg(getOp(), "");

        }
    }

