package Interface_Adapter.login;

import Interface_Adapter.ViewManagerModel;
import Interface_Adapter.profile.ProfileState;
import Interface_Adapter.profile.ProfileViewModel;
import Use_Case.login.LoginOutputBoundary;
import Use_Case.login.LoginOutputData;
import View.DashboardView;

import java.awt.KeyboardFocusManager;
import java.awt.Window;

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
        profileState.setUsername(outputData.getUsername());
        this.viewManagerModels.setState(profileViewModels.getViewName());
        this.viewManagerModels.firePropertyChange();
    }

    @Override
    public void prepareHomeView(LoginOutputData outputData) {
        LoginState currentState = loginViewModels.getState();
        currentState.setUsernameError(null);
        currentState.setUsername(outputData.getUsername());
        loginViewModels.setState(currentState);
        loginViewModels.firePropertyChange();

        Window activeWindow = KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow();
        if (activeWindow != null) {
            activeWindow.dispose();
        }
        DashboardView.showDashboard(outputData.getUsername());

    }

    @Override
    public void prepareFailView(String error) {
        LoginState currentState = loginViewModels.getState();
        currentState.setUsernameError(error);
        loginViewModels.firePropertyChange();
    }

    @Override
    public void backToWelcomeView() {
        final LoginState state = loginViewModels.getState();
        state.setUsernameError(null);
        loginViewModels.setState(state);
        loginViewModels.firePropertyChange();

        this.viewManagerModels.setState("welcome");
        this.viewManagerModels.firePropertyChange();
    }
}
