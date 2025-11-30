package use_case.announcement;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface AnnouncementInputBoundary {
    void execute(AnnouncementInputData input) throws IOException, ParseException;
    void BackToHomeView();
}
