package use_case.dashboard;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class DashboardInteractor implements DashboardInputBoundary{
    private final DashboardOutputBoundary presenter;
    private DashboardDataAccessObject DAO;

    public DashboardInteractor(DashboardDataAccessObject DAO,DashboardOutputBoundary out){
        this.presenter=out;
        this.DAO = DAO;
    }
    @Override
    public void execute(DashboardInputData input) {

    }


    @Override
    public void prepareFindMatchesView() {
        presenter.prepareFindMatchesView();
    }

    @Override
    public void prepareAnnouncementView(DashboardInputData input) throws IOException, ParseException {
        DashboardOutputData ouptut = new DashboardOutputData(DAO.getAnnouncements(input.getUsername()));
        presenter.prepareAnnouncementView(ouptut);
    }
}
