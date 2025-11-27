package Use_Case.signup;

public interface SignupOutputBoundary {
    void prepareFailView(String Error);
    void prepareSuccessView(SignupOutputData output);
    void switchToWelcomeView();

}
