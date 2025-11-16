package Entity;

import java.util.List;
import java.util.Map;

public class MatchingPreferences {
    private List<String> courses;
    private Map<String, String> programs;
    private int yearOfStudy;
    private List<String> hobbies;
    private List<String>  languages;
    private Map<String, String> weights;

    public MatchingPreferences(List<String> courses, List<String> programs, int yearOfStudy, List<String> hobbies) {
        if (courses != null && courses.size() > 6) {
            throw new IllegalArgumentException("Cannot select more than 6 courses");
        }
        if (programs != null && programs.size() > 3) {
            throw new IllegalArgumentException("Cannot select more than 3 programs");
        }
        this.courses = courses;
        this.programs = programs;
        this.yearOfStudy = yearOfStudy;
        this.hobbies = hobbies;
        this.languages = languages;
        this.weights = weights;
    }

    public List<String> getCourses() { return courses; }
    public Map<String, String> getPrograms() { return programs; }
    public int getYearOfStudy() { return yearOfStudy; }
    public List<String> getHobbies() { return hobbies; }
    public List<String> getLanguages() { return languages; }
    public Map<String, String> getWeights() { return weights; }

    public void setCourses(List<String> courses) {
        if (courses.size() > 6) throw new IllegalArgumentException("Cannot select more than 6 courses");
        this.courses = courses;
    }

    public void setPrograms(Map<String, String> programs) {
        if (programs.size() > 3) throw new IllegalArgumentException("Cannot select more than 3 programs");
        this.programs = programs;
    }

    public void setYearOfStudy(int yearOfStudy) { this.yearOfStudy = yearOfStudy; }
    public void setHobbies(List<String> hobbies) { this.hobbies = hobbies; }
    public void setLanguages(List<String> hobbies) { this.languages = languages; }
    public void setWeights(Map<String, String> weights) { this.weights = weights; }
    // Default {Courses : FIRST, Programs : SECOND, Year of Study : THIRD, Languages Spoken FOURTH: ,Interests : FIFTH}
    // 0.35, 0.25, 0.2, 0.12, 0.08
}
