package Entity;

import java.util.*;

public class MatchingPreferences {
    private List<String> courses;
    private Map<String, String> programs;
    private int yearOfStudy;
    private List<String> hobbies;
    private List<String>  languages;
    private Map<String, Double> weights;

    public MatchingPreferences(List<String> courses, Map<String, String> programs, int yearOfStudy, List<String> hobbies, List<String>  languages, Map<String, Double> weights) {
        this(courses, programs, yearOfStudy, hobbies, languages, weights, true);
    }

    public MatchingPreferences(List<String> courses, Map<String, String> programs, int yearOfStudy,
                               List<String> hobbies, List<String> languages, Map<String, Double> weights,
                               boolean enforceValidation) {

        // Sanitize nulls to avoid runtime errors when loading legacy data
        this.courses = courses == null ? new ArrayList<>() : courses;
        this.programs = programs == null ? new HashMap<>() : programs;
        this.yearOfStudy = yearOfStudy;
        this.hobbies = hobbies == null ? new ArrayList<>() : hobbies;
        this.languages = languages == null ? new ArrayList<>() : languages;
        this.weights = (weights == null || weights.isEmpty()) ? defaultWeights() : weights;

        if (enforceValidation) {
            validateCourses(this.courses);
            validateYearOfStudy(this.yearOfStudy);
            validateHobbies(this.hobbies);
            validateLanguages(this.languages);
            validateWeights(this.weights);
        }
    }


    public List<String> getCourses() { return courses; }
    public  Map<String, String> getPrograms() { return programs; }
    public int getYearOfStudy() { return yearOfStudy; }
    public List<String> getHobbies() { return hobbies; }
    public List<String> getLanguages() { return languages; }
    public Map<String, Double> getWeights() { return weights; }

    public void setCourses(List<String> courses) {
        validateCourses(courses);
        this.courses = courses;
    }

    public void setPrograms(Map<String, String> programs) {
//        validatePrograms(programs);
        this.programs = programs;
    }

    public void setYearOfStudy(int yearOfStudy) {
        validateYearOfStudy(yearOfStudy);
        this.yearOfStudy = yearOfStudy; }

    public void setHobbies(List<String> hobbies) {
        validateHobbies(hobbies);
        this.hobbies = hobbies;
    }

    public void setLanguages(List<String> languages) {
        validateLanguages(languages);
        this.languages = languages; }

    public void setWeights(Map<String, Double> weights) {
        validateWeights(weights);
        this.weights = weights; }
    // Default {"Courses" : FIRST, "Programs" : SECOND, "YearOfStudy" : THIRD, "Languages" FOURTH:, "Hobbies" : FIFTH}
    // 0.35, 0.25, 0.2, 0.12, 0.08

    // Validation Helper Methods.
    private static void validateCourses(List<String> courses) {
        if (courses == null || courses.isEmpty()) {
            throw new IllegalArgumentException("Must select at least one course");
        }
        if (courses != null && courses.size() > 6) {
            throw new IllegalArgumentException("Cannot select more than 6 courses");
        }
    }

    private static void validatePrograms(Map<String, String> programs) {
        if (programs == null || programs.isEmpty()) {
            throw new IllegalArgumentException("Must select at least one program");
        }
        if (programs != null && programs.size() > 3) {
            throw new IllegalArgumentException("Cannot select more than 3 programs");
        }
    }

    public static void validateYearOfStudy(int years) {
        if (years < 1) {
            throw new IllegalArgumentException("Year of Study must be greater than zero");
        }
    }

    private static void validateLanguages(List<String> languages) {
        if (languages == null || languages.isEmpty()) {
            throw new IllegalArgumentException("Must select at least one language");
        }
    }

    private static void validateHobbies(List<String> hobbies) {
        if (hobbies == null || hobbies.isEmpty()) {
            throw new IllegalArgumentException("Must select at least one hobby");
        }
    }

    private static void validateWeights(Map<String, Double> weights) {
        if (weights == null || weights.isEmpty()) {
            throw new IllegalArgumentException("Must rank the categories");
        }

        double total = 0;
        for (Double value : weights.values()) {
            if (value == null) {
                throw new IllegalArgumentException("Each category must have a weight");
            }
            total += value;
        }

        if (Math.abs(total - 1.0) > 0.001) {
            throw new IllegalArgumentException("Weights must add up to 1.0");
        }
    }

    private static Map<String, Double> defaultWeights() {
        Map<String, Double> defaults = new HashMap<>();
        defaults.put("Courses", 0.35);
        defaults.put("Programs", 0.25);
        defaults.put("YearOfStudy", 0.20);
        defaults.put("Languages", 0.12);
        defaults.put("Hobbies", 0.08);
        return defaults;
    }

}