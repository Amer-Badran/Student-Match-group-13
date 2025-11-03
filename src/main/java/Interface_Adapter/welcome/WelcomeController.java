package Interface_Adapter.welcome;
//import Use_Case.*;
import Use_Case.welcome.welcomeInputBoundary;

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
