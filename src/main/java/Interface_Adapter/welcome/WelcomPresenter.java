package Interface_Adapter.welcome;

import Interface_Adapter.ViewManagerModel;
import Interface_Adapter.signup.SignupViewModel;
import Interface_Adapter.login.LoginViewModel;
import Use_Case.welcome.welcomeOutputBoundary;

public class WelcomPresenter implements welcomeOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final SignupViewModel signupViewModel;
    private final LoginViewModel loginViewModel;

    public WelcomPresenter(ViewManagerModel viewManagerModel,SignupViewModel signupViewModel, LoginViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.signupViewModel = signupViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void switchToSignupView() {
        viewManagerModel.setState(signupViewModel.getViewName());
        viewManagerModel.firePropertyChange();


    }

    @Override
    public void switchToLoginView() {
        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
