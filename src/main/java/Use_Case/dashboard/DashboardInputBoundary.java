package Use_Case.dashboard;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface DashboardInputBoundary {
    void execute(DashboardInputData input);
    void prepareFindMatchesView();
    void prepareAnnouncementView(DashboardInputData input) throws IOException, ParseException;
}
