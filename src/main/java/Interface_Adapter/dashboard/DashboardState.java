package Interface_Adapter.dashboard;

import java.util.ArrayList;

public class DashboardState {
    public String username;
    public ArrayList<String> notification;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNotification(ArrayList<String> notification) {
        this.notification = notification;
    }

    public ArrayList<String> getNotification() {
        return notification;
    }
}
