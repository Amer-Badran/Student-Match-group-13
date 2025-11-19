package app;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import Data_Access.JSONDataObject;
import Entity.ClientFactory;
import Interface_Adapter.ViewManagerModel;
import Interface_Adapter.login.LoginController;
import Interface_Adapter.login.LoginPresenter;
import Interface_Adapter.login.LoginViewModel;
import Interface_Adapter.signup.SignupController;
import Interface_Adapter.signup.SignupPresenter;
import Interface_Adapter.signup.SignupViewModel;
import Interface_Adapter.welcome.WelcomPresenter;
import Interface_Adapter.welcome.WelcomeController;
import Use_Case.login.LoginDataAcessObject;
import Use_Case.login.LoginInputBoundary;
import Use_Case.login.LoginInteractor;
import Use_Case.login.LoginOutputBoundary;
import Use_Case.signup.SignupInputBoundary;
import Use_Case.signup.SignupInteractor;
import Use_Case.signup.SignupOutputBoundary;
import View.*;
import Use_Case.welcome.WelcomInteractor;
import Use_Case.welcome.welcomeInputBoundary;
import Use_Case.welcome.welcomeOutputBoundary;

public class AppBuilder {

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
   // final UserFactory userFactory = new UserFactory();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    private SignupView signupView;
    private WelcomeView welcomeView;
    private LoginView loginView;
    private SignupViewModel signupViewModel = new SignupViewModel();
//    private LoginViewModel loginViewModels = new LoginViewModel();
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
    final welcomeOutputBoundary welcomePresenter = new WelcomPresenter(viewManagerModel,signupViewModel);
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
        final LoginOutputBoundary loginPresenter = new LoginPresenter(viewManagerModel,loginViewModels);
        final LoginInputBoundary loginInteractor = new LoginInteractor(DAO,loginPresenter);
        loginView.setLoginController(new LoginController(loginInteractor));
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
