package app;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import data_access.JSONDataObject;
import entity.ClientFactory;
import interface_adapter.announcement.AnnouncementController;
import interface_adapter.announcement.AnnouncementPresenter;
import interface_adapter.announcement.AnnouncementViewModel;
import interface_adapter.chat.ChatController;
import interface_adapter.chat.ChatPresenter;
import interface_adapter.chat.ChatViewModel;
import interface_adapter.enterInfo.EnterInfoController;
import interface_adapter.enterInfo.EnterInfoPresenter;
import interface_adapter.enterInfo.EnterInfoViewModel;
import interface_adapter.notification.NotificationController;
import interface_adapter.notification.NotificationPresenter;
import interface_adapter.notification.NotificationViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.dashboard.DashboardController;
import interface_adapter.dashboard.DashboardPresenter;
import interface_adapter.dashboard.DashboardViewModel;
import interface_adapter.findmatches.FindMatchesController;
import interface_adapter.findmatches.FindMatchesPresenter;
import interface_adapter.findmatches.FindMatchesViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.profile.ProfileController;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.logout.LogoutViewModel;
import interface_adapter.profile.ProfilePresenter;
import interface_adapter.profile.ProfileViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.welcome.WelcomPresenter;
import interface_adapter.welcome.WelcomeController;
import use_case.announcement.AnnouncementInputBoundary;
import use_case.announcement.AnnouncementInteractor;
import use_case.announcement.AnnouncementOutputBoundary;
import use_case.chat.ChatInputBoundary;
import use_case.chat.ChatInteractor;
import use_case.chat.ChatOutputBoundary;
import use_case.enterInfo.EnterInfoInputBoundary;
import use_case.enterInfo.EnterInfoInteractor;
import use_case.enterInfo.EnterInfoOutputBoundary;
import use_case.notification.NotificationInputBoundary;
import use_case.notification.NotificationInteractor;
import use_case.notification.NotificationOutputBoundary;
import use_case.dashboard.DashboardInputBoundary;
import use_case.dashboard.DashboardInteractor;
import use_case.dashboard.DashboardOutputBoundary;
import use_case.findmatches.FindMatchesInputBoundary;
import use_case.findmatches.FindMatchesInteractor;
import use_case.findmatches.FindMatchesOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.matchingstrategy.WeightedMatchingAlgorithm;
import use_case.profile.ProfileInputBoundary;
import use_case.profile.ProfileInteractor;
import use_case.profile.ProfileOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import View.*;
import use_case.welcome.WelcomInteractor;
import use_case.welcome.welcomeInputBoundary;
import use_case.welcome.welcomeOutputBoundary;
import org.json.simple.parser.ParseException;

public class AppBuilder {

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
   // final UserFactory userFactory = new UserFactory();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    private SignupView signupView;
    private WelcomeView welcomeView;
    private LoginView loginView;
    private ProfileView profileView;
    private EnterInfoView enterInfoView;
    private NotificationView notificationView;
    private ChatView chatView;
    private DashboardView dashboardView;
    private FindMatchesView findMatchesView;
    private AnnouncementView announcementView;

    private SignupViewModel signupViewModel = new SignupViewModel();
    private LoginViewModel loginViewModels = new LoginViewModel();
    private ProfileViewModel profileViewModels = new ProfileViewModel();
    private EnterInfoViewModel enterInfoViewModel = new EnterInfoViewModel();
    private NotificationViewModel notificationViewModel = new NotificationViewModel();
    private ChatViewModel chatViewModel = new ChatViewModel();
    private DashboardViewModel dashboardViewModel = new DashboardViewModel();
    private LogoutViewModel logoutViewModel = new LogoutViewModel();
    private FindMatchesViewModel findMatchesViewModel = new FindMatchesViewModel();
    private AnnouncementViewModel announcementViewModel = new AnnouncementViewModel();


    private final WeightedMatchingAlgorithm matchingAlgorithm = new WeightedMatchingAlgorithm();



    private JSONDataObject DAO = new JSONDataObject();
    private ClientFactory clientFactory = new ClientFactory();

    public AppBuilder() throws IOException {
        cardPanel.setLayout(cardLayout);
    }

//    public AppBuilder addSignupView() {
//        signupViewModel = new SignupViewModel();
//        signupView = new SignupView(signupViewModel);
//        cardPanel.add(signupView, signupView.getViewName());
//        return this;
//    }

