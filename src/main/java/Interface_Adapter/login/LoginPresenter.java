package Interface_Adapter.login;

import Interface_Adapter.ViewManagerModel;
import Interface_Adapter.profile.ProfileState;
import Interface_Adapter.profile.ProfileViewModel;
import Use_Case.login.LoginOutputBoundary;
import Use_Case.login.LoginOutputData;

public class LoginPresenter implements LoginOutputBoundary {
    private final ViewManagerModel viewManagerModels;
    private final LoginViewModel loginViewModels;
    private ProfileViewModel profileViewModels;
    public LoginPresenter(ViewManagerModel viewManagerModel,LoginViewModel loginViewModel,ProfileViewModel profileViewModel){
        this.loginViewModels = loginViewModel;
        this.viewManagerModels = viewManagerModel;
        this.profileViewModels = profileViewModel;

    }

    @Override
    public void prepareProfileView(LoginOutputData outputData) {
        final ProfileState profileState = profileViewModels.getState();
        profileState.setUserName(outputData.getUsername());
        this.viewManagerModels.setState(profileViewModels.getViewName());
        this.viewManagerModels.firePropertyChange();
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
