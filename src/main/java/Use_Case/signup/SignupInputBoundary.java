package Use_Case.signup;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface SignupInputBoundary {


     void execute(SignupInputData signupInputData) throws IOException, ParseException;

     void switchToLoginView();
}
