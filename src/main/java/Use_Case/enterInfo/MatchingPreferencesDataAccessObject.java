package Use_Case.enterInfo;

import Entity.OldClient;
import Entity.MatchingPreferences;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.ArrayList;

public interface MatchingPreferencesDataAccessObject {
    OldClient getClient(String username) throws IOException, ParseException;
    void save(String username, MatchingPreferences matchingPreferences) throws IOException, java.text.ParseException, ParseException;
    ArrayList<String> getNotification(String username) throws IOException, ParseException;
}
