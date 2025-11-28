package Use_Case.Notification;

public class NotificationInputData {
    private String username;
    private String other ;

    public NotificationInputData(String name,String other){
        this.username = name;
        this.other = other;
    }

    public String getUsername() {
        return username;
    }

    public String getOther() {
        return other;
    }
}
