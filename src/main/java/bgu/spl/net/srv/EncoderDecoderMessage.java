package bgu.spl.net.srv;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.srv.Messages.*;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class EncoderDecoderMessage implements MessageEncoderDecoder<Message> {
    private byte[] bytes = new byte[1 << 10]; //start with 1k
    private int len = 0;
    private int curr=2; //indicates from where we need to print in pop string
    private int count=0; //conter to know how many popstring we did
    private short k=0; //op
    private String username=""; //username we might get from the message
    private Message r= null; //message to return


    @Override
    public Message decodeNextByte(byte nextByte) {
        if (len == 2) { //got bytes for op
            k = bytesToShort(bytes);
        }
        if (k==4) {
            r=new LogoutMsg(k);
            resetFields();//function we wrote to clear all fields and start over
            return r;
        }
        if (k==11) {
            r=new MyCoursesMsg(k);
            resetFields();
            return r;
        }
        if (k == 5 || k == 6 || k == 7 || k == 9 || k == 10) { //handling this cases the same way(reading 4 bytes)
            if (len == 3) {
                byte[] b = {bytes[2], nextByte};
                short num = bytesToShort(b);//reading the other 2 bytes for course number
                if (k == 5)
                    r= new CourseRegMsg(k,num);
                if (k == 6)
                    r= new KdamCheckMsg(k,num);
                if (k == 7)
                    r= new CourseStatMsg(k,num);
                if (k == 9)
                    r= new IsRegisteredMsg(k,num);
                if (k==10)
                    r=new UnregisterMsg(k,num);
                resetFields();
                return r;
            }
        }
        if (nextByte == '\0'&&len>=2&&(k==8||k==1||k==2||k==3)) { //if we have string of username/password, and tells us when we need to do popstring
            if (k == 8) { //only username needed
                r= new StudentStatMsg(k,popString());
                resetFields();
                return r;
            }
            String password;
            if (count == 0) { //if we did not got the username yet
                username = popString();
                count++;
            } else { //if count =1
                password = popString();
                if (k == 1)
                    r= new AdminRegMsg(k,username, password);
                if (k == 2)
                    r= new StudentRegMsg(k,username, password);
                if (k == 3)
                    r= new LoginMsg(k,username, password);
                resetFields();
                return r;
            }
        }
            pushByte(nextByte);
            return null;
        }


    @Override
    public byte[] encode(Message message) {
        byte[] bytesOut = new byte[1 << 10]; //the array we will return Eventually
        short myOp= message.getOp();
        byte[] myOpByte= shortToBytes(myOp);
        bytesOut[0]=myOpByte[0];
        bytesOut[1]=myOpByte[1];
        if (myOp==12){ //ACK message
            short msgOp=((AckMsg)message).getMsgOpcode();
            byte[] msgByte= shortToBytes(msgOp); //the message op
            bytesOut[2]=msgByte[0];
            bytesOut[3]=msgByte[1];
            String back=((AckMsg)message).getBackMsg(); //the message we might ger back as pre course list etc..
            byte[] msg= back.getBytes(StandardCharsets.UTF_8);
            int j;
            for( j=0;j<back.length();j++){ //adding for the output array the string we converted
                bytesOut[j+4]=msg[j];
            }
            bytesOut[j+4]=0x00;
        }else { //error
            short msgOp=((ErrorMsg)message).getSenderOPcode();
            byte[] msgByte= shortToBytes(msgOp);
            bytesOut[2]=msgByte[0];
            bytesOut[3]=msgByte[1];
        }
        return bytesOut;

    }

    private void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }

        bytes[len++] = nextByte;
    }

    private String popString() {
        //notice that we explicitly requesting that the string will be decoded from UTF-8
        //this is not actually required as it is the default encoding in java.
            String result = new String(bytes, curr, len-curr, StandardCharsets.UTF_8);
            curr=len; //start poping from the new spot
            return result;
        }
    public short bytesToShort(byte[] byteArr)
    {
        short result = (short)((byteArr[0] & 0xff) << 8);
        result += (short)(byteArr[1] & 0xff);
        return result;
    }
    public byte[] shortToBytes(short num)
    {
        byte[] bytesArr = new byte[2];
        bytesArr[0] = (byte)((num >> 8) & 0xFF);
        bytesArr[1] = (byte)(num & 0xFF);
        return bytesArr;
    }



        public void resetFields(){
        len=0;
        curr=2;
        k=0;
        count=0;
        bytes = new byte[1 << 10];
        }
}

