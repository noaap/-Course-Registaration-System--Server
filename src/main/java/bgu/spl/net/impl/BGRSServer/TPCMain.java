package bgu.spl.net.impl.BGRSServer;


import bgu.spl.net.srv.*;
import bgu.spl.net.srv.Messages.Message;

import java.io.IOException;

public class TPCMain {
    public static void main(String[] args) {
            Database database = Database.getInstance();
            database.initialize("./Courses.txt");
            Server.threadPerClient(
                    Integer.parseInt(args[0]), //port
                    () -> new MessageProtocol(database), //protocol factory
                    EncoderDecoderMessage::new //message encoder decoder factory
            ).serve();
        }
}
