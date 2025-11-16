package Entity;

import org.json.simple.JSONObject;

import java.util.List;
import java.util.ArrayList;

public class MatchingPreferences {

    private final List<String> courseCodes;
    private final List<String> programNames;
    private final int yearOfStudy;

    public MatchingPreferences(List<String> courseCodes, List<String> programNames, int yearOfStudy) {
        this.courseCodes = courseCodes;
        this.programNames = programNames;
        this.yearOfStudy = yearOfStudy;
    }

    // Getter methods
    public List<String> getCourseCodes() {
        return new ArrayList<>(courseCodes); // Return a copy to avoid accidental modifications
    }

    public List<String> getProgramNames() {
        return new ArrayList<>(programNames); // Return a copy
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }
}

