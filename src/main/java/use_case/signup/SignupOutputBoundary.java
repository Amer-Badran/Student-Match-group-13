package use_case.signup;

public interface SignupOutputBoundary {
    void prepareFailView(String Error);
    void prepareSuccessView(SignupOutputData output);

}
