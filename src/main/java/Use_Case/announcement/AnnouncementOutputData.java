package Use_Case.announcement;

import java.util.ArrayList;

public class AnnouncementOutputData {

    ArrayList<String> log;
    public AnnouncementOutputData(ArrayList<String> history){
        log = history;
    }

    public ArrayList<String> getLog() {
        return log;
    }


}
