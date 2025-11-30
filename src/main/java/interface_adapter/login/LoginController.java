package interface_adapter.login;

import use_case.login.LoginInputBoundary;
import use_case.login.LoginInputData;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class LoginController {
    private final LoginInputBoundary interactor;

    public LoginController(LoginInputBoundary loginInputBoundary){
        this.interactor = loginInputBoundary;
    }
    public void execute(String name, String pass) throws IOException, ParseException {
        LoginInputData inputs = new LoginInputData(name,pass);
        interactor.execute(inputs);
    }
    public void backToWelcomeView(){
        interactor.backToWelcomeView();
    }
}
