package use_case.findmatches;

import entity.Client;
import entity.Profile;
import use_case.matchingstrategy.WeightedMatchingAlgorithm;
import org.json.simple.parser.ParseException;

import java.io.IOException;
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
    public void switchToHomeView(){
        presenter.switchToHomeView();
    }

    @Override
    public Profile getProfile(String name) throws IOException, ParseException {
        if(!userDAO.UserExists(name)){
            presenter.prepareFailView("this user does not exist");
        }
        return userDAO.getProfile(name);
    }

    @Override
    public void execute(FindMatchesInputData inputData) throws IOException, ParseException {

        // 1. Get current user
        Client currentUser = userDAO.findByUsername(inputData.getCurrentUsername());
        if (currentUser == null) {
            presenter.prepareFailView("User not found.");
            return;
        }

        // 2. Load all users
        List<Client> allUsers = userDAO.getAllUsers();

        // 3. Check if only the current user exists
        if (allUsers.size() == 1 && allUsers.contains(currentUser)) {
            presenter.prepareFailView(
                    "Wow you're early, it seems that you are the first user, come back later and try again!"
            );
            return;
        }

        // 4. Build score list for all candidates except current user
        List<Map.Entry<String, Double>> scored = new ArrayList<>();

        for (Client candidate : allUsers) {
            double score = matchingAlgorithm.calculateScore(currentUser, candidate);

            // Skip if candidate is the current user or score is invalid
            if (candidate.getUsername().equals(currentUser.getUsername()) || score <= 0 || Double.isNaN(score)) {
                continue;
            }

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
