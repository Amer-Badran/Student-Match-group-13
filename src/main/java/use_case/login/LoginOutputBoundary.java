package use_case.login;

import java.util.ArrayList;

public interface LoginOutputBoundary {
    void prepareProfileView(LoginOutputData outputData);
    void prepareHomeView(ArrayList<String> notif);
    void prepareFailView(String error);
    void backToWelcomeView();
}
