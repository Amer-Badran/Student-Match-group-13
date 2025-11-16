package Use_Case.findmatches;

import Entity.User;
import Entity.MatchingPreferences;
import Use_Case.matchingstrategy.MatchingStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FindMatchesInteractor implements FindMatchesInputBoundary {

    private final FindMatchesDataAccessObject userDataAccessObject;
    private final FindMatchesOutputBoundary presenter;
    private final MatchingStrategy matchingStrategy;

    public FindMatchesInteractor(FindMatchesDataAccessObject userDataAccessObject,
                                 FindMatchesOutputBoundary presenter,
                                 MatchingStrategy matchingStrategy) {
        this.userDataAccessObject = userDataAccessObject;
        this.presenter = presenter;
        this.matchingStrategy = matchingStrategy;
    }

    @Override
    public void execute(FindMatchesInputData inputData) {

       User currentUser = userDataAccessObject.findByUsername(inputData.getCurrentUsername());
        MatchingPreferences currentPrefs = currentUser.getPreferences();

        List<User> allUsers = userDataAccessObject.getAllUsers();
        List<User> candidates = new ArrayList<>();

        for (User u : allUsers) {
            if (!u.getUsername().equals(currentUser.getUsername())) {
                candidates.add(u);
            }
        }

        // Sort by compatibility score (highest first)
        candidates.sort(Comparator.comparingDouble(
                u -> -matchingStrategy.calculateScore(currentPrefs, u.getPreferences()))
        );

        FindMatchesOutputData outputData =
                new FindMatchesOutputData(currentUser, candidates);

        presenter.present(outputData);
    }
}
