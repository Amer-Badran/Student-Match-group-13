package interface_adapter.logout;

import org.json.simple.parser.ParseException;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInputData;

import java.io.IOException;

public class LogoutController {
    private final LogoutInputBoundary logoutInteractor;

    public LogoutController(LogoutInputBoundary logoutInteractor) {
        this.logoutInteractor = logoutInteractor;
    }

    public void execute(String username) throws IOException, ParseException {
        logoutInteractor.execute(new LogoutInputData(username));
    }
}
