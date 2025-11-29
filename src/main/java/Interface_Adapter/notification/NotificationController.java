package Interface_Adapter.notification;

import Use_Case.notification.NotificationInputBoundary;
import Use_Case.notification.NotificationInputData;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class NotificationController {
    private final NotificationInputBoundary interactor;
    public NotificationController(NotificationInputBoundary interactor){
        this.interactor = interactor;
    }
   public ArrayList<String> getNotification(String username) throws IOException, ParseException {
        return interactor.getNotification(username);
   }
   public void execute(String sender,String other) throws IOException, ParseException {
       NotificationInputData input = new NotificationInputData(sender,other);
        interactor.execute(input);
   }
   public void switchToHomeView(){
        interactor.switchToHomeView();
   }

}
