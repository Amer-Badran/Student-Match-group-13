package Use_Case.dashboard;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public interface DashboardDataAccessObject {
    ArrayList<String> getAnnouncements(String username) throws IOException, ParseException;

}
