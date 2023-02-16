package bgu.spl.net.srv.Messages;

public class ErrorMsg extends Message{
    private short myOP=13;
    private short senderOPcode;
    public ErrorMsg(short senderOPcode){
        super((short) 13);
        this.senderOPcode=senderOPcode;
    }

    public short getSenderOPcode() {
        return senderOPcode;
    }


}
