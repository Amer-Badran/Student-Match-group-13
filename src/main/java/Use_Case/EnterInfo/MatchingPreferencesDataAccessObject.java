package Use_Case.EnterInfo;

import Entity.OldClient;
import Entity.MatchingPreferences;
import org.json.simple.parser.ParseException;
import java.io.IOException;

public interface MatchingPreferencesDataAccessObject {
    OldClient getClient(String username) throws IOException, ParseException;
    void save(String username, MatchingPreferences matchingPreferences) throws IOException, java.text.ParseException, ParseException;
}
