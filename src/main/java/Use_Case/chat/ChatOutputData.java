package Use_Case.chat;

import java.util.ArrayList;

public class ChatOutputData {
    ArrayList<String> log;
    ArrayList<String> notifications;
    public ChatOutputData(ArrayList<String> history,ArrayList<String> noti){
        log = history;
        notifications = noti;
    }

    public ArrayList<String> getLog() {
        return log;
    }

    public ArrayList<String> getNotifications() {
        return notifications;
    }
}
