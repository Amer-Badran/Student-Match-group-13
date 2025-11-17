package Interface_Adapter.login;

import Interface_Adapter.ViewManagerModel;
import Use_Case.login.LoginOutputBoundary;

public class LoginPresenter implements LoginOutputBoundary {
    private final ViewManagerModel viewManagerModels;
    private final LoginViewModel loginViewModels;
    public LoginPresenter(ViewManagerModel viewManagerModel,LoginViewModel loginViewModel){
        this.loginViewModels = loginViewModel;
        this.viewManagerModels = viewManagerModel;

    }

    @Override
    public void prepareProfileView() {
        LoginState currentState = loginViewModels.getState();
        currentState.setUsernameError("We will go to make you Profile !");
        loginViewModels.firePropertyChange();
    }

    @Override
    public void prepareHomeView() {
        LoginState currentState = loginViewModels.getState();
        currentState.setUsernameError("We will go to the Home Screen!");
        loginViewModels.firePropertyChange();

    }

    @Override
    public void prepareFailView(String error) {
        LoginState currentState = loginViewModels.getState();
        currentState.setUsernameError(error);
        loginViewModels.firePropertyChange();
    }

    @Override
    public void backToWelcomeView() {
//        this.viewManagerModels.setState(WelcomeViewModels.getViewName());
//        this.viewManagerModels.firePropertyChange();


    }
}
