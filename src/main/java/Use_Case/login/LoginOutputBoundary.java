package Use_Case.login;

public interface LoginOutputBoundary {
    void prepareProfileView(LoginOutputData outputData);
    void prepareHomeView(LoginOutputData outputData);
    void prepareFailView(String error);
    void backToWelcomeView();
}
