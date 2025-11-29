package Use_Case.findmatches;

import Entity.Client;
import Entity.Profile;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;

public interface FindMatchesDataAccessObject {
    Client findByUsername(String username) throws IOException, ParseException;
    List<Client> getAllUsers() throws IOException, ParseException;
    Profile getProfile(String name) throws IOException, ParseException;
    boolean UserExists(String name) throws IOException, ParseException;
}
