package Interface_Adapter.Notification;

import java.util.ArrayList;

public class NotificationState {
    private String username;
    private String error;
    private ArrayList<String> notification;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getUsername() {
        return username;
    }

    public String getError() {
        return error;
    }

    public ArrayList<String> getNotification() {
        return notification;
    }

    public void setNotification(ArrayList<String> notification) {
        this.notification = notification;
    }
}
