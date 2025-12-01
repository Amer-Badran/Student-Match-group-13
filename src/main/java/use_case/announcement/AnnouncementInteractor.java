package use_case.announcement;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class AnnouncementInteractor implements AnnouncementInputBoundary{
    private AnnouncementDataAccessObject DAO;
    private AnnouncementOutputBoundary presenter;

    public AnnouncementInteractor(AnnouncementDataAccessObject DAO,
                                  AnnouncementOutputBoundary presenter){
        this.DAO = DAO;
        this.presenter = presenter;
    }
    @Override
    public void execute(AnnouncementInputData input) throws IOException, ParseException {
        if(input.getMessage().isEmpty()){
            presenter.prepareFailView("announcement can not be empty");
        }
        else{
            DAO.updateAnnouncements(input.getUsername() + ":  " + input.getMessage());
            ArrayList<String> log = DAO.getAnnouncements(input.getUsername());
            AnnouncementOutputData output = new AnnouncementOutputData(log);
            presenter.prepareSuccessView(output);
        }}
    public void update(String name) throws IOException, ParseException {
        ArrayList<String> log = DAO.getAnnouncements(name);
        AnnouncementOutputData output = new AnnouncementOutputData(log);
        presenter.prepareSuccessView(output);
    }


    @Override
    public void BackToHomeView() {
        presenter.switchToHomeView();

    }

}
