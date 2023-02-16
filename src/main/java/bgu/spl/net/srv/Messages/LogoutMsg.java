package bgu.spl.net.srv.Messages;

import bgu.spl.net.srv.MessageProtocol;
import bgu.spl.net.srv.User;

public class LogoutMsg extends Message{
    public LogoutMsg(short op){
        super(op);
    }
    public Message act(MessageProtocol protocol) {
        if (protocol.getLoggedName() != null) //no user logged in
            synchronized (data.getUsers().get(protocol.getLoggedName())) {
            User userToLogout = data.getUsers().get(protocol.getLoggedName());
            userToLogout.setActive(false);
            protocol.setUserName(null);
            protocol.setTerminate(true);
            return new AckMsg(getOp(), "");
        }
        return new ErrorMsg(getOp());
    }
    }

