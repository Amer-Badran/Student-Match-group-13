package entity;

import java.util.*;

public class MatchingPreferences {
    private List<String> courses;
    private Map<String, String> programs;
    private int yearOfStudy;
    private List<String> hobbies;
    private List<String>  languages;
    private Map<String, Double> weights;

    public MatchingPreferences(List<String> courses, Map<String, String> programs, int yearOfStudy, List<String> hobbies, List<String>  languages, Map<String, Double> weights) {

        validateCourses(courses);
        validateYearOfStudy(yearOfStudy);
        validateHobbies(hobbies);
        validateLanguages(languages);
        validateWeights(weights);

        this.courses = courses;
        this.programs = programs;
        this.yearOfStudy = yearOfStudy;
        this.hobbies = hobbies;
        this.languages = languages;
        this.weights = weights;
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
        // ensure that weights selected for categories are unique
        Set<Double> uniqueValues = new HashSet<>(weights.values());
        if (uniqueValues.size() != weights.size()) {
            throw new IllegalArgumentException("Rankings must be unique");
        }
    }

}