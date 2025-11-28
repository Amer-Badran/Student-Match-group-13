package Interface_Adapter.signup;

import Use_Case.signup.SignupInputBoundary;
import Use_Case.signup.SignupInputData;
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
    public void switchToWelcomeView(){
        signupInteractors.switchToWelcomeView();
    }
}
