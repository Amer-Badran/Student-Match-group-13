package use_case.logout;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public class LogoutInteractor implements LogoutInputBoundary {
    private final LogoutDataAccessObject logoutDataAccessObject;
    private final LogoutOutputBoundary logoutPresenter;

    public LogoutInteractor(LogoutDataAccessObject logoutDataAccessObject, LogoutOutputBoundary logoutPresenter) {
        this.logoutDataAccessObject = logoutDataAccessObject;
        this.logoutPresenter = logoutPresenter;
    }

    @Override
    public void execute(LogoutInputData inputData) {
        String username = inputData.getUsername();
        try {
            if (!logoutDataAccessObject.userExists(username)) {
                logoutPresenter.prepareFailView("User " + username + " does not exist.");
                return;
            }

            logoutDataAccessObject.logout(username);
            LogoutOutputData outputData = new LogoutOutputData(
                    username,
                    true,
                    "Logging out and returning to the welcome page.");
            logoutPresenter.prepareSuccessView(outputData);
        } catch (IOException | ParseException e) {
            logoutPresenter.prepareFailView("Unable to logout right now: " + e.getMessage());
        }
    }
}
