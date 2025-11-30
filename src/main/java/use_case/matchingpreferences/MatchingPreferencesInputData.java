package use_case.matchingpreferences;

import java.util.ArrayList;
import java.util.List;

public class MatchingPreferencesInputData {
    private final List<String> courses;
    private final List<String> programs = new ArrayList<>();
    private final int yearOfStudy;
    private List<String> hobbies;
    private List<String>  languages;

    public MatchingPreferencesInputData(List<String> courses, List<String> programs, int yearOfStudy) {
        this.courses = courses;
        //this.programs = programs;
        this.yearOfStudy = yearOfStudy;
        this.hobbies = hobbies;
        this.languages = languages;
    }

    public List<String> getCourses() { return courses; }
    public List<String> getPrograms() { return programs; }
    public int getYearOfStudy() { return yearOfStudy; }
    public List<String> getHobbies() { return hobbies; }
    public List<String>  getLanguages() { return languages; }
}

