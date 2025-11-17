package Use_Case.EnterInfo;

import java.util.List;
import java.util.Map;

/**
 * Data container that aggregates all the data received from view
 * from the dropdown menus.
 */
public class EnterInfoInputData {

    private final List<String> selectCourses;
    private final List<String> selectPrograms;
    private final int selectYear;

    /**
     * Constructor for new data object.
     * @param selectCourses List of all selected courses (code to title).
     * @param selectPrograms List of all selected program user-friendly IDs (combines name and type).
     * @param selectYear Integer year of study selected by the student.
     */
    public EnterInfoInputData(List<String> selectCourses, List<String> selectPrograms, int selectYear) {
        this.selectCourses =  selectCourses;
        this.selectPrograms = selectPrograms;
        this.selectYear = selectYear;
    }

    public List<String> getCourses() {
        return selectCourses;
    }
    public List<String> getPrograms() {
        return selectPrograms;
    }
    public int getSelectYear() {return selectYear; }
}
