package Interface_Adapter.announcement;

import Use_Case.announcement.AnnouncementInputBoundary;
import Use_Case.announcement.AnnouncementInputData;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

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
