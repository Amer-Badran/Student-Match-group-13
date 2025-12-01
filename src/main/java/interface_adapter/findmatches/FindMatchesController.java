package interface_adapter.findmatches;

import entity.Profile;
import use_case.findmatches.FindMatchesInputBoundary;
import use_case.findmatches.FindMatchesInputData;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class FindMatchesController {

    private final FindMatchesInputBoundary interactor;

    public FindMatchesController(FindMatchesInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String username) throws IOException, ParseException {
        FindMatchesInputData inputData = new FindMatchesInputData(username);
        interactor.execute(inputData);
    }
    public void switchToHomeView(){
        interactor.switchToHomeView();
    }
    public Profile getProfile(String name) throws IOException, ParseException {return interactor.getProfile(name);}
}
