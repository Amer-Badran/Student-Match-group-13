package use_case.notification;

import java.util.ArrayList;

public class NotificationOutputData {
    private String username;
    private String receiver;
    private ArrayList<String> log;

    public NotificationOutputData(String name,String receiver,ArrayList<String> logs){
        this.username = name;
        this.log = logs;
        this.receiver = receiver;

    }

    public String getUsername() {
        return username;
    }

    public ArrayList<String> getLog() {
        return log;
    }

    public String getReceiver() {
        return receiver;
    }
}

