package bgu.spl.net.srv;

import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.srv.Messages.AckMsg;
import bgu.spl.net.srv.Messages.ErrorMsg;
import bgu.spl.net.srv.Messages.LoginMsg;
import bgu.spl.net.srv.Messages.Message;

public class MessageProtocol implements MessagingProtocol<Message> {
    private String userName; //indicates the username that is in the system for the specific connectionHandler
    private Database database;
    private boolean terminate=false;
    public MessageProtocol(Database database){
        this.database=database;
        userName=null;
    }
    @Override
    public Message process(Message msg) {
       return msg.act(this);
    }

    @Override
    public boolean shouldTerminate() {
        return terminate;
    }

    public void setTerminate(boolean b){
        terminate=b;
    }

    public String getLoggedName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
