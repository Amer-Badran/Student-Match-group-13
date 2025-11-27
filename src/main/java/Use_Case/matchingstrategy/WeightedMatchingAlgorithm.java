package Use_Case.matchingstrategy;

import Entity.MatchingPreferences;
import Entity.Client;

import java.util.List;
import java.util.Map;

/**
 * Calculates a compatibility score based on common courses,
 * programs, languages spoken, year of study, and hobbies,
 * applying the weights specified by the current user.
 */
public class WeightedMatchingAlgorithm implements MatchingStrategy {

    static final double FIRST = 0.35;
    static final double SECOND = 0.25;
    static final double THIRD = 0.20;
    static final double FOURTH = 0.12;
    static final double FIFTH = 0.08;

    @Override
    public double calculateScore(Client currentClient, Client otherClient) {
        double totalScore = 0.0;
        totalScore += calculateCourseScore(currentClient, otherClient);
        totalScore += calculateHobbyScore(currentClient, otherClient);
        totalScore += calculateLanguageScore(currentClient, otherClient);
        totalScore += calculateYearScore(currentClient, otherClient);
        totalScore += calculateProgramScore(currentClient, otherClient);
        return totalScore;
    }

    private double calculateCourseScore(Client currentClient, Client otherClient) {
        MatchingPreferences currentUserPrefs = currentClient.getPreferences();
        MatchingPreferences otherUserPrefs = otherClient.getPreferences();
        List<String> courses = currentUserPrefs.getCourses();
        List<String> otherCourses = otherUserPrefs.getCourses();
        double courseWeight = currentUserPrefs.getWeights().getOrDefault("Courses", FIRST);

        long numMatches = courses.stream().filter(otherCourses::contains).count();
        return courses.isEmpty() ? 0.0 : courseWeight * ((double) numMatches / courses.size());
    }

    private double calculateHobbyScore(Client currentClient, Client otherClient) {
        MatchingPreferences currentUserPrefs = currentClient.getPreferences();
        MatchingPreferences otherUserPrefs = otherClient.getPreferences();
        List<String> hobbies = currentUserPrefs.getHobbies();
        List<String> otherHobbies = otherUserPrefs.getHobbies();
        double hobbyWeight = currentUserPrefs.getWeights().getOrDefault("Hobbies", FIFTH);

        long numMatches = hobbies.stream().filter(otherHobbies::contains).count();
        return hobbies.isEmpty() ? 0.0 : hobbyWeight * ((double) numMatches / hobbies.size());
    }

    private double calculateLanguageScore(Client currentClient, Client otherClient) {
        MatchingPreferences currentUserPrefs = currentClient.getPreferences();
        MatchingPreferences otherUserPrefs = otherClient.getPreferences();
        List<String> languages = currentUserPrefs.getLanguages();
        List<String> otherLanguages = otherUserPrefs.getLanguages();
        double langWeight = currentUserPrefs.getWeights().getOrDefault("Languages", FOURTH);

        long numMatches = languages.stream().filter(otherLanguages::contains).count();
        return languages.isEmpty() ? 0.0 : langWeight * ((double) numMatches / languages.size());
    }

    private double calculateYearScore(Client currentClient, Client otherClient) {
        MatchingPreferences currentUserPrefs = currentClient.getPreferences();
        MatchingPreferences otherUserPrefs = otherClient.getPreferences();
        int yearOfStudy = currentUserPrefs.getYearOfStudy();
        int otherYear = otherUserPrefs.getYearOfStudy();
        double yearWeight = currentUserPrefs.getWeights().getOrDefault("YearOfStudy", THIRD);
        return yearOfStudy == otherYear ? yearWeight : 0.0;
    }

    private double calculateProgramScore(Client currentClient, Client otherClient) {
        MatchingPreferences currentUserPrefs = currentClient.getPreferences();
        MatchingPreferences otherUserPrefs = otherClient.getPreferences();
        Map<String, String> programs = currentUserPrefs.getPrograms();
        Map<String, String> otherPrograms = otherUserPrefs.getPrograms();
        double programWeight = currentUserPrefs.getWeights().getOrDefault("Programs", SECOND);

        double numMatches = 0.0;
        for (Map.Entry<String, String> entry : programs.entrySet()) {
            String programKey = entry.getKey();
            String programValue = entry.getValue();

            if (otherPrograms.containsKey(programKey)) {
                numMatches += 0.75; // partial match on program key
                if (programValue.equals(otherPrograms.get(programKey))) {
                    numMatches += 0.25; // exact program match
                }
            }
        }
        return programs.isEmpty() ? 0.0 : programWeight * (numMatches / programs.size());
    }
}
