package Interface_Adapter.findmatches;

import Entity.Profile;
import Use_Case.findmatches.FindMatchesInputBoundary;
import Use_Case.findmatches.FindMatchesInputData;
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
