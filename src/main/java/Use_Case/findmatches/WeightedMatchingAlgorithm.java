package Use_Case.findmatches;

import Entity.MatchingPreferences;
import Entity.User;

import java.util.List;
import java.util.Map;

/**
 *
 * This calculates a compatibility score based on common courses,
 * programs, languages spoken, year of study and hobbies, applying the weights specified by the
 * current user.
 */
public class WeightedMatchingAlgorithm {

    static final double FIRST = 0.35;
    static final double SECOND = 0.25;
    static final double THIRD = 0.20;
    static final double FOURTH = 0.12;
    static final double FIFTH = 0.08;

    public double calculateScore(User currentUser, User otherUser) {
        MatchingPreferences currentUserPrefs = currentUser.getPreferences();
        MatchingPreferences otherUserPrefs = otherUser.getPreferences();
        Map<String, Double> weights = currentUserPrefs.getWeights();

        double totalScore = 0.0;

        // 1. Calculate Course Score
        {
            List<String> courses = currentUserPrefs.getCourses();
            List<String> otherCourses = otherUserPrefs.getCourses();
            double courseWeight = weights.getOrDefault("course", FIRST);
            int numCourses = courses.size();
            int numMatches = 0;

            for (String course : courses) {
                if (otherCourses.contains(course)) {
                    numMatches++;
                }
            }

            double courseScore = numCourses > 0 ? courseWeight * ((double) numMatches / numCourses) : 0.0;
            totalScore += courseScore;
        }

        // 2. Calculate Hobbies Score
        {
            List<String> hobbies = currentUserPrefs.getHobbies();
            List<String> otherHobbies = otherUserPrefs.getHobbies();
            double hobbyWeight = weights.getOrDefault("hobbies", FIFTH);
            int numHobbies = hobbies.size();
            int numMatches = 0;

            for (String hobby : hobbies) {
                if (otherHobbies.contains(hobby)) {
                    numMatches++;
                }
            }

            double hobbiesScore = numHobbies > 0 ? hobbyWeight * ((double) numMatches / numHobbies) : 0.0;
            totalScore += hobbiesScore;
        }

        // 3. Calculate Languages Spoken Score
        {
            List<String> languages = currentUserPrefs.getLanguages();
            List<String> otherLanguages = otherUserPrefs.getLanguages();
            double langWeight = weights.getOrDefault("languages", FOURTH);
            int numLangs = languages.size();
            int numMatches = 0;

            for (String lang : languages) {
                if (otherLanguages.contains(lang)) {
                    numMatches++;
                }
            }

            double languageScore = numLangs > 0 ? langWeight * ((double) numMatches / numLangs) : 0.0;
            totalScore += languageScore;
        }

        // 4. Calculate Year of Study Score
        {
            int yearOfStudy = currentUserPrefs.getYearOfStudy();
            int otherYearOfStudy = otherUserPrefs.getYearOfStudy();
            int isMatch = (otherYearOfStudy == yearOfStudy) ? 1 : 0;
            ;

            double yearScore = isMatch * weights.getOrDefault("year", THIRD);
            totalScore += yearScore;
        }

        // 5. Calculate Program Score
        {
            Map<String, String> programs = currentUserPrefs.getPrograms();
            Map<String, String> otherPrograms = otherUserPrefs.getPrograms();
            double programWeight = weights.getOrDefault("programs", SECOND);
            int numPrograms = programs.size();
            double numMatches = 0.0;

            for (String program : programs.keySet()) {
                // If the other user has the same program key (e.g., same faculty)
                if (otherPrograms.containsKey(program)) {
                    numMatches += 0.75; // partial match on key

                    // If they also have the same program value (same specialization)
                    if (otherPrograms.get(program).equals(programs.get(program))) {
                        numMatches += 0.25; // extra match on exact program value
                    }
                }
            }

            double languageScore = numPrograms > 0 ? programWeight * ((double) numMatches / numPrograms) : 0.0;
            totalScore += languageScore;
        }

        return totalScore;
    }

}