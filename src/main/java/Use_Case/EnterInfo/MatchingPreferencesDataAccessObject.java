package Use_Case.EnterInfo;

import Entity.Client;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.text.ParseException;

public interface MatchingPreferencesDataAccessObject {
    Client getClient(String username) throws IOException, ParseException;
    void save(Client client) throws IOException, java.text.ParseException;
}
