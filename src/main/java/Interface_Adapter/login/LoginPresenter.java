package Interface_Adapter.login;

import Interface_Adapter.ViewManagerModel;
import Interface_Adapter.dashboard.DashboardState;
import Interface_Adapter.dashboard.DashboardViewModel;
import Interface_Adapter.profile.ProfileState;
import Interface_Adapter.profile.ProfileViewModel;
import Use_Case.login.LoginOutputBoundary;
import Use_Case.login.LoginOutputData;

import java.util.ArrayList;

public class LoginPresenter implements LoginOutputBoundary {
    private final ViewManagerModel viewManagerModels;
    private final LoginViewModel loginViewModels;
    private ProfileViewModel profileViewModels;
    private final DashboardViewModel dashboardViewModel;
    public LoginPresenter(ViewManagerModel viewManagerModel,
                          LoginViewModel loginViewModel,
                          ProfileViewModel profileViewModel,
                          DashboardViewModel dashboardViewModels){
        this.loginViewModels = loginViewModel;
        this.viewManagerModels = viewManagerModel;
        this.profileViewModels = profileViewModel;
        this.dashboardViewModel = dashboardViewModels;


    }

    @Override
    public void prepareProfileView(LoginOutputData outputData) {
        final ProfileState profileState = profileViewModels.getState();
        profileState.setUsername(outputData.getUsername());
        this.viewManagerModels.setState(profileViewModels.getViewName());
        this.viewManagerModels.firePropertyChange();
    }

    @Override
    public void prepareHomeView(ArrayList<String> notification) {
        final DashboardState dashboardState = dashboardViewModel.getState();

        dashboardState.setNotification(notification);
        dashboardState.setUsername(loginViewModels.getState().getUsername());
//        notificationViewModel.firePropertyChange();
        this.viewManagerModels.setState(dashboardViewModel.getViewName());
        this.viewManagerModels.firePropertyChange();

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
