package Use_Case.Notification;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public interface NotificationInputBoundary {
    void switchToHomeView();
    void prepareFailView(String error);
    void execute(NotificationInputData input) throws IOException, ParseException;
    ArrayList<String> getNotification(String username) throws IOException, ParseException;
}
