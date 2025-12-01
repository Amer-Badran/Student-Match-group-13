package use_case.dashboard;

import java.util.ArrayList;

public class DashboardOutputData {
    private ArrayList<String> announcemnts;
    public DashboardOutputData(ArrayList<String> announcemnts){
        this.announcemnts =announcemnts;
    }

    public ArrayList<String> getAnnouncemnts() {
        return announcemnts;
    }
}
