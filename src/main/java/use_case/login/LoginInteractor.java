package use_case.login;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class LoginInteractor implements LoginInputBoundary{
    private final LoginDataAcessObject DAO;
    private LoginOutputBoundary loginPresenter;
    private LoginOutputData outputData;


    public LoginInteractor(LoginDataAcessObject dataobject,LoginOutputBoundary pres) throws IOException {
        this.DAO = dataobject;
        this.loginPresenter = pres;
    }

    @Override
    public void execute(LoginInputData input) throws IOException, ParseException {
        String name = input.getName();
        String pass = input.getPass();
        if(!DAO.userExists(name)){
            loginPresenter.prepareFailView("This user name does not exist !");
        }
        else if(!DAO.correctPassword(name,pass)){
            loginPresenter.prepareFailView("Wrong Password ! try again");
        }
        else if(DAO.checkNewUser(name)){
            outputData = new LoginOutputData(name);
            loginPresenter.prepareProfileView(outputData);
        }
        else{
            ArrayList<String> notification = DAO.getNotification(name);
            loginPresenter.prepareHomeView(notification);
        }
    }

    @Override
    public void backToWelcomeView() {
        loginPresenter.backToWelcomeView();

    }
}
