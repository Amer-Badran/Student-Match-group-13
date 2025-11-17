package Use_Case.login;

import Data_Access.JSONDataobject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class LoginInteractor implements LoginInputBoundary{
    private final LoginDataAcessObject DAO;
    private LoginOutputBoundary loginPresenter;


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
            loginPresenter.prepareProfileView();
        }
        else{
            loginPresenter.prepareHomeView();
        }
    }

    @Override
    public void backToWelcomeView() {
        loginPresenter.backToWelcomeView();

    }
}
