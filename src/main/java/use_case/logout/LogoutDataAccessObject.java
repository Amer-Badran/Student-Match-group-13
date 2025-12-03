package use_case.logout;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface LogoutDataAccessObject {
    boolean userExists(String username) throws IOException, ParseException;

    void logout(String username) throws IOException, ParseException;
}
