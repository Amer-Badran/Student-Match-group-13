package Interface_Adapter.welcome;

import Interface_Adapter.ViewManagerModel;
import Interface_Adapter.login.LoginViewModel;
import Interface_Adapter.signup.SignupViewModel;
import Use_Case.welcome.welcomeOutputBoundary;
import View.LoginView;

public class WelcomPresenter implements welcomeOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final SignupViewModel signupViewModel;
    private final LoginViewModel loginViewModel;

    public WelcomPresenter(ViewManagerModel viewManagerModel,SignupViewModel signupViewModel,
                           LoginViewModel loginViewModels) {
        this.viewManagerModel = viewManagerModel;
        this.signupViewModel = signupViewModel;
        this.loginViewModel = loginViewModels;
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
