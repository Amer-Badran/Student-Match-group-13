package Interface_Adapter.login;

import Use_Case.login.LoginInputBoundary;
import Use_Case.login.LoginInputData;
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
