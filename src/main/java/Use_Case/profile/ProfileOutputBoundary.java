package Use_Case.profile;

public interface ProfileOutputBoundary {
    void prepareSuccessView(ProfileOutputData outputData);
    void prepareFailView(String errorMessage);
}
