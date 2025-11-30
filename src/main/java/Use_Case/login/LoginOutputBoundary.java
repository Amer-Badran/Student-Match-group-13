package Use_Case.login;

import java.util.ArrayList;

public interface LoginOutputBoundary {
    void prepareProfileView(LoginOutputData outputData);
    void prepareFailView(String error);
    void backToWelcomeView();
}
