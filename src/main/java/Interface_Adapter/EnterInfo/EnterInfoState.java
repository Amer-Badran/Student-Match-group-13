package Interface_Adapter.EnterInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A "snapshot" of all data needed for the Enter Info screen.
 */
public class EnterInfoState {

    // Fields for all available options.
    private Map<String, String> allCourses = new HashMap<>();
    private List<String> allPrograms = new ArrayList<>();
    private List<Integer> allYears = new ArrayList<>();
    private List<String> allHobbies = new ArrayList<>();
    private List<String> allLanguages = new ArrayList<>();
    private Map<String, Double>  allWeights = new HashMap<>();

    // Fields for the user's current selections.
    private List<String> selectedCourses = new ArrayList<>();
    private List<String> selectedPrograms = new ArrayList<>();
    private int selectedYear = 1;
    private List<String> selectedHobbies = new ArrayList<>();
    private List<String> selectedLanguages = new ArrayList<>();
    private Map<String, Double> selectedWeights =  new HashMap<>();
    private String username = "";
    private String saveMessage = "";
    private String failedSaveMessage = "";

    /**
     * Copy constructor: Creates a new state by copying an old one.
     * This is for making changes without modifying the old state.
     */
    public EnterInfoState(EnterInfoState copy) {
        this.allCourses = copy.allCourses;
        this.allPrograms = copy.allPrograms;
        this.allYears = copy.allYears;
        this.allHobbies = copy.allHobbies;
        this.allLanguages = copy.allLanguages;
        this.allWeights = copy.allWeights;
        this.selectedCourses = copy.selectedCourses;
        this.selectedPrograms = copy.selectedPrograms;
        this.selectedYear = copy.selectedYear;
        this.selectedHobbies = copy.selectedHobbies;
        this.selectedLanguages = copy.selectedLanguages;
        this.selectedWeights = copy.selectedWeights;
        this.saveMessage = copy.saveMessage;
        this.failedSaveMessage = copy.failedSaveMessage;
    }

    /**
     * Default constructor: Creates a blank state.
     */
    public EnterInfoState() {}

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
    // Getters functions.

    public Map<String, String> getAllCourses() { return allCourses; }
    public List<String> getAllPrograms() { return allPrograms; }
    public List<Integer> getAllYears() { return allYears; }
    public List<String> getAllHobbies() { return allHobbies; }
    public List<String> getAllLanguages() { return allLanguages; }
    public Map<String, Double> getAllWeights() { return allWeights; }
    public List<String> getSelectedCourses() { return selectedCourses; }
    public List<String> getSelectedPrograms() { return selectedPrograms; }
    public int getSelectedYear() { return selectedYear; }
    public List<String> getSelectedHobbies() { return selectedHobbies; }
    public List<String> getSelectedLanguages() { return selectedLanguages; }
    public Map<String, Double> getSelectedWeights() { return selectedWeights; }
    public String getSaveMessage() { return saveMessage; }
    public String getFailedSaveMessage() { return failedSaveMessage; }

    // Setter functions.
    // Used by Presenter to update the state.

    public void setAllCourses(Map<String, String> allCourses) { this.allCourses = allCourses; }
    public void setAllPrograms(List<String> allPrograms) { this.allPrograms = allPrograms; }
    public void setAllYears(List<Integer> allYears) { this.allYears = allYears; }
    public void setAllHobbies(List<String> allHobbies) { this.allHobbies = allHobbies; }
    public void setAllLanguages(List<String> allLanguages) { this.allLanguages = allLanguages; }
    public void setAllWeights(Map<String, Double> allWeights) { this.allWeights = allWeights; }
    public void setSelectedCourses(List<String> selectedCourses) { this.selectedCourses = selectedCourses; }
    public void setSelectedPrograms(List<String> selectedPrograms) { this.selectedPrograms = selectedPrograms; }
    public void setSelectedYear(int selectedYear) { this.selectedYear = selectedYear; }
    public void setSelectedHobbies(List<String> selectedHobbies) { this.selectedHobbies = selectedHobbies; }
    public void setSelectedLanguages(List<String> selectedLanguages) { this.selectedLanguages = selectedLanguages; }
    public void setSelectedWeights(Map<String, Double> selectedWeights) { this.selectedWeights = selectedWeights; }
    public void setSaveMessage(String saveMessage) { this.saveMessage = saveMessage; }
    public void setFailedSaveMessage(String failedSaveMessage) { this.failedSaveMessage = failedSaveMessage; }
}
