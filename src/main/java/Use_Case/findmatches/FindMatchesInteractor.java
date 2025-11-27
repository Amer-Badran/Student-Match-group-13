package Use_Case.findmatches;

import Entity.Client;
import Entity.MatchingPreferences;
import Use_Case.matchingstrategy.WeightedMatchingAlgorithm;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FindMatchesInteractor implements FindMatchesInputBoundary {

    private final FindMatchesDataAccessObject userDAO;
    private final WeightedMatchingAlgorithm matchingAlgorithm;
    private final FindMatchesOutputBoundary presenter;

    private static boolean hasUsablePreferences(Client user) {
        if (user == null || user.getPreferences() == null) {
            return false;
        }

        MatchingPreferences prefs = user.getPreferences();

        boolean hasCourses = prefs.getCourses() != null && !prefs.getCourses().isEmpty();
        boolean hasPrograms = prefs.getPrograms() != null && !prefs.getPrograms().isEmpty();
        boolean hasHobbies = prefs.getHobbies() != null && !prefs.getHobbies().isEmpty();
        boolean hasLanguages = prefs.getLanguages() != null && !prefs.getLanguages().isEmpty();
        boolean hasYear = prefs.getYearOfStudy() > 0;

        return hasCourses || hasPrograms || hasHobbies || hasLanguages || hasYear;
    }

    public FindMatchesInteractor(FindMatchesDataAccessObject userDAO,
                                 WeightedMatchingAlgorithm matchingAlgorithm,
                                 FindMatchesOutputBoundary presenter) {
        this.userDAO = userDAO;
        this.matchingAlgorithm = matchingAlgorithm;
        this.presenter = presenter;
    }

    @Override
    public void execute(FindMatchesInputData inputData) {

        // 1. Get current user
        Client currentUser = userDAO.findByUsername(inputData.getCurrentUsername());
        if (currentUser == null) {
            presenter.prepareFailView("User not found.");
            return;
        }

        // 2. Load all users and filter out entries without usable preferences
        List<Client> allUsers = userDAO.getAllUsers();
        List<Client> candidates = new ArrayList<>();
        for (Client user : allUsers) {
            if (user == null || !hasUsablePreferences(user)) {
                continue;
            }
            if (!user.getUsername().equals(currentUser.getUsername())) {
                candidates.add(user);
            }
        }

        if (!hasUsablePreferences(currentUser)) {
            presenter.prepareFailView("Please complete your preferences before searching for matches.");
            return;
        }

        // 3. Check if only the current user has usable preferences
        if (candidates.isEmpty()) {
            presenter.prepareFailView(
                    "Wow you're early, it seems that you are the first user, come back later and try again!"
            );
            return;
        }

        // 4. Build score list for all candidates except current user
        List<Map.Entry<String, Double>> scored = new ArrayList<>();

        for (Client candidate : candidates) {
            double score = matchingAlgorithm.calculateScore(currentUser, candidate);
            scored.add(Map.entry(candidate.getUsername(), score));
        }

        // 5. Sort by score descending
        scored.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

        // 6. Keep top 10 matches
        if (scored.size() > 10) {
            scored = scored.subList(0, 10);
        }

        // 7. Move into LinkedHashMap for ordered output
        Map<String, Double> resultMap = new LinkedHashMap<>();
        for (Map.Entry<String, Double> entry : scored) {
            resultMap.put(entry.getKey(), entry.getValue());
        }

        // 8. Check if matches are empty
        if (resultMap.isEmpty()) {
            presenter.prepareFailView(
                    "You have no matches, try to adjust your matching preferences."
            );
            return;
        }

        // 9. Send to presenter
        FindMatchesOutputData outputData = new FindMatchesOutputData(resultMap);
        presenter.prepareSuccessView(outputData);
    }

}
