package bgu.spl.net.srv.Messages;

import bgu.spl.net.srv.Admin;
import bgu.spl.net.srv.MessageProtocol;

public class AdminRegMsg extends Message {
    private String userName;
    private String password;
    public AdminRegMsg(short op, String userName,String password){
        super(op);
        this.userName=userName;
        this.password=password;
    }
    public Message act(MessageProtocol protocol){
           if (data.getUsers().containsKey(userName)||protocol.getLoggedName()!=null)
               return new ErrorMsg(getOp());
           if( data.getUsers().putIfAbsent(userName, new Admin(userName, password))!=null)//if we try to put and somebody else already got ahead of us
           return new ErrorMsg(getOp());
        return new AckMsg(getOp(), "");
       }
    }

