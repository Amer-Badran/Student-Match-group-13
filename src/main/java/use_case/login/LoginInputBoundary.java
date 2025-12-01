package use_case.login;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface LoginInputBoundary {
    void execute(LoginInputData input) throws IOException, ParseException;
    void backToWelcomeView();
}
