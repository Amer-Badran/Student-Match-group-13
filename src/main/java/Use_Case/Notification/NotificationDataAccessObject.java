package Use_Case.Notification;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public interface NotificationDataAccessObject {

    ArrayList<String> getNotification(String username) throws IOException, ParseException;
    boolean UserExists(String username) throws IOException, ParseException;
    void CheckNewChat(String Sender,String Receiver) throws IOException, ParseException;
    ArrayList<String> getMessages(String username,String receiver) throws IOException, ParseException;
    void removeUserFromNotificatoin(String current,String other) throws IOException, ParseException;


}
