package interface_adapter.welcome;
//import Use_Case.*;
import use_case.welcome.welcomeInputBoundary;

public class WelcomeController {

    private final welcomeInputBoundary userSignupUseCaseInteractor;

    public WelcomeController(welcomeInputBoundary userSignupUseCaseInteractor) {
        this.userSignupUseCaseInteractor = userSignupUseCaseInteractor;
    }

    public void switchToSignupView(){
        userSignupUseCaseInteractor.switchToSignupView();
    }
    public void switchToLoginView(){
        userSignupUseCaseInteractor.switchToLoginView();
    }

}
