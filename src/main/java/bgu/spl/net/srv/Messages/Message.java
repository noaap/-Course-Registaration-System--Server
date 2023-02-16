package bgu.spl.net.srv.Messages;

import bgu.spl.net.srv.Database;
import bgu.spl.net.srv.MessageProtocol;

public abstract class Message {
    protected Database data=Database.getInstance();
    private short op; //my op code' getting from enc/dec
    public Message(short op){
        this.op=op;
    }
    public  Message act(MessageProtocol prot){return null;}

    public short getOp() {
        return op;
    }


}
