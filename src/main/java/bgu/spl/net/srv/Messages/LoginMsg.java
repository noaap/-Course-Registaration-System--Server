package bgu.spl.net.srv.Messages;

import bgu.spl.net.srv.MessageProtocol;
import bgu.spl.net.srv.User;

public class LoginMsg extends Message {
    private String userName;
    private String password;

    public LoginMsg(short op, String userName, String password) {
        super(op);
        this.userName = userName;
        this.password = password;
    }

    public Message act(MessageProtocol protocol) {
        if (data.getUsers().get(userName) != null) {//if no such user
            synchronized (data.getUsers().get(userName)) {
                User u = data.getUsers().get(userName);
                if (protocol.getLoggedName() == null && u.getPassword().equals(password) && !data.getUsers().get(userName).getIsActive()) {
                    u.setActive(true);//user flag for logging in
                    protocol.setUserName(userName); //set the user as the logged in to the specific client
                    return new AckMsg(getOp(), "");
                }
            }
        }
        return new ErrorMsg(getOp());
    }
}