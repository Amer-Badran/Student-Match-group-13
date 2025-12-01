package interface_adapter.signup;

import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInputData;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class SignupController{
    SignupInputBoundary signupInteractors;
    public SignupController(SignupInputBoundary signupInteractor){
        this.signupInteractors = signupInteractor;

    }

    public void execute(String name,String pass) throws IOException, ParseException {
        SignupInputData input = new SignupInputData(name,pass);
        signupInteractors.execute(input);
    }
    public void switchToLoginView(){
        signupInteractors.switchToLoginView();
    }
}
