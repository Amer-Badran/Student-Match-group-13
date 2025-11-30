package interface_adapter.dashboard;

import use_case.dashboard.DashboardInputBoundary;
import use_case.dashboard.DashboardInputData;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class DashboardController {
    private final DashboardInputBoundary interactor;
    public DashboardController(DashboardInputBoundary bound){
        this.interactor = bound;
    }
    public void prepareNotificationView(){
        DashboardInputData inputData = new DashboardInputData("");
        interactor.prepareNotificationView(inputData);

    }
    public void prepareFindMatchesView(){
        interactor.prepareFindMatchesView();
    }
    public void prepareAnnouncementView(String name) throws IOException, ParseException {
        DashboardInputData inputData = new DashboardInputData(name);
        interactor.prepareAnnouncementView(inputData);
    }
}
