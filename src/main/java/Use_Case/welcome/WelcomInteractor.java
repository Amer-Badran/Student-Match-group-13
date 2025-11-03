package Use_Case.welcome;


//import Entity.ClientFactory;

public class WelcomInteractor implements welcomeInputBoundary{
    private final welcomeOutputBoundary userPresenter;

    public WelcomInteractor(welcomeOutputBoundary userPresenter) {
        this.userPresenter = userPresenter;
    }

    @Override
    public void switchToSignupView() {
            userPresenter.switchToSignupView();
    }

    @Override
    public void switchToLoginView() {
        userPresenter.switchToLoginView();

    }
}
