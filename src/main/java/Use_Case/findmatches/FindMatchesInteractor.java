package Use_Case.findmatches;

import Entity.User;
import Use_Case.matchingstrategy.MatchingStrategy;
import Use_Case.matchingstrategy.WeightedMatchingAlgorithm;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FindMatchesInteractor implements FindMatchesInputBoundary {

    private final FindMatchesDataAccessObject userDAO;
    private final WeightedMatchingAlgorithm matchingAlgorithm;
    private final FindMatchesOutputBoundary presenter;

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
        User currentUser = userDAO.findByUsername(inputData.getCurrentUsername());
        if (currentUser == null) {
            presenter.prepareFailView("User not found.");
            return;
        }

        // 2. Load all users
        List<User> allUsers = userDAO.getAllUsers();

        // 3. Build score list for all candidates except current user
        List<Map.Entry<String, Double>> scored = new ArrayList<>();

        for (User candidate : allUsers) {
            if (candidate.getUsername().equals(currentUser.getUsername())) continue;

            double score = matchingAlgorithm.calculateScore(currentUser, candidate);
            scored.add(Map.entry(candidate.getUsername(), score));
        }

        // 4. Sort by score DESCENDING
        scored.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

        // 5. Keep top 10
        if (scored.size() > 10) {
            scored = scored.subList(0, 10);
        }

        // 6. Move into LinkedHashMap for ordered output
        Map<String, Double> resultMap = new LinkedHashMap<>();
        for (Map.Entry<String, Double> entry : scored) {
            resultMap.put(entry.getKey(), entry.getValue());
        }

        // 7. Send to presenter
        FindMatchesOutputData outputData = new FindMatchesOutputData(resultMap);
        presenter.prepareSuccessView(outputData);
    }
}
