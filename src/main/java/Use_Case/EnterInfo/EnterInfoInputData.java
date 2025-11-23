package Use_Case.EnterInfo;

import java.util.List;
import java.util.Map;

/**
 * Data container that aggregates all the data received from view
 * from the dropdown menus.
 */
public class EnterInfoInputData {

    private final List<String> selectCourses;
    private final Map<String,String> selectPrograms;
    private final int selectYear;
    private final List<String> selectedHobbies;
    private final List<String> selectedLanguages;
    private final Map<String, Double> selectedWeights;
    private final String username;
    /**
     * Constructor for new data object.
     * @param selectCourses List of all selected courses (code to title).
     * @param selectPrograms List of all selected program user-friendly IDs (combines name and type).
     * @param selectYear Integer year of study selected by the student.
     */
    public EnterInfoInputData(String username,List<String> selectCourses,Map<String,String> selectPrograms, int selectYear,
                              List<String> selectedHobbies, List<String> selectedLanguages,
                              Map<String, Double> selectedWeights) {
        this.username = username;
        this.selectCourses =  selectCourses;
        this.selectPrograms = selectPrograms;
        this.selectYear = selectYear;
        this.selectedHobbies = selectedHobbies;
        this.selectedLanguages = selectedLanguages;
        this.selectedWeights = selectedWeights;
    }

    public List<String> getCourses() {
        return selectCourses;
    }
    public Map<String,String> getPrograms() {
        return selectPrograms;
    }
    public int getSelectYear() {return selectYear; }

    public List<String> getSelectedHobbies() {
        return selectedHobbies;
    }

    public List<String> getSelectedLanguages() {
        return selectedLanguages;
    }

    public Map<String, Double> getSelectedWeights() {
        return selectedWeights;
    }
    public String getUsername(){return username;}
}
