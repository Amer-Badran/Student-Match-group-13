package Interface_Adapter.signup;

import Interface_Adapter.ViewManagerModel;
import Interface_Adapter.login.LoginState;
import Interface_Adapter.login.LoginViewModel;
import Use_Case.signup.SignupOutputBoundary;
import Use_Case.signup.SignupOutputData;

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
    public void prepareSuccessView(SignupOutputData output) {
        final LoginState loginState = loginViewModels.getState();
        loginState.setClient(output.getClient());
        loginViewModels.firePropertyChange();
        this.viewManagerModels.setState(loginViewModels.getViewName());
        this.viewManagerModels.firePropertyChange();
    }
}

