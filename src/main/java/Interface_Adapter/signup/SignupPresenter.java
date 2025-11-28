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
        signupState.setSuccessMessage("");
        signupViewModels.firePropertyChange();
    }

    @Override
    public void prepareSuccessView(SignupOutputData output) {
        final SignupState signupState = signupViewModels.getState();
        signupState.setSuccessMessage("New user created.");
        signupState.setUsernameError("");
        signupViewModels.firePropertyChange();

        final LoginState loginState = loginViewModels.getState();
        loginState.setClient(output.getClient());
        loginViewModels.firePropertyChange();

        this.viewManagerModels.setState("welcome");
        this.viewManagerModels.firePropertyChange();
    }

    @Override
    public void switchToWelcomeView() {
        final SignupState signupState = signupViewModels.getState();
        signupState.setUsernameError("");
        signupState.setSuccessMessage("");
        signupViewModels.setState(signupState);
        signupViewModels.firePropertyChange();

        this.viewManagerModels.setState("welcome");
        this.viewManagerModels.firePropertyChange();
    }
}

