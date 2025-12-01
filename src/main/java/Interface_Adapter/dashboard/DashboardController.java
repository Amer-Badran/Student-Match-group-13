package Interface_Adapter.dashboard;

import Use_Case.dashboard.DashboardInputBoundary;
import Use_Case.dashboard.DashboardInputData;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class DashboardController {
    private final DashboardInputBoundary interactor;
    public DashboardController(DashboardInputBoundary bound){
        this.interactor = bound;
    }
    public void prepareFindMatchesView(){
        interactor.prepareFindMatchesView();
    }

}
