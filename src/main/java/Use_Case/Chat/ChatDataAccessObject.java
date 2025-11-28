package Use_Case.Chat;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public interface ChatDataAccessObject {

    void removeUserFromNotificatoin(String current,String other) throws IOException, ParseException;
    void updatePeopleMessages(String Sender, String Receiver,String message) throws IOException, ParseException;
    void addSenderToNotification(String Sender, String Receiver) throws IOException, ParseException;
    ArrayList<String> getNotification(String username) throws IOException, ParseException;
    ArrayList<String> getMessages(String username,String receiver) throws IOException, ParseException;

}
