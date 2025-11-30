package Use_Case.announcement;

public class AnnouncementInputData {

    private String message;
    private String username;
    public AnnouncementInputData(String username,String M){

        this.message = M;
        this.username = username;
    }


    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }
}
