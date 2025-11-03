package Interface_Adapter.welcome;

import Interface_Adapter.ViewManagerModel;
import Interface_Adapter.signup.SignupViewModel;
import Use_Case.welcome.welcomeOutputBoundary;

public class WelcomPresenter implements welcomeOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final SignupViewModel signupViewModel;

    public WelcomPresenter(ViewManagerModel viewManagerModel,SignupViewModel signupViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.signupViewModel = signupViewModel;
    }

    @Override
    public void switchToSignupView() {
        viewManagerModel.setState(signupViewModel.getViewName());
        viewManagerModel.firePropertyChange();


    }

    @Override
    public void switchToLoginView() {

    }
}
