package interface_adapter.announcement;

import java.util.ArrayList;

public class AnnouncementState {
    private ArrayList<String> announcementList;
    private String error;
    private String username;
    public void setAnnouncementList(ArrayList<String> announcementList) {
        this.announcementList = announcementList;
    }

    public ArrayList<String> getAnnouncementList() {
        return announcementList;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