    public AppBuilder addWelcomeView(){
        welcomeView = new WelcomeView();
        cardPanel.add(welcomeView,welcomeView.getViewName());
        return this;
}

public AppBuilder addSignupView(){
                                                       // we need this here because now we have a state
        signupView = new SignupView(signupViewModel); // ane we need to tell java who is the state holder !
        cardPanel.add(signupView,signupView.getViewName());
        return this;

}
public AppBuilder addSignupUseCase(){
        final SignupOutputBoundary SignupPresenter = new SignupPresenter(viewManagerModel,signupViewModel,loginViewModels);
        final SignupInputBoundary signupInteractor = new SignupInteractor(DAO,SignupPresenter,clientFactory);
        signupView.setSignupController(new SignupController(signupInteractor));
        return this;
}

public AppBuilder addWelcomeUseCase(){
    final welcomeOutputBoundary welcomePresenter = new WelcomPresenter(viewManagerModel,signupViewModel,loginViewModels);
    final welcomeInputBoundary welcomeInteractor = new WelcomInteractor(welcomePresenter);
    welcomeView.setWelcomeControllerController(new WelcomeController(welcomeInteractor));
    return this;
}

public AppBuilder addLoginView(){
        loginView = new LoginView(loginViewModels);
        cardPanel.add(loginView,loginView.getViewName());
        return this;
}
public AppBuilder addLoginUseCase() throws IOException {
        final LoginOutputBoundary loginPresenter = new LoginPresenter(viewManagerModel,loginViewModels,profileViewModels,dashboardViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(DAO,loginPresenter);
        loginView.setLoginController(new LoginController(loginInteractor));
        return this;
}
public AppBuilder addProfileView(){
        profileView = new ProfileView(profileViewModels);
        cardPanel.add(profileView,profileViewModels.getViewName());
        return this;
}
public AppBuilder addProfileUseCase(){
        final ProfileOutputBoundary profilePresenter = new ProfilePresenter(viewManagerModel,profileViewModels,enterInfoViewModel);
        final ProfileInputBoundary profileInteractor = new ProfileInteractor(DAO,profilePresenter);
        profileView.setProfileController(new ProfileController(profileInteractor));
        return this;
}
public AppBuilder addEnterInfoView(){
        enterInfoView = new EnterInfoView(enterInfoViewModel);
        cardPanel.add(enterInfoView,enterInfoViewModel.getViewName());
        return this;
}
public AppBuilder addEnterInfoUseCase(){
        final EnterInfoOutputBoundary enterInfoPresetner = new EnterInfoPresenter(enterInfoViewModel,viewManagerModel,dashboardViewModel);
        final EnterInfoInputBoundary enterinfoInteractor = new EnterInfoInteractor(DAO,enterInfoPresetner);
        enterInfoView.setEnterInfoController(new EnterInfoController(enterinfoInteractor));
        return this;
}

public AppBuilder addDashboardView(){
        dashboardView = new DashboardView(dashboardViewModel, logoutViewModel);
        cardPanel.add(dashboardView,dashboardViewModel.getViewName());
        return this;
}

public AppBuilder addDashboardUseCase(){
    final DashboardOutputBoundary  DashboardPresetner = new DashboardPresenter(dashboardViewModel,viewManagerModel
                                                                        ,notificationViewModel,findMatchesViewModel,
                                                                          announcementViewModel);
    final DashboardInputBoundary DashboardInteractor = new DashboardInteractor(DAO, DashboardPresetner);
    dashboardView.setDashboardController(new DashboardController(DashboardInteractor));
    return this;
}

public AppBuilder addLogoutUseCase(){
        final LogoutOutputBoundary logoutPresenter = new LogoutPresenter(logoutViewModel, viewManagerModel);
        final LogoutInputBoundary logoutInteractor = new LogoutInteractor(DAO, logoutPresenter);
        dashboardView.setLogoutController(new LogoutController(logoutInteractor));
        return this;
}


public AppBuilder addNotificationView() throws IOException, ParseException {
        notificationView = new NotificationView(notificationViewModel);
        cardPanel.add(notificationView,"notification");
        return this;
}
public AppBuilder addNotificationUseCase(){
        final NotificationOutputBoundary notificationPresenter = new NotificationPresenter(notificationViewModel,chatViewModel,
                                                                                viewManagerModel,dashboardViewModel);
        final NotificationInputBoundary notificationInteractor = new NotificationInteractor(DAO,notificationPresenter);
        notificationView.setNotificationController(new NotificationController(notificationInteractor));
        return this;
}

public AppBuilder addAnnouncementView(){
        announcementView = new AnnouncementView(announcementViewModel);
        cardPanel.add(announcementView,announcementViewModel.getViewName());
        return this;
}

public AppBuilder addAnnouncementUseCase(){
        final AnnouncementOutputBoundary AnnouncementPresenter = new AnnouncementPresenter(announcementViewModel,dashboardViewModel,viewManagerModel);
        final AnnouncementInputBoundary AnnouncementInteractor = new AnnouncementInteractor(DAO,AnnouncementPresenter);
        announcementView.setAnnouncementController(new AnnouncementController(AnnouncementInteractor));
        return this;
}

public AppBuilder addFindMatchesView(){
        findMatchesView = new FindMatchesView(findMatchesViewModel);
        cardPanel.add(findMatchesView,findMatchesViewModel.getViewName());
        return this;
}
public AppBuilder addFindMatchesUseCase(){
        final FindMatchesOutputBoundary FindMatchesPresenter = new FindMatchesPresenter(viewManagerModel,findMatchesViewModel,dashboardViewModel);
        final FindMatchesInputBoundary findMatchesInteractor = new FindMatchesInteractor(DAO,matchingAlgorithm,FindMatchesPresenter);
        findMatchesView.setFindMatchesController(new FindMatchesController(findMatchesInteractor));
        return this;}


public AppBuilder addChatView(){
        chatView = new ChatView(chatViewModel);
        cardPanel.add(chatView,chatViewModel.getViewName());
        return this;
}
public AppBuilder addChatUseCase(){
        final ChatOutputBoundary chatPresenter = new ChatPresenter(chatViewModel,viewManagerModel,notificationViewModel);
        final ChatInputBoundary chatInteractor = new ChatInteractor(DAO,chatPresenter);
        chatView.setChatController(new ChatController(chatInteractor));
        return this;


}
    public JFrame build() {
        final JFrame application = new JFrame("Team 13 prototype");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        // I commented this out
        // viewManagerModel.setState(signupView.getViewName());
        //
        viewManagerModel.setState(welcomeView.getViewName());
        viewManagerModel.firePropertyChange();

        return application;
    }



}
