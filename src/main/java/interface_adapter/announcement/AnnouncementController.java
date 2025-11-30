package interface_adapter.announcement;

import use_case.announcement.AnnouncementInputBoundary;
import use_case.announcement.AnnouncementInputData;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class AnnouncementController {
    private AnnouncementInputBoundary interactor;

    public AnnouncementController(AnnouncementInputBoundary interactor){
        this.interactor = interactor;
    }

    public void execute(String name,String message ) throws IOException, ParseException {
        AnnouncementInputData input= new AnnouncementInputData(name,message);
        interactor.execute(input);
    }

    public void BackToHomeView(){
        interactor.BackToHomeView();
    }
}
