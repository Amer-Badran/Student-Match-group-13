package app;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import Data_Access.JSONDataObject;
import Entity.ClientFactory;
import Interface_Adapter.announcement.AnnouncementController;
import Interface_Adapter.announcement.AnnouncementPresenter;
import Interface_Adapter.announcement.AnnouncementViewModel;
import Interface_Adapter.enterInfo.EnterInfoController;
import Interface_Adapter.enterInfo.EnterInfoPresenter;
import Interface_Adapter.enterInfo.EnterInfoViewModel;
import Interface_Adapter.ViewManagerModel;
import Interface_Adapter.dashboard.DashboardController;
import Interface_Adapter.dashboard.DashboardPresenter;
import Interface_Adapter.dashboard.DashboardViewModel;
import Interface_Adapter.findmatches.FindMatchesController;
import Interface_Adapter.findmatches.FindMatchesPresenter;
import Interface_Adapter.findmatches.FindMatchesViewModel;
import Interface_Adapter.login.LoginController;
import Interface_Adapter.login.LoginPresenter;
import Interface_Adapter.login.LoginViewModel;
import Interface_Adapter.profile.ProfileController;
import Interface_Adapter.profile.ProfilePresenter;
import Interface_Adapter.profile.ProfileViewModel;
import Interface_Adapter.signup.SignupController;
import Interface_Adapter.signup.SignupPresenter;
import Interface_Adapter.signup.SignupViewModel;
import Interface_Adapter.welcome.WelcomPresenter;
import Interface_Adapter.welcome.WelcomeController;
import Use_Case.announcement.AnnouncementInputBoundary;
import Use_Case.announcement.AnnouncementInteractor;
import Use_Case.announcement.AnnouncementOutputBoundary;
import Use_Case.enterInfo.EnterInfoInputBoundary;
import Use_Case.enterInfo.EnterInfoInteractor;
import Use_Case.enterInfo.EnterInfoOutputBoundary;
import Use_Case.dashboard.DashboardInputBoundary;
import Use_Case.dashboard.DashboardInteractor;
import Use_Case.dashboard.DashboardOutputBoundary;
import Use_Case.findmatches.FindMatchesInputBoundary;
import Use_Case.findmatches.FindMatchesInteractor;
import Use_Case.findmatches.FindMatchesOutputBoundary;
import Use_Case.login.LoginInputBoundary;
import Use_Case.login.LoginInteractor;
import Use_Case.login.LoginOutputBoundary;
import Use_Case.matchingstrategy.WeightedMatchingAlgorithm;
import Use_Case.profile.ProfileInputBoundary;
import Use_Case.profile.ProfileInteractor;
import Use_Case.profile.ProfileOutputBoundary;
import Use_Case.signup.SignupInputBoundary;
import Use_Case.signup.SignupInteractor;
import Use_Case.signup.SignupOutputBoundary;
import View.*;
import Use_Case.welcome.WelcomInteractor;
import Use_Case.welcome.welcomeInputBoundary;
import Use_Case.welcome.welcomeOutputBoundary;
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
    private DashboardView dashboardView;
    private FindMatchesView findMatchesView;
    private AnnouncementView announcementView;

    private SignupViewModel signupViewModel = new SignupViewModel();
    private LoginViewModel loginViewModels = new LoginViewModel();
    private ProfileViewModel profileViewModels = new ProfileViewModel();
    private EnterInfoViewModel enterInfoViewModel = new EnterInfoViewModel();
    private DashboardViewModel dashboardViewModel = new DashboardViewModel();
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
        dashboardView = new DashboardView(dashboardViewModel);
        cardPanel.add(dashboardView,dashboardViewModel.getViewName());
        return this;
}

public AppBuilder addDashboardUseCase(){
    final DashboardOutputBoundary  DashboardPresetner = new DashboardPresenter(dashboardViewModel,viewManagerModel
                                                                        ,findMatchesViewModel,
                                                                          announcementViewModel);
    final DashboardInputBoundary DashboardInteractor = new DashboardInteractor(DAO, DashboardPresetner);
    dashboardView.setDashboardController(new DashboardController(DashboardInteractor));
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
