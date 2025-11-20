package Use_Case.EnterInfo;

import Entity.Client;
import Entity.MatchingPreferences;
import org.json.simple.parser.ParseException;
import java.io.IOException;

public interface MatchingPreferencesDataAccessObject {
    Client getClient(String username) throws IOException, ParseException;
    void save(MatchingPreferences matchingPreferences) throws IOException, java.text.ParseException;
}
