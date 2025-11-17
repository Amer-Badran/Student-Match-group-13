package Use_Case.findmatches;

public interface FindMatchesOutputBoundary {

    void prepareSuccessView(FindMatchesOutputData outputData);
    void prepareFailView(String errorMessage);
    // if the entered information doesn't meet the criteria
}
