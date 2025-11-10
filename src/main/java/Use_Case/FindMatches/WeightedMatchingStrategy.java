package Use_Case.FindMatches;

import entity.MatchingPreferences;
import entity.User;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A concrete implementation of the **Strategy Pattern** (MatchingStrategy).
 *
 * This strategy calculates a compatibility score based on common courses,
 * programs, and year of study, applying the weights specified by the
 * current user.
 */
public class WeightedMatchingStrategy implements MatchingStrategy {

    @Override
    public double calculateScore(User currentUser, User otherUser) {
        MatchingPreferences currentUserPrefs = currentUser.getPreferences();
        MatchingPreferences otherUserPrefs = otherUser.getPreferences();
        Map<String, Integer> weights = currentUser.getWeights();

        // Get default weights if none provided
        int courseWeight = weights.get(0);
        int programWeight = weights.getOrDefault("programs", 1);
        int yearWeight = weights.getOrDefault("year", 1);
        int totalWeight = courseWeight + programWeight + yearWeight;

        if (totalWeight == 0) {
            return 0.0; // Avoid division by zero if all weights are 0
        }

        // 1. Calculate Course Score
        double courseScore = calculateSetOverlap(
                currentUserPrefs.getCourseCodes(),
                otherUserPrefs.getCourseCodes());

        // 2. Calculate Program Score
        double programScore = calculateSetOverlap(
                currentUserPrefs.getProgramCodes(),
                otherUserPrefs.getProgramCodes());

        // 3. Calculate Year of Study Score
        double yearScore = (currentUserPrefs.getYearOfStudy() == otherUserPrefs.getYearOfStudy())
                ;

        // 4. Calculate Final Weighted Score
        double finalScore = (
                (courseScore * courseWeight) +
                        (programScore * programWeight) +
                        (yearScore * yearWeight)
        ) / totalWeight;

        return finalScore;
    }

    /**
     * Helper method to calculate the Jaccard index (overlap) between two lists.
     * (Intersection size / Union size). Hey, Julia. I know you want to get rid od this part,
     * but the whole purpose of this index is to make sure the mathing score remains fair,
     * while also being symmetrical for both of the clients.
     * If that is not something we necessarily want that's fine.
     *
     * @return A score between 0.0 and 1.0.
     */
    private double calculateSetOverlap(List<String> listA, List<String> listB) {
        if (listA == null || listB == null || listA.isEmpty() || listB.isEmpty()) {
            return 0.0;
        }

        Set<String> setA = new HashSet<>(listA);
        Set<String> setB = new HashSet<>(listB);

        Set<String> intersection = new HashSet<>(setA);
        intersection.retainAll(setB);

        Set<String> union = new HashSet<>(setA);
        union.addAll(setB);

        if (union.isEmpty()) {
            return 0.0;
        }

        return (double) intersection.size() / union.size();
    }
}
