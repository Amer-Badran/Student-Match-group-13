package Interface_Adapter.login;

import Interface_Adapter.Notification.NotificationState;
import Interface_Adapter.Notification.NotificationViewModel;
import Interface_Adapter.ViewManagerModel;
import Interface_Adapter.profile.ProfileState;
import Interface_Adapter.profile.ProfileViewModel;
import Use_Case.login.LoginOutputBoundary;
import Use_Case.login.LoginOutputData;

import java.util.ArrayList;

public class LoginPresenter implements LoginOutputBoundary {
    private final ViewManagerModel viewManagerModels;
    private final LoginViewModel loginViewModels;
    private ProfileViewModel profileViewModels;
    private NotificationViewModel notificationViewModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          LoginViewModel loginViewModel,
                          ProfileViewModel profileViewModel,
                          NotificationViewModel notificationViewModels){
        this.loginViewModels = loginViewModel;
        this.viewManagerModels = viewManagerModel;
        this.profileViewModels = profileViewModel;
        this.notificationViewModel = notificationViewModels;


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
        final NotificationState notificationState = notificationViewModel.getState();

        notificationState.setNotification(notification);
        notificationState.setUsername(loginViewModels.getState().getUsername());
        notificationViewModel.firePropertyChange();
        this.viewManagerModels.setState(notificationViewModel.getViewName());
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
