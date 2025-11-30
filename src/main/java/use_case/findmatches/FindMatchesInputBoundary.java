package use_case.findmatches;

import entity.Profile;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface FindMatchesInputBoundary {
    void switchToHomeView();
    Profile getProfile(String name) throws IOException, ParseException;
    void execute(FindMatchesInputData inputData) throws IOException, ParseException;
}
