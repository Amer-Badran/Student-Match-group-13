package Use_Case.announcement;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public interface AnnouncementDataAccessObject {
    void updateAnnouncements(String announcement) throws IOException, ParseException;
    ArrayList<String> getAnnouncements(String username) throws IOException, ParseException;
}
