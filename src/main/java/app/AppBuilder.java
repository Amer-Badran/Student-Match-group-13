package app;
import javax.swing.*;
import java.awt.*;

import Interface_Adapter.ViewManagerModel;
import Interface_Adapter.signup.SignupViewModel;
import Interface_Adapter.welcome.WelcomPresenter;
import Interface_Adapter.welcome.WelcomeController;
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
    private SignupViewModel signupViewModel = new SignupViewModel();


    public AppBuilder() {
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

public AppBuilder addWelcomeUseCase(){
    final welcomeOutputBoundary welcomePresenter = new WelcomPresenter(viewManagerModel,signupViewModel);
    final welcomeInputBoundary welcomeInteractor = new WelcomInteractor(welcomePresenter);
    welcomeView.setWelcomeControllerController(new WelcomeController(welcomeInteractor));
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
