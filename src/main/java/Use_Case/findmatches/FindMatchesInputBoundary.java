package Use_Case.findmatches;

import Entity.Profile;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface FindMatchesInputBoundary {
    void switchToHomeView();
    Profile getProfile(String name) throws IOException, ParseException;
    void execute(FindMatchesInputData inputData) throws IOException, ParseException;
}
