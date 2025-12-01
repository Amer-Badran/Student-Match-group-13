package use_case.enterInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Data container that aggregates all the data that need to be passed to view
 * for the dropdown menus.
 */
public class EnterInfoOutputData {

    private final Map<String, String> courses;
    private final List<String> programs;
    private final List<Integer> yearsStudy;
    private final ArrayList<String> notif;
    /**
     * Constructor for new data object.
     * @param courses Map of all available courses (code to title).
     * @param programs List of all available program user-friendly IDs (combines name and type).
     * @param yearsStudy List of all available year of Study options.
     */
    public EnterInfoOutputData(Map<String, String> courses, List<String> programs,
                               List<Integer> yearsStudy,ArrayList<String> noti) {
        this.courses = courses;
        this.programs = programs;
        this.yearsStudy = yearsStudy;
        this.notif = noti;

    }

    public Map<String, String> getCourses() {
        return courses;
    }
    public List<String> getPrograms() {
        return programs;
    }
    public List<Integer> getYearsStudy() {
        return yearsStudy;
    }
    public ArrayList<String> getNotif() {return notif;}
}
