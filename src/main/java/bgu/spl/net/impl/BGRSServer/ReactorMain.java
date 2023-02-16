package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.srv.Database;
import bgu.spl.net.srv.EncoderDecoderMessage;
import bgu.spl.net.srv.MessageProtocol;
import bgu.spl.net.srv.Messages.Message;
import bgu.spl.net.srv.Server;

public class ReactorMain {
    public static void main(String[]args){
        Database database=Database.getInstance();
        database.initialize("./Courses.txt");
        Server.reactor(
                Integer.parseInt(args[1]),
               Integer.parseInt(args[0]) , //port
                () ->  new MessageProtocol(database), //protocol factory
                EncoderDecoderMessage::new //message encoder decoder factory
        ).serve();

    }
}
