package Use_Case.login;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface LoginInputBoundary {
    void execute(LoginInputData input) throws IOException, ParseException;
    void backToWelcomeView();
}
