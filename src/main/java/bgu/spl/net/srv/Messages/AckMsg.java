package bgu.spl.net.srv.Messages;

public class AckMsg extends Message{
    private  short msgOpcode;
    private String backMsg;
    public AckMsg(short msgOpcode,String backMsg){
        super((short) 12);
        this.msgOpcode=msgOpcode;
        this.backMsg=backMsg;
    }

    public short getMsgOpcode() {
        return msgOpcode;
    }

    public String getBackMsg() {
        return backMsg;
    }


}
