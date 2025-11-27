package Interface_Adapter.findmatches;

import Use_Case.findmatches.FindMatchesInputBoundary;
import Use_Case.findmatches.FindMatchesInputData;

public class FindMatchesController {

    private final FindMatchesInputBoundary interactor;

    public FindMatchesController(FindMatchesInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void findMatches(String username) {
        FindMatchesInputData inputData = new FindMatchesInputData(username);
        interactor.execute(inputData);
    }
}