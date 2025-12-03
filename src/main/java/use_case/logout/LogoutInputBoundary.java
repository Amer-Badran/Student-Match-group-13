package use_case.logout;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface LogoutInputBoundary {
    void execute(LogoutInputData inputData) throws IOException, ParseException;
}
