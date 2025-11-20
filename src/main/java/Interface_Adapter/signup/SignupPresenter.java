package Interface_Adapter.signup;

import Interface_Adapter.ViewManagerModel;
import Interface_Adapter.login.LoginViewModel;
import Use_Case.signup.SignupOutputBoundary;

public class SignupPresenter implements SignupOutputBoundary {
    private final SignupViewModel signupViewModels;
    private final LoginViewModel loginViewModels;
    private final ViewManagerModel viewManagerModels;
    public SignupPresenter(ViewManagerModel viewManagerModel, SignupViewModel signupViewModel, LoginViewModel loginViewModel){
        this.signupViewModels = signupViewModel;
        this.viewManagerModels = viewManagerModel;
        this.loginViewModels = loginViewModel;
    }
    @Override
    public void prepareFailView(String Error) {
        final SignupState signupState = signupViewModels.getState();
        signupState.setUsernameError(Error);
        signupViewModels.firePropertyChange();
    }

    @Override
    public void prepareSuccessView() {
        this.viewManagerModels.setState(loginViewModels.getViewName());
        this.viewManagerModels.firePropertyChange();
    }
}

