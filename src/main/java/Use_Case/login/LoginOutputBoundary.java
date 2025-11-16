package Use_Case.login;

public interface LoginOutputBoundary {
    void prepareProfileView();
    void prepareHomeView();
    void prepareFailView(String error);
    void backToWelcomeView();
}
