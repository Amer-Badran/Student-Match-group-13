package Use_Case.dashboard;

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




}
