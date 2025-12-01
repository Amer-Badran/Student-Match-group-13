package Use_Case.announcement;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public interface AnnouncementInputBoundary {
    void execute(AnnouncementInputData input) throws IOException, ParseException;
    void BackToHomeView();
}
