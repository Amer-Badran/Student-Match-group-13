package Use_Case.login;

public interface LoginOutputBoundary {
    void prepareProfileView(LoginOutputData outputData);
    void prepareHomeView();
    void prepareFailView(String error);
    void backToWelcomeView();
}
